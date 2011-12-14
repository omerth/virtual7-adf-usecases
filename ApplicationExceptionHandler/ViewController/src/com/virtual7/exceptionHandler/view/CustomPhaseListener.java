package com.virtual7.exceptionHandler.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.controller.v2.lifecycle.ADFLifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;

public class CustomPhaseListener implements PagePhaseListener {
    public CustomPhaseListener() {
        super();
    }

    public void afterPhase(PagePhaseEvent pagePhaseEvent) {
        System.out.println("After " + pagePhaseEvent.getPhaseId() + "(" +
                           ADFLifecycle.getPhaseName(pagePhaseEvent.getPhaseId()) + ")");
//        if (pagePhaseEvent.getPhaseId() == 8) {
//            FacesContext.getCurrentInstance().addMessage(null,
//                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Phase listener message"));
//        }
    }

    public void beforePhase(PagePhaseEvent pagePhaseEvent) {
        System.out.println("Before " + pagePhaseEvent.getPhaseId() + "(" +
                           ADFLifecycle.getPhaseName(pagePhaseEvent.getPhaseId()) + ")");
    }
}
