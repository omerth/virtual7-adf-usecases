package com.virtual7.fileUpload.view.upload;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.webapp.ChainedUploadedFileProcessor;


/**
 * Chained Upload File Processor which does the upload to UCM.
 */
public class UCMUploadFileProcessor implements ChainedUploadedFileProcessor {

    private static final ADFLogger LOG = ADFLogger.createADFLogger(UCMUploadFileProcessor.class);

    public UCMUploadFileProcessor() {
        super();
    }

    public void init(Object object) {
        LOG.info("Initialize the UCMUploadFileProcessor");
    }

    public UploadedFile processFile(Object request, UploadedFile uploadedFile) throws IOException {
        String fileName = uploadedFile.getFilename();
        LOG.info("Start uploading the file to UCM:" + fileName);

        // Print the file content if the file is small.
        if (uploadedFile.getLength() < 1024) {
            BufferedReader br = new BufferedReader(new InputStreamReader(uploadedFile.getInputStream()));
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } finally {
                br.close();
            }
        }

        // Simulate random errors to process the file to UCM.
        int rnd = (int)(Math.random() * (double)10);
        if (rnd % 2 == 0) {
            throw new IOException("Problem occured when uploading the file to UCM:" + fileName);
        }

        LOG.log("Done uploading the file to UCM:" + fileName);

        /**
         * Since we did not change anything in the Inputstream we got from the parameter
         * its ok to return the same object. The file argument is backed by the buffer
         * (which was created in the ChainedUploadedFileProcessor which invokes the ChainedUploadedFileProcessor classes)
         * hence subsequent processors will be able to access the stream again.
         */
        return uploadedFile;
    }
}
