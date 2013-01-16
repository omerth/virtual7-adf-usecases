package com.eurofighter.fileupload.av;

public abstract class AVAlertException extends AVException{
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
