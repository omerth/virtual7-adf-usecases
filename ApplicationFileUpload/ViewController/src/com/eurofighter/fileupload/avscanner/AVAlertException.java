package com.eurofighter.fileupload.avscanner;

/**
 * General Exception which allows each concrete AVScanner object to alert different messages particularily matching their
 * concrete implemented situations. A method can throw a new AVAlertException with constructed with a custom message,
 * and that message will be shown to the users as information for the uploaded file.
 */
public class AVAlertException extends AVException {

    @SuppressWarnings("compatibility:-7245449920971247126")
    private static final long serialVersionUID = -8129033861815383593L;

    public AVAlertException() {
        super();
    }

    public AVAlertException(Throwable cause) {
        super(cause);
    }

    public AVAlertException(String message, Throwable cause) {
        super(message, cause);
    }

    public AVAlertException(String message) {
        super(message);
    }


}
