package com.eurofighter.fileupload.av.mock;

import com.eurofighter.fileupload.av.AVAlertException;
import com.eurofighter.fileupload.av.AVException;

public class AVScanErrorException extends AVAlertException{
    public AVScanErrorException(Throwable cause) {
        super(cause);
    }

    public AVScanErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AVScanErrorException(String message) {
        super(message);
    }

    public AVScanErrorException() {
        super();
    }
    
}
