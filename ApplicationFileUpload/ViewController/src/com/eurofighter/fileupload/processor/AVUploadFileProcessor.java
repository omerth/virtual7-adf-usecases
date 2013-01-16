package com.eurofighter.fileupload.processor;


import com.eurofighter.fileupload.av.AVAlertException;
import com.eurofighter.fileupload.av.AVConnectionException;
import com.eurofighter.fileupload.av.AVInfectedException;
import com.eurofighter.fileupload.av.IAVScanner;
import com.eurofighter.fileupload.av.AbsAVScanner;
import com.eurofighter.fileupload.av.mock.AVEncryptedException;
import com.eurofighter.fileupload.av.mock.AVScanErrorException;
import com.eurofighter.fileupload.av.mock.AVWarningException;
import com.eurofighter.fileupload.av.utils.RequestUtils;

import com.eurofighter.fileupload.av.savapi.SavapiAVScanner;

import java.io.IOException;

import java.lang.reflect.Constructor;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;

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
     * These will keep the values configured for the init parameters.
     */
    private String serverName;
    private String serverPort;
    private String infectedFilesDir;
    
    private Object context;
    private IAVScanner scanner;
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
        this.context = context;
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

        // Read the infected files dir.
        String infectedFilesDirParam = RequestUtils.getInitParameter(context, AbsAVScanner.INFECTED_FILES_DIR_PARAM_NAME);
        LOG.info(AbsAVScanner.INFECTED_FILES_DIR_PARAM_NAME + "=" + infectedFilesDirParam);
        if (infectedFilesDirParam != null) {
            this.infectedFilesDir = infectedFilesDirParam.trim();
        }
        // Obtain the IAVScanner concrete implementation configured in web.xml
        String antiVirussClassName = RequestUtils.getInitParameter(context, AbsAVScanner.ANTIVIRUS_SCANNER_CLASS_PARAM_NAME);
        scanner = initAVScannerWithRefrection(antiVirussClassName);
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

        try {
            scanner.scan(uploadedFile);
        } catch (AVConnectionException e) {
            throw new IOException("Error occurred when connecting to the AV Server to scan the file " + fileName, e);
        } catch (AVInfectedException e) {
            throw new IOException("The file " + fileName + " is infected, so was not processed");            
        } catch (AVAlertException e) {
//            if(e instanceof AVEncryptedException){
//                throw new IOException("The file " + fileName + " is infected(encrypted level), so was not processed");    
//            } 
//            if(e instanceof AVWarningException){
//                throw new IOException("The file " + fileName + " is infected(warning level), so was not processed");
//            }
//            if(e instanceof AVScanErrorException){
//                throw new IOException("The file " + fileName + " is infected(scanError level), so was not processed");
//            }
            throw new IOException(e.getMessage(), e);
        }
        LOG.log("Done scanning for Virus the uploaded file:" + fileName);

        /**
         * Since we did not change anything in the Inputstream we got from the parameter
         * its ok to return the same object. The file argument is backed by the buffer
         * (which was created in the ChainedUploadedFileProcessor which invokes the ChainedUploadedFileProcessor classes)
         * hence subsequent processors will be able to access the stream again.
         */
        return uploadedFile;
    }
    private IAVScanner initAVScannerWithRefrection(String classFullName){
        try {
            Class avScannerClass = Class.forName(classFullName);
            IAVScanner avScanner;
            if(IAVScanner.class.isAssignableFrom(avScannerClass)){
//                Constructor constructor = avScannerClass.getConstructor(new Class[]{AVUploadFileProcessor.class});
//                avScanner = (IAVScanner)constructor.newInstance(new Object[]{this});
                avScanner = (IAVScanner)avScannerClass.newInstance();
                Method initScanner = avScannerClass.getMethod("init", Object.class);
                initScanner.invoke(avScanner, context);
                return avScanner;
            }
            throw new ClassNotFoundException("The class " + classFullName + "ïs not instance of IAVScanner, so it can`t" +
                "be instantiated");
        } catch (InstantiationException e) {
            LOG.severe(e);
        } catch (IllegalAccessException e) {
            LOG.severe(e);
        } catch (ClassNotFoundException e) {
            LOG.severe(e);
        } catch (NoSuchMethodException e) {
            LOG.severe(e);
        } catch (InvocationTargetException e) {
            LOG.severe(e);
        }
        return null;
    }
//    public String getInfectedFilesDir() {
//        return infectedFilesDir;
//    }
//    public String getServerName() {
//        return serverName;
//    }
//
//    public String getServerPort() {
//        return serverPort;
//    }
//    
//    public Object getContext() {
//        return context;
//    }
}
