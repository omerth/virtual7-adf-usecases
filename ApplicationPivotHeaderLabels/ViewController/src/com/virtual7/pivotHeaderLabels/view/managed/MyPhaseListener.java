package com.virtual7.pivotHeaderLabels.view.managed;

import javax.faces.context.FacesContext;

import javax.faces.event.PhaseId;

import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;

import oracle.adf.controller.v2.lifecycle.Phases;

import oracle.adf.share.ADFContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MyPhaseListener implements PagePhaseListener {
    public MyPhaseListener() {
        super();
    }

    public void afterPhase(PagePhaseEvent pagePhaseEvent) {
        if (pagePhaseEvent.getPhaseId() == PhaseId.RESTORE_VIEW.getOrdinal()) {
            try {
                ExtendedRenderKitService service =
                    (ExtendedRenderKitService)Service.getRenderKitService(FacesContext.getCurrentInstance(),
                                                                          ExtendedRenderKitService.class);
                service.addScript(FacesContext.getCurrentInstance(), "alert('testtestest')");
                ADFContext.getCurrent().getScope("BackingBean").get("ManagedBean");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void beforePhase(PagePhaseEvent pagePhaseEvent) {
    }
}
