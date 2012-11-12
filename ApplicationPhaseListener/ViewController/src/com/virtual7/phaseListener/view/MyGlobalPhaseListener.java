package com.virtual7.phaseListener.view;

import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;

public class MyGlobalPhaseListener implements PagePhaseListener {
    public MyGlobalPhaseListener() {
        super();
        System.out.println("Global listener initialized");
    }
    
    public void afterPhase(PagePhaseEvent event) {
        System.out.println("Global listener afterPhase - " + Lifecycle.getPhaseName(event.getPhaseId()));
    }

    public void beforePhase(PagePhaseEvent event) {
        System.out.println("Global listener beforePhase - " + Lifecycle.getPhaseName(event.getPhaseId()));
    }
}
