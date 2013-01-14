package com.eurofighter.fileupload;


import com.eurofighter.fileupload.avscan.AVServiceException;
import com.eurofighter.fileupload.avscan.AVStreamScanner;
import com.eurofighter.fileupload.avscan.ScanObject;
import com.eurofighter.fileupload.util.RequestUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.EnumSet;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.webapp.ChainedUploadedFileProcessor;


/**
 * Chained Upload File Processor which does the Antivirus Scan of the file.
 */
public class AVUploadFileProcessor implements ChainedUploadedFileProcessor {

    /**
     * Private logger.
     */
    private static final ADFLogger LOG = ADFLogger.createADFLogger(AVUploadFileProcessor.class);

    /**
     * Initialization parameter for the <code>AVUploadFileProcessor</code> that configures the server and port where the
     * antivirus process is running. The value should contain a String in format "server_name:port", like for example:
     *
     *  <context-param>
     *      <param-name>com.eurofighter.fileupload.AVUploadFileProcessor.AV_SERVER</param-name>
     *      <param-value>localhost:4711</param-value>
     *  </context-param>
     */
    private static final String ANTIVIRUS_SERVER_PARAM_NAME =
        "com.eurofighter.fileupload.AVUploadFileProcessor.AV_SERVER";

    /**
     * Initialization parameter for the <code>AVUploadFileProcessor</code> that configures the path to the folder where
     * the files wich are found to contain virus are placed. If there is non value defined then the infected files will
     * not be copyed to any folder, they will be removed at the end of the request as invalid. The value should contain
     * a String containing the aboslute path to the folder like for example:
     *
     *  <context-param>
     *      <param-name>com.eurofighter.fileupload.AVUploadFileProcessor.INFECTED_FILES_DIR</param-name>
     *      <param-value>d:/tmpVirus</param-value>
     *  </context-param>
     */
    private static final String INFECTED_FILES_DIR_PARAM_NAME =
        "com.eurofighter.fileupload.AVUploadFileProcessor.INFECTED_FILES_DIR";

    /**
     * Initialization parameter for the <code>AVUploadFileProcessor</code> that configures the levels of AV warnings
     * which are considered as infected. In other words said, when the AV returns one of this level of information
     * regarding the file, that file will be considered as infected and will be treated accordingly (file will be copyed
     * to the INFECTED_FILES_DIR, the user will be notifyed and the uprocessing of the file will be stopped). When the
     * parameter is not defined or is defined with emtpy value all levels are considered as INFECTED.
     * Possible values for this parameter is a String containing a comma separated list with the possible values:
     *
     * 'infected' - the AV sends this level when the file is infected with a virus
     * 'encrypted' - the AV sends this level when the file is encrypted and scanning can not be done (for example a zip protected with password)
     * 'warning' - the AV sends this level when the file can be read but can not be changed (for example an encrypted PDF)
     * 'scanerror' - the AV sends this level when an error occured when scanning the file
     *
     *  <context-param>
     *      <param-name>com.eurofighter.fileupload.AVUploadFileProcessor.INFECTED_FILES_LEVELS</param-name>
     *      <param-value>infected,encrypted,warning,scanerror</param-value>
     *  </context-param>
     */
    private static final String INFECTED_FILES_LEVELS_PARAM_NAME =
        "com.eurofighter.fileupload.AVUploadFileProcessor.INFECTED_FILES_LEVELS";

    /**
     * Enumeration containing the list of all possible Infected File Levels.
     */
    private static enum InfectedFileLevel {
        infected,
        encrypted,
        warning,
        scanerror;
    }

    /**
     * These will keep the values configured for the init parameters.
     */
    private String serverName;
    private String serverPort;
    private String infectedFilesDir;
    private EnumSet<InfectedFileLevel> infectedFileLevels;

    /**
     * Default constructor.
     */
    public AVUploadFileProcessor() {
        super();
    }

    /**
     * Initialization method for the Antivirus Upload File Processor. It reads initialization parameters from web.xml.
     *
     * @param context the request context. Eyther a ServletContext or a PortletContext.
     */
    public void init(Object context) {
        LOG.info("Initialize the AVUploadFileProcessor");

        // Read the AV server configuration.
        String avServerParam = RequestUtils.getInitParameter(context, ANTIVIRUS_SERVER_PARAM_NAME);
        LOG.info(ANTIVIRUS_SERVER_PARAM_NAME + "=" + avServerParam);
        if (avServerParam != null) {
            String[] parts = avServerParam.split(":");
            if (parts != null && parts.length > 1) {
                if (parts[0] != null) {
                    this.serverName = parts[0].trim();
                }
                if (parts[1] != null) {
                    this.serverPort = parts[1].trim();
                }
            }
        }

        // Read the infected files dir.
        String infectedFilesDirParam = RequestUtils.getInitParameter(context, INFECTED_FILES_DIR_PARAM_NAME);
        LOG.info(INFECTED_FILES_DIR_PARAM_NAME + "=" + infectedFilesDirParam);
        if (infectedFilesDirParam != null) {
            this.infectedFilesDir = infectedFilesDirParam.trim();
        }

        // Read the AV Levels.
        initAvLevels(context);
    }

