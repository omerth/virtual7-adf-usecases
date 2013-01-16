package com.eurofighter.fileupload.avscanner;

/**
 * Exception thrown in case there is a communication problem with the AV System. For this type of exception,
 * the AVUploadFileProcessor shows a common message for all implementations of AVScanners
 */
public class AVConnectionException extends AVException {

    @SuppressWarnings("compatibility:-7545915494881526626")
    private static final long serialVersionUID = 2307040310159852623L;

    public AVConnectionException() {
        super();
    }

    public AVConnectionException(Throwable cause) {
        super(cause);
    }

    public AVConnectionException(String message) {
        super(message);
    }

    public AVConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
