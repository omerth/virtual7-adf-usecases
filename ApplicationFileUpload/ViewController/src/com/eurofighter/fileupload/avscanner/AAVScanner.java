package com.eurofighter.fileupload.avscanner;


import com.eurofighter.fileupload.avscanner.utils.RequestUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.model.UploadedFile;


/**
 * Abstract class which which can be extended by a class that wants to implement an IAVScanner.
 * This class offers usefull functionality like
 * - reading AV server connection informations in a form of "server:port" from web.xml initialization parameters.
 * - copy of infected files into a custom defined folder trough intialization parameters from web.xml file.
 */
public abstract class AAVScanner implements IAVScanner {
    /**
     * Logger.
     */
    protected static final ADFLogger LOG = ADFLogger.createADFLogger(AAVScanner.class);

    /**
     * Initialization parameter for the <code>AAVScanner</code> that configures the server and port where the
     * antivirus process is running. The value should contain a String in format "server_name:port", like for example:
     *
     *  <context-param>
     *      <param-name>com.eurofighter.fileupload.avscanner.AAVScanner.AV_SERVER</param-name>
     *      <param-value>localhost:4711</param-value>
     *  </context-param>
     */
    private static final String ANTIVIRUS_SERVER_PARAM_NAME =
        "com.eurofighter.fileupload.avscanner.AAVScanner.AV_SERVER";

    /**
     * Initialization parameter for the <code>AAVScanner</code> that tells if the connection to the AV System should be done
     * with SSL or not. The value should be a string with possible values: "true" or "false", like for example:
     *
     *  <context-param>
     *      <param-name>com.eurofighter.fileupload.avscanner.AAVScanner.AV_SERVER_USE_SSL_CONNECTION</param-name>
     *      <param-value>true</param-value>
     *  </context-param>
     */
    private static final String ANTIVIRUS_SERVER_USE_SSL_CONNECTION_PARAM_NAME =
        "com.eurofighter.fileupload.avscanner.AAVScanner.AV_SERVER_USE_SSL_CONNECTION";

    /**
     * Initialization parameter for the <code>AAVScanner</code> that configures the path to the folder where
     * the files wich are found to contain virus are placed. If there is non value defined then the infected files will
     * not be copyed to any folder, they will be removed at the end of the request as invalid. The value should contain
     * a String containing the aboslute path to the folder like for example:
     *
     *  <context-param>
     *      <param-name>com.eurofighter.fileupload.avscanner.AAVScanner.INFECTED_FILES_DIR</param-name>
     *      <param-value>d:/tmpVirus</param-value>
     *  </context-param>
     */
    private static final String INFECTED_FILES_DIR_PARAM_NAME =
        "com.eurofighter.fileupload.avscanner.AAVScanner.INFECTED_FILES_DIR";

    /**
     * Fields holding initialization parameter values.
     */
    protected String serverName;
    protected String serverPort;
    protected boolean useSSLConnection;
    protected String infectedFilesDir;

    /**
     * Default constructor.
     * Is mandatory to have it because the AVScanner classes are instantiated trough reflection using the default constructor.
     */
    public AAVScanner() {
        super();
    }


    /**
     * Concrete implementation of the IAVScanner.init() method.
     * Read the initialization parameters for the AVServer and the Infected Files dir.
     *
     * @param context the current ServletContext or PortletContext.
     * @see com.eurofighter.fileupload.avscanner.IAVScanner
     */
    public void init(Object context) {
        // Read the infected files dir and set the server namd and port.
        this.serverName = null;
        this.serverPort = null;
        String avServerParam = RequestUtils.getInitParameter(context, ANTIVIRUS_SERVER_PARAM_NAME);
        LOG.info("Read initialization parameter: " + ANTIVIRUS_SERVER_PARAM_NAME + "=" + avServerParam);
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

        // Read parameter telling is SSL connection should be used.
        this.useSSLConnection = false;
        String sslServerParam = RequestUtils.getInitParameter(context, ANTIVIRUS_SERVER_USE_SSL_CONNECTION_PARAM_NAME);
        LOG.info("Read initialization parameter: " + ANTIVIRUS_SERVER_USE_SSL_CONNECTION_PARAM_NAME + "=" +
                 sslServerParam);
        if (sslServerParam != null && "true".equalsIgnoreCase(sslServerParam.trim())) {
            this.useSSLConnection = true;
        }

        //Read the path to the folder where the infected files should be saved.
        this.infectedFilesDir = null;
        String infectedFilesDirParam = RequestUtils.getInitParameter(context, INFECTED_FILES_DIR_PARAM_NAME);
        LOG.info("Read initialization parameter: " + AAVScanner.INFECTED_FILES_DIR_PARAM_NAME + "=" +
                 infectedFilesDirParam);
        if (infectedFilesDirParam != null) {
            this.infectedFilesDir = infectedFilesDirParam.trim();
        }
    }

