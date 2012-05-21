package com.virtual7.xdm.beans;

public class UiState {
    private String currentTF = "/WEB-INF/task-edit-overview-flow.xml#task-edit-overview-flow";
    
    public UiState() {
        super();
    }
    
    public void setCurrentTF(String s) {
        currentTF = s;
    }
    
    public String getCurrentTF() {
        return currentTF;
    }
}
