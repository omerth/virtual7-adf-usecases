package com.virtual7.ucmContent.model;

import java.util.HashMap;

public class UCMService {

    private String docName;
    private HashMap<String, String> docContent = new HashMap<String, String>();
    private HashMap<String, String> docContentTemp = new HashMap<String, String>();


    public void selectDocument(String docName) {
        this.docName = docName;

        if (this.docName != null && !"".equals(this.docName)) {
            String content = "Content of " + docName;
            this.docContentTemp.put(this.docName, content);
            this.docContent.put(this.docName, content);
        }
    }

    public String getDocumentContent() {
        if (this.docName != null && !"".equals(this.docName)) {
            return this.docContentTemp.get(this.docName);
        }
        return "";
    }

    public void setDocumentContent(String docContent) {
        if (this.docName != null && !"".equals(this.docName)) {
            this.docContentTemp.put(this.docName, docContent);
        }
    }

    public void saveDocument() {
        if (this.docName != null && !"".equals(this.docName)) {
            this.docContent.put(this.docName, this.docContentTemp.get(this.docName));
        }
    }

    public void cancel() {
        if (this.docName != null && !"".equals(this.docName)) {
            this.docContentTemp.put(this.docName, this.docContent.get(docName));
        }
    }
}
