package com.eurofighter.fileupload.avscanner;

/**
 * Abstract class being the base for all the Exception classes for the AVScanner API
 */
public abstract class AVException extends Exception {

    @SuppressWarnings("compatibility:-8847268954758367964")
    private static final long serialVersionUID = -5607964597788789272L;

    public AVException() {
        super();
    }

    public AVException(Throwable cause) {
        super(cause);
    }

    public AVException(String message, Throwable cause) {
        super(message, cause);
    }

    public AVException(String message) {
        super(message);
    }

}
