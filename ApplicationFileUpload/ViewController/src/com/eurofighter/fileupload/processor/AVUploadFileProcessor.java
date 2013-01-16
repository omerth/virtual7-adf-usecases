package com.eurofighter.fileupload.processor;


import com.eurofighter.fileupload.avscanner.AVAlertException;
import com.eurofighter.fileupload.avscanner.AVConnectionException;
import com.eurofighter.fileupload.avscanner.AVInfectedException;
import com.eurofighter.fileupload.avscanner.IAVScanner;
import com.eurofighter.fileupload.avscanner.ScanResponse;
import com.eurofighter.fileupload.avscanner.utils.RequestUtils;

import java.io.IOException;

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
     * Initialization parameter for the <code>AVUploadFileProcessor</code> that configures the class of the AVScanner
     * object which will be used to perform the actual file scanning. The class defined must be a class implementing the
     * <code>com.eurofighter.fileupload.av.IAVScanner interface</code>, like for example:
     *
     *  <context-param>
     *      <param-name>com.eurofighter.fileupload.processor.AVUploadFileProcessor.AVSCANNER_CLASS</param-name>
     *      <param-value>com.eurofighter.fileupload.av.savapi.SavapiAVScanner</param-value>
     *  </context-param>
     */
    private static final String ANTIVIRUS_SCANNER_CLASS_PARAM_NAME =
        "com.eurofighter.fileupload.processor.AVUploadFileProcessor.AVSCANNER_CLASS";

    /**
     * These will keep the instance of the AVScanner object.
     */
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

        // Read the IAVScanner class configured in web.xml and instantiate the scanner object.
        this.scanner = null;
        try {
            String avScannerClassName = RequestUtils.getInitParameter(context, ANTIVIRUS_SCANNER_CLASS_PARAM_NAME);
            LOG.info("Read initialization parameter: " + ANTIVIRUS_SCANNER_CLASS_PARAM_NAME + "=" +
                     avScannerClassName);
            if (avScannerClassName != null) {
                avScannerClassName = avScannerClassName.trim();
            }
            Object obj = Class.forName(avScannerClassName).newInstance();
            if (obj instanceof IAVScanner) {
                this.scanner = (IAVScanner)obj;
            } else {
                LOG.warning("The avscanner class:" + avScannerClassName +
                            " is not implementing the IAVScanner interface!");
                this.scanner = null;
            }
        } catch (Exception e) {
            LOG.severe("Could not instantiate the AVScanner class!", e);
            this.scanner = null;
        }

        // If there is a valid scanner object, the initialize it.
        if (this.scanner != null) {
            this.scanner.init(context);
        }
    }

    /**
     * Process the uploaded file trough the current antivirus upload file processor.
     * First check if we have valid AVScanner object, if not then throw an exception.
     * Afetrwards execute the scanning and interpret the possible exceptions accordingly.
     *
     * @param request the request.
     * @param uploadedFile the uploaded file.
     * @return the uploaded file if no virus found.
     * @throws IOException in case there is no valid AVScanner object or an error occured while performing the file scan.
     */
    public UploadedFile processFile(Object request, UploadedFile uploadedFile) throws IOException {
        // In case the scanner object is invalid, throw an exception.
        if (this.scanner == null) {
            throw new IOException("Invalid scanner object specifyed!");
        }

        String fileName = uploadedFile.getFilename();
        LOG.info("Start scanning for Virus the uploaded file:" + fileName);

        try {
            ScanResponse response = scanner.scan(uploadedFile);
            String responseMsg = null;
            if (response != null) {
                responseMsg = response.getMessage();
            }
            LOG.info("Scan of the file " + fileName +
                     " was processed correctly, and response from scanner returned following message:" + responseMsg);
        } catch (AVConnectionException e) {
            // Connection to the scanner server occured.
            throw new IOException("Error occurred when connecting to the AV Scanner System to scan the file " +
                                  fileName, e);
        } catch (AVInfectedException e) {
            // The file is infected.
            throw new IOException("The file " + fileName + " is infected, so was not processed");
        } catch (AVAlertException e) {
            // Other exception occured, so show that particular message.
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
}
