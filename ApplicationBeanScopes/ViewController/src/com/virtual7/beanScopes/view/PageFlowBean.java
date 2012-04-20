package com.virtual7.beanScopes.view;

import java.util.Date;

public class PageFlowBean {

    private static int seq = 0;
    private String instanceId;
    private String currentTime;

    private static synchronized String getNextSeqNr() {
        ++seq;
        return "" + seq;
    }

    public PageFlowBean() {
        super();
        instanceId = "PageFlow scope instance no: " + getNextSeqNr();
        Date now = new Date(System.currentTimeMillis());
        currentTime = "Created at: " + now;       
        System.out.println("Constructed instance:" + instanceId);
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }
}
