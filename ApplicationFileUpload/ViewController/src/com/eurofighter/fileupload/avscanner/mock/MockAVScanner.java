package com.eurofighter.fileupload.avscanner.mock;


import com.eurofighter.fileupload.avscanner.AAVScanner;
import com.eurofighter.fileupload.avscanner.AVAlertException;
import com.eurofighter.fileupload.avscanner.AVInfectedException;
import com.eurofighter.fileupload.avscanner.ScanResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class simulate an antivirus scanner. Will read a file and if detect in that file or in it name one of the words given
 * in a configurable list, will mark that file as infected
 */
public class MockAVScanner extends AAVScanner {

    /**
     * Keyword used to identify different error situations.
     */
    private static final String KEYWORD_INFECTED = "infected";
    private static final String KEYWORD_CONNECTION_ERROR = "connectionError";
    private static final String KEYWORD_CUSTOM_ERROR = "customError";

    /**
     * Default constructor.
     * Is mandatory to have it because the AVScanner classes are instantiated trough reflection using the default constructor.
     */
    public MockAVScanner() {
        super();
    }

    /**
     * Perform the scan.
     *
     * @param fileName
     * @param fileStream
     * @return
     * @throws AVInfectedException
     * @throws AVAlertException
     */
    @Override
    protected ScanResponse performScan(String fileName, InputStream fileStream) throws AVInfectedException,
                                                                                       AVAlertException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (line.indexOf(KEYWORD_INFECTED) != -1) {
                    throw new AVInfectedException("Mock scanner found an infected file:" + fileName);
                } else if (line.indexOf(KEYWORD_CONNECTION_ERROR) != -1) {
                    throw new AVInfectedException("Mock scanner found an connection error in file:" + fileName);
                } else if (line.indexOf(KEYWORD_INFECTED) != -1) {
                    throw new AVAlertException("Custom error message comming from MockAVScanner for file:" + fileName);
                }
            }

        } catch (IOException e) {
            LOG.warning("IO Error while performin scan!", e);
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                LOG.warning("Error while closing the buffered reade!", e);
            }
        }
        return new ScanResponse("MockAVScanner tells file is clean: " + fileName);
    }
}
