package com.virtual7.taskFlowOpps.view;

import javax.faces.event.ValueChangeEvent;

public class TaskFlow4Bean {
    private String param; 
    
    public TaskFlow4Bean() {
        super();
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    public void onValueChange(ValueChangeEvent valueChangeEvent) {
        setParam((String) valueChangeEvent.getNewValue());
    }
}
