package com.virtual7.fileUpload.view.upload;


import com.virtual7.fileUpload.view.avScan.AVServiceException;
import com.virtual7.fileUpload.view.avScan.AVStreamScanner;

import com.virtual7.fileUpload.view.avScan.ScanObject;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.webapp.ChainedUploadedFileProcessor;


/**
 * Chained Upload File Processor which does the Antivirus Scan of the file.
 */
public class AVUploadFileProcessor implements ChainedUploadedFileProcessor {

    private static final ADFLogger LOG = ADFLogger.createADFLogger(AVUploadFileProcessor.class);

    public AVUploadFileProcessor() {
        super();
    }

    public void init(Object object) {
        LOG.info("Initialize the AVUploadFileProcessor");
    }

    public UploadedFile processFile(Object request, UploadedFile uploadedFile) throws IOException {
        String fileName = uploadedFile.getFilename();
        LOG.info("Start scanning for Virus the uploaded file:" + fileName);

        //        AVStreamScanner scanner = AVStreamScanner.getInstance("iancu-pc", "4711");
        //        String resp;
        //        try {
        //            resp = scanner.getStreamScanReponse(uploadedFile.getInputStream(), fileName, false);
        //            System.out.println("AV Response:" + resp);
        //
        //            // Check to see if the response indicates ok or alert.
        //            ScanObject respObj = new ScanObject();
        //            respObj.updateScanState(resp);
        //            if (respObj.isAlert() || respObj.isWarning()) {
        //                // Alert so throw exception.
        //                // TODO: move the file to virus vault.
        //
        //                // Depending on the alert show the correct message.
        //                String msg = "File did not pass AV scan!";
        //                throw new IOException(msg);
        //            }
        //        } catch (AVServiceException e) {
        //            throw new IOException(e);
        //        }

        BufferedReader br = new BufferedReader(new InputStreamReader(uploadedFile.getInputStream()));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.indexOf("virus") != -1) {
                    throw new IOException("Virus found in file " + fileName + "!\n");
                }
            }
        } finally {
            br.close();
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