    /**
     * Init the avLevels List depending on the anti-virus levels list from the web.xml.
     *
     * @param context that need to be pressed as reference on init(Object context) method
     */
    private void initAvLevels(Object context) {
        // Initialize the infected file levels with empty list.
        this.infectedFileLevels = EnumSet.noneOf(InfectedFileLevel.class);

        // Read parameter.
        String avLevelsParam = RequestUtils.getInitParameter(context, INFECTED_FILES_LEVELS_PARAM_NAME);
        LOG.info(INFECTED_FILES_LEVELS_PARAM_NAME + "=" + avLevelsParam);
        if (avLevelsParam != null) {
            String[] levels = avLevelsParam.split(",");
            if (levels != null && levels.length > 0) {
                for (String level : levels) {
                    if (level != null) {
                        String levelTrimmed = level.trim();
                        // Iterate trough the enum and see if the value is in the enum.
                        for (InfectedFileLevel levelEnumElem : AVUploadFileProcessor.InfectedFileLevel.values()) {
                            if (levelTrimmed.equalsIgnoreCase(levelEnumElem.name())) {
                                this.infectedFileLevels.add(levelEnumElem);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Process the uploaded file trough the current antivirus upload file processor.
     * First check if we have valid antivirus server configuration, if not then throw an exception.
     * Afetrwards check the file with the antivirus and if virus is found or error on scanning then cppy the file to the infectedFilesDir (if any specifyed) and throw according error.
     * If check is ok proceed by returning the same file.
     *
     * @param request the request.
     * @param uploadedFile the uploaded file.
     * @return the uploaded file if no virus found.
     * @throws IOException in case no AV server configuration is given, the file contains virus, or there was an error scanning the file.
     */
    public UploadedFile processFile(Object request, UploadedFile uploadedFile) throws IOException {
        // Check for valid server configuration.
        if (this.serverName == null || this.serverPort == null) {
            throw new IOException("No Antivirus Server Configuration specified");
        }

        String fileName = uploadedFile.getFilename();
        LOG.info("Start scanning for Virus the uploaded file:" + fileName);

        // This keeps the input stream for closing.
        InputStream in = null;
        try {
            // Execute scan.
            AVStreamScanner scanner = AVStreamScanner.getInstance(this.serverName, this.serverPort);
            in = uploadedFile.getInputStream();
            String resp = scanner.getStreamScanReponse(in, fileName, false);
            LOG.fine("Recieved AV Response:" + resp);

            // Interpret the response.
            ScanObject respObj = new ScanObject();
            respObj.updateScanState(resp);
            if (isInfected(respObj)) {
                // File is infected, so move it to the infected files dir.
                LOG.info("Found infected file:" + fileName);
                copyUploadedFileToInfectedFolder(uploadedFile);

                // Depending on the alert show the correct message.
                throw new IOException("The file " + fileName + " is infected, so was not processed");
            }
        } catch (AVServiceException e) {
            throw new IOException("Error occurred when connecting to the AV Server to scan the file " + fileName, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    LOG.warning("Error while closing input stream to iuploaded file!");
                }
            }
        }

        // Mock test for checking if a file contains the virus word in it.
        //        BufferedReader br = new BufferedReader(new InputStreamReader(uploadedFile.getInputStream()));
        //        try {
        //            String line;
        //            while ((line = br.readLine()) != null) {
        //                if (line.indexOf("virus") != -1) {
        //                    throw new IOException("Virus found in file " + fileName + "!\n");
        //                }
        //            }
        //        } finally {
        //            br.close();
        //        }

        LOG.log("Done scanning for Virus the uploaded file:" + fileName);

        /**
         * Since we did not change anything in the Inputstream we got from the parameter
         * its ok to return the same object. The file argument is backed by the buffer
         * (which was created in the ChainedUploadedFileProcessor which invokes the ChainedUploadedFileProcessor classes)
         * hence subsequent processors will be able to access the stream again.
         */
        return uploadedFile;
    }

    /**
     * Method telling if the object for which the response object was generated is infected or not.
     *
     * @param respObj the response object containig response from the Antivirus Scan Process.
     * @return true if the ScanObject respObj is infected, encrypted, error or warning status and there is a similar
     * AVLevel in the avLevels list for that status.
     */
    private boolean isInfected(ScanObject respObj) {
        return (respObj.isInfected() &&
                (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(AVUploadFileProcessor.InfectedFileLevel.infected))) ||
            (respObj.isEncrypted() &&
             (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(AVUploadFileProcessor.InfectedFileLevel.encrypted))) ||
            (respObj.isError() &&
             (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(AVUploadFileProcessor.InfectedFileLevel.scanerror))) ||
            (respObj.isWarning() &&
             (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(AVUploadFileProcessor.InfectedFileLevel.warning)));
    }

    /**
     * Size of the buffer used to copy the uploaded file to the infected folder.
     */
    private static final int COPY_BUFFER_SIZE = 8192; // 8K

    /**
     * Copy the uploaded file to the infected files folder.
     *
     * @param uploadedFile the uploaded file.
     */
    private void copyUploadedFileToInfectedFolder(UploadedFile uploadedFile) {
        if (this.infectedFilesDir != null) {
            File dir = new File(this.infectedFilesDir);
            if (dir.exists() && dir.isDirectory()) {
                // Create a unique file name containing the current system time.
                File file = new File(dir, System.currentTimeMillis() + "_" + uploadedFile.getFilename());
                LOG.info("Copying file to:" + file.getAbsolutePath());

                OutputStream out = null;
                InputStream in = null;
                try {
                    out = new FileOutputStream(file);
                    in = uploadedFile.getInputStream();

                    byte[] buffer = new byte[COPY_BUFFER_SIZE];
                    int bytes;
                    while ((bytes = in.read(buffer)) > 0) {
                        out.write(buffer, 0, bytes);
                    }
                } catch (Exception e) {
                    LOG.warning("Error while copying the uploadedFile:" + uploadedFile.getFilename() +
                                " to the infected dir:" + this.infectedFilesDir);
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (Exception e) {
                            LOG.warning("Error while closing output stream while copying to infected folder!");
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Exception e) {
                            LOG.warning("Error while closing input stream while copying to infected folder!");
                        }
                    }
                }
            }
        }
    }

}
