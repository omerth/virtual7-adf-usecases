package com.eurofighter.fileupload.av.mock;

import com.eurofighter.fileupload.av.AVAlertException;
import com.eurofighter.fileupload.av.AVException;

public class AVEncryptedException extends AVAlertException{
    public AVEncryptedException(Throwable cause) {
        super(cause);
    }

    public AVEncryptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AVEncryptedException(String message) {
        super(message);
    }

    public AVEncryptedException() {
        super();
    }
    
}
