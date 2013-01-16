package com.eurofighter.fileupload.av.mock;

import com.eurofighter.fileupload.av.AVAlertException;
import com.eurofighter.fileupload.av.AVException;

public class AVWarningException extends AVAlertException{
    public AVWarningException(Throwable cause) {
        super(cause);
    }

    public AVWarningException(String message, Throwable cause) {
        super(message, cause);
    }

    public AVWarningException(String message) {
        super(message);
    }

    public AVWarningException() {
        super();
    }
    
}
