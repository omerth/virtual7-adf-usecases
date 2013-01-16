package com.eurofighter.fileupload.av;

public abstract class AVException extends Exception{
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