    /**
     * Concrete implementation of the method from the IAVScanner.scan() method.
     *
     * It offeres a skeleton implementation of the method with the following steps:
     * - validate the serverName and serverPort parameters and if they are invalid throw exception
     * - obtain the inputStream from the uploadedFile
     * - invoke the abstract performScan() method which must be concretely implemented by each extending object.
     * - in case an AVInfectedException is thrown by the performScan() method the file is copied to the INFECTED_FILES_DIR.
     * - in case AVConnectionException or AVAlertException are thrown they are thrown further
     * - in case an unknown exception is thrown a new AVAlert exception is thrown with the messasge "Unknonw exception while performing the AV scanning! Please contact the administrator!".
     * - close the input stream
     * - return the response
     *
     * @param uploadedFile
     * @return a ScanResponse
     * @throws AVConnectionException
     * @throws AVInfectedException
     * @throws AVAlertException
     * @see com.eurofighter.fileupload.avscanner.IAVScanner
     */
    public ScanResponse scan(UploadedFile uploadedFile) throws AVConnectionException, AVInfectedException,
                                                               AVAlertException {
        // Check for valid server configuration.
        if (this.serverName == null || this.serverPort == null) {
            throw new AVAlertException("Invalid Antivirus Server Configuration specified!");
        }

        // This keeps the input stream for closing.
        InputStream in = null;
        String fileName = uploadedFile.getFilename();
        try {
            // Execute scan.
            in = uploadedFile.getInputStream();
            ScanResponse resp = performScan(fileName, uploadedFile.getLength(), in);

            // Return the response.
            return resp;
        } catch (AVInfectedException e) {
            // File is infected, so move it to the infected files dir.
            LOG.warning("Found infected file:" + fileName);
            copyUploadedFileToInfectedFolder(uploadedFile);
            throw e;
        } catch (AVConnectionException e) {
            // Possibly expected exception, so throw it further.
            throw e;
        } catch (AVAlertException e) {
            // Possibly expected exception, so throw it further.
            throw e;
        } catch (Exception e) {
            // Unknown exception. This should not occur so throw an unknown exception messasge.
            throw new AVAlertException("Unknonw exception while performing the AV scanning! Please contact the administrator!",
                                       e);
        } finally {
            // Close the input stream.
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
     * Method which must be concretely implemented in all classes that extend the AAVScanner class. It performs the the actual ascanning
     * of the file with the given fileName and for which the content can be read trough the given fileStream.
     *
     * @param fileName the name of the file that is being scanned.
     * @param fileSize the zize of the file that is being scanned.
     * @param fileStream stream to the content of the file. The implementing classes can read content once from the stream
     *          and must not take care about closing the input stream.
     * @return a ScanResponse object which can contain informative information about the scanning process. It is returned only in case
     *              of succesfull scanning.
     * @throws AVConnectionException see com.eurofighter.fileupload.avscanner.IAVScanner.scan() description of then this exception should be thrown.
     * @throws AVInfectedException see com.eurofighter.fileupload.avscanner.IAVScanner.scan() description of then this exception should be thrown.
     * @throws AVAlertException see com.eurofighter.fileupload.avscanner.IAVScanner.scan() description of then this exception should be thrown.
     *
     */
    protected abstract ScanResponse performScan(String fileName, long fileSize,
                                                InputStream fileStream) throws AVConnectionException,
                                                                               AVInfectedException, AVAlertException;

    /**
     * Size of the buffer used to copy the uploaded file to the infected folder.
     */
    private static final int COPY_BUFFER_SIZE = 8192; // 8K

    /**
     * Copy the uploaded file to the infected files folder.
     *
     * @param uploadedFile the uploaded file.
     */
    protected void copyUploadedFileToInfectedFolder(UploadedFile uploadedFile) {
        if (infectedFilesDir != null) {
            File dir = new File(infectedFilesDir);
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
                                " to the infected dir:" + infectedFilesDir);
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
