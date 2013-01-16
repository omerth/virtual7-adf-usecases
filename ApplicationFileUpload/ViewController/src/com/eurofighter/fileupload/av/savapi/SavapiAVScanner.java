package com.eurofighter.fileupload.av.savapi;

import com.eurofighter.fileupload.av.AVConnectionException;
import com.eurofighter.fileupload.av.AVInfectedException;
import com.eurofighter.fileupload.av.AbsAVScanner;

import com.eurofighter.fileupload.av.utils.RequestUtils;
import com.eurofighter.fileupload.processor.AVUploadFileProcessor;


import java.io.IOException;
import java.io.InputStream;

import java.util.EnumSet;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class SavapiAVScanner extends AbsAVScanner{
    /**
     * Private logger.
     */
    private static final ADFLogger LOG = ADFLogger.createADFLogger(SavapiAVScanner.class);
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
     *      <param-name>com.eurofighter.fileupload.processor.AVUploadFileProcessor.INFECTED_FILES_LEVELS</param-name>
     *      <param-value>infected,encrypted,warning,scanerror</param-value>
     *  </context-param>
     */
    private static final String INFECTED_FILES_LEVELS_PARAM_NAME =
        "com.eurofighter.fileupload.processor.AVUploadFileProcessor.INFECTED_FILES_LEVELS";

    /**
     * Enumeration containing the list of all possible Infected File Levels.
     */
    
    private static enum InfectedFileLevel {
        infected,
        encrypted,
        warning,
        scanerror;
    }
    
    private EnumSet<InfectedFileLevel> infectedFileLevels;
    private String serverName;
    private String serverPort;
    
//    public SavapiAVScanner(AVUploadFileProcessor fileProcessor) {
//        super(fileProcessor);
//        // Read the AV Levels.
//        initAvLevels(fileProcessor.getContext());
//    }
    public void init(Object context) {
        super.init(context);
        initAvLevels(context);
        // Read the AV server configuration.
        String avServerParam = RequestUtils.getInitParameter(context, AbsAVScanner.ANTIVIRUS_SERVER_PARAM_NAME);
        LOG.info(AbsAVScanner.ANTIVIRUS_SERVER_PARAM_NAME + "=" + avServerParam);
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
    }
    public void scan(UploadedFile uploadedFile) throws AVInfectedException, AVConnectionException {
        // This keeps the input stream for closing.
        InputStream in = null;
        try {
            
            String fileName = uploadedFile.getFilename();
            LOG.info("Start scanning for Virus the uploaded file:" + fileName);
            // Execute scan.
            AVStreamScanner scanner = AVStreamScanner.getInstance(serverName, serverPort);
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
                throw new AVInfectedException("Savapi antiviruss found a file infected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    LOG.warning("Error while closing input stream to iuploaded file!");
                }
            }
        }
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
                        for (InfectedFileLevel levelEnumElem : InfectedFileLevel.values()) {
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
     * Method telling if the object for which the response object was generated is infected or not.
     *
     * @param respObj the response object containig response from the Antivirus Scan Process.
     * @return true if the ScanObject respObj is infected, encrypted, error or warning status and there is a similar
     * AVLevel in the avLevels list for that status.
     */
    public boolean isInfected(ScanObject respObj) {
        return (respObj.isInfected() &&
                (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(InfectedFileLevel.infected))) ||
            (respObj.isEncrypted() &&
             (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(InfectedFileLevel.encrypted))) ||
            (respObj.isError() &&
             (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(InfectedFileLevel.scanerror))) ||
            (respObj.isWarning() &&
             (this.infectedFileLevels.isEmpty() || this.infectedFileLevels.contains(InfectedFileLevel.warning)));
    }
    
}
