package com.virtual7.ucmContent.model;

public class UCMDoc {
    private String docId;
    private String docContent;

    public UCMDoc() {
        super();
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    public String getDocContent() {
        return docContent;
    }
}
