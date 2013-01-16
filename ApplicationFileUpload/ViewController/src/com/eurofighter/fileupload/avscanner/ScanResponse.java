package com.eurofighter.fileupload.avscanner;

/**
 * A response object from the scan. The response object is intended to return only an informative message of the scan process for case when
 * scan was performed correctly.
 */
public class ScanResponse {

    private String message;

    public ScanResponse() {
        super();
    }

    public ScanResponse(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
