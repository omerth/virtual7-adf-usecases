package com.eurofighter.fileupload.avscanner;

import com.eurofighter.fileupload.avscanner.utils.RequestUtils;
import com.eurofighter.fileupload.processor.AVUploadFileProcessor;

import org.apache.myfaces.trinidad.model.UploadedFile;

/**
 * Main interface of the AVScanner API. An AVScanner object is an object executing the real scanning of a file.
 * It implements the concrete processing of the file trough a particular AV Scanner system.
 * Objects implementing this interface are used by the <code>com.eurofighter.fileupload.processor.AVUploadFileProcessor</code>
 * file processor to perform the file scanning.
 */
public interface IAVScanner {

    /**
     * Initialization method which is invoked only once per scanner object, after the object is created. It recieves as
     * parameter the servlet/portlet request context and can use it to obtain intialization information such as read of
     * initialization parameters from web.xml. The com.eurofighter.fileupload.avscanner.utils.RequestUtils can be used
     * to work easly with the context object.
     *
     * @param context the current ServletContext or PortletContext.
     */
    void init(Object context);

    /**
     * Perform the scan of the uploadedFile. This method can invoke the <code>uploadedFile.getInputStream()</code> method
     * as many times as it wants because the uploadedFile is buffered, but it also has to take care to close the opened
     * inputStreams.
     *
     * The scanning process is readonly, it can only inform about different events occured during the scanning process,
     * by the means of the thrown Exceptions. If the scan is sucessfull the method must return a ScanResponse object with status
     * regarding the scan process.
     *
     * @param uploadedFile the file to scan.
     * @return a ScanResponse object which can contain informative information about the scanning process. It is returned only in case
     *              of succesfull scanning.
     * @throws AVConnectionException Exception thrown in case there is a communication problem with the AV System. For this
     *              type of exception, the AVUploadFileProcessor shows a common message for all implementations of AVScanners.
     * @throws AVInfectedException Exception thrown in case the file is infected conform to the concrete AVScanner's rules. For this
     *              type of exception, the AVUploadFileProcessor shows a common message for all implementations of AVScanners.
     * @throws AVAlertException this is a general Exception which allows each concrete AVScanner object to alert different messages
     *              particularily matching their concrete implemented situations. A method can throw a new AVAlertException with
     *              constructed with a custom message, and that message will be shown to the users as information for the uploaded file.
     */
    ScanResponse scan(UploadedFile uploadedFile) throws AVConnectionException, AVInfectedException, AVAlertException;

}
