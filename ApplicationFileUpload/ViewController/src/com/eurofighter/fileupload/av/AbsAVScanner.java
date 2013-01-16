package com.eurofighter.fileupload.av;

import com.eurofighter.fileupload.av.utils.RequestUtils;
import com.eurofighter.fileupload.processor.AVUploadFileProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.model.UploadedFile;

public abstract class AbsAVScanner implements IAVScanner{
    //                      ------      MEMBERS        ------
    /**
     * Private logger.
     */
    private static final ADFLogger LOG = ADFLogger.createADFLogger(AbsAVScanner.class);
    
    /**
     * Initialization parameter for the <code>AVUploadFileProcessor</code> that configures the server and port where the
     * antivirus process is running. The value should contain a String in format "server_name:port", like for example:
     *
     *  <context-param>
     *      <param-name>com.eurofighter.fileupload.processor.AVUploadFileProcessor.AV_SERVER</param-name>
     *      <param-value>localhost:4711</param-value>
     *  </context-param>
     */
    public static final String ANTIVIRUS_SERVER_PARAM_NAME =
        "com.eurofighter.fileupload.processor.AVUploadFileProcessor.AV_SERVER";

    /**
     * Initialization parameter for the <code>AVUploadFileProcessor</code> that configures the path to the folder where
     * the files wich are found to contain virus are placed. If there is non value defined then the infected files will
     * not be copyed to any folder, they will be removed at the end of the request as invalid. The value should contain
     * a String containing the aboslute path to the folder like for example:
     *
     *  <context-param>
     *      <param-name>com.eurofighter.fileupload.processor.AVUploadFileProcessor.INFECTED_FILES_DIR</param-name>
     *      <param-value>d:/tmpVirus</param-value>
     *  </context-param>
     */
    public static final String INFECTED_FILES_DIR_PARAM_NAME =
        "com.eurofighter.fileupload.processor.AVUploadFileProcessor.INFECTED_FILES_DIR";
    public static final String ANTIVIRUS_SCANNER_CLASS_PARAM_NAME = 
        "com.eurofighter.fileupload.processor.AVUploadFileProcessor.AVSCANNER_CLASS";
    /**
     * Size of the buffer used to copy the uploaded file to the infected folder.
     */
    private static final int COPY_BUFFER_SIZE = 8192; // 8K
    
    //                          -----       METHODS     ------
//    //constructor
//    protected AVUploadFileProcessor fileProcessor;
//    public AbsAVScanner(AVUploadFileProcessor fileProcessor){
//        this.fileProcessor = fileProcessor;
//    }
    protected Object context;
    private String infectedFilesDir;
    
    public void init(Object context) {
        this.context = context;
        // Read the infected files dir.
        String infectedFilesDirParam = RequestUtils.getInitParameter(context, AbsAVScanner.INFECTED_FILES_DIR_PARAM_NAME);
        LOG.info(AbsAVScanner.INFECTED_FILES_DIR_PARAM_NAME + "=" + infectedFilesDirParam);
        if (infectedFilesDirParam != null) {
            this.infectedFilesDir = infectedFilesDirParam.trim();
        }
    }
    /**
     * Copy the uploaded file to the infected files folder.
     *
     * @param uploadedFile the uploaded file.
     */
    public void copyUploadedFileToInfectedFolder(UploadedFile uploadedFile) {
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
