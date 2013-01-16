package com.eurofighter.fileupload.avscanner;

/**
 * Exception thrown in case the file is infected conform to the concrete AVScanner's rules. For this type of exception,
 * the AVUploadFileProcessor shows a common message for all implementations of AVScanners
 */
public class AVInfectedException extends AVAlertException {

    @SuppressWarnings("compatibility:-6361336365025250191")
    private static final long serialVersionUID = 95410347186577638L;

    public AVInfectedException() {
        super();
    }

    public AVInfectedException(Throwable cause) {
        super(cause);
    }

    public AVInfectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AVInfectedException(String message) {
        super(message);
    }

}
