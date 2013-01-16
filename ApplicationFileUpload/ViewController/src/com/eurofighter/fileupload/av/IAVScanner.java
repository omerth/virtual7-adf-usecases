package com.eurofighter.fileupload.av;


import com.eurofighter.fileupload.av.savapi.ScanObject;

import org.apache.myfaces.trinidad.model.UploadedFile;

public interface IAVScanner {
    
    void scan(UploadedFile uploadedFile) throws AVConnectionException, AVInfectedException, AVAlertException;
    void init(Object context);
}
