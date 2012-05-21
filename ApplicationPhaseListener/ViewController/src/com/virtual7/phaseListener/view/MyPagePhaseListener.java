package com.virtual7.phaseListener.view;

import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.controller.v2.context.PageLifecycleContext;
import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PageController;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.model.OperationBinding;

public class MyPagePhaseListener extends PageController implements PagePhaseListener {
    public MyPagePhaseListener() {
        super();
        System.out.println("Page listener initialized");
    }
    
    public void afterPhase(PagePhaseEvent event) {
        System.out.println("Page listener afterPhase - " + Lifecycle.getPhaseName(event.getPhaseId()));
    }

    public void beforePhase(PagePhaseEvent event) {
        
        System.out.println("Page listener beforePhase - " +  Lifecycle.getPhaseName(event.getPhaseId()));
    }
    
    @Override
    public void initContext(LifecycleContext lifecycleContext) {
        super.initContext(lifecycleContext);
        System.out.println("PageController: initContext");
    }

    @Override
    public void prepareModel(LifecycleContext lifecycleContext) {
        super.prepareModel(lifecycleContext);
        System.out.println("PageController: prepareModel");
    }

    @Override
    public void applyInputValues(LifecycleContext lifecycleContext) {
        super.applyInputValues(lifecycleContext);
        System.out.println("PageController: applyInputValues");
    }

    @Override
    public void validateInputValues(LifecycleContext lifecycleContext) {
        super.validateInputValues(lifecycleContext);
        System.out.println("PageController: validateInputValues");
    }

    @Override
    public void validateModelUpdates(LifecycleContext lifecycleContext) {
        super.validateModelUpdates(lifecycleContext);
        System.out.println("PageController: validateModelUpdates");
    }

    @Override
    public void prepareRender(LifecycleContext lifecycleContext) {
        super.prepareRender(lifecycleContext);
        System.out.println("PageController: prepareRender");
    }

    @Override
    public void processUpdateModel(LifecycleContext lifecycleContext) {
        super.processUpdateModel(lifecycleContext);
        System.out.println("PageController: processUpdateModel");
    }

    @Override
    public void metadataCommit(LifecycleContext lifecycleContext) {
        super.metadataCommit(lifecycleContext);
        System.out.println("PageController: metadataCommit");
    }

    @Override
    public void processComponentEvents(LifecycleContext lifecycleContext) {
        super.processComponentEvents(lifecycleContext);
        System.out.println("PageController: processComponentEvents");
    }

    @Override
    public void reportErrors(PageLifecycleContext pageLifecycleContext) {
        super.reportErrors(pageLifecycleContext);
        System.out.println("PageController: reportErrors");
    }

    @Override
    public boolean invokeEventMethod(PageLifecycleContext pageLifecycleContext, String string) throws Exception {
        System.out.println("PageController: invokeEventMethod");
        return super.invokeEventMethod(pageLifecycleContext, string);
    }

    @Override
    public boolean invokeActionBinding(PageLifecycleContext pageLifecycleContext, String string) {
        System.out.println("PageController: invokeActionBinding");
        return super.invokeActionBinding(pageLifecycleContext, string);
    }

    @Override
    public void initializeMethodParameters(PageLifecycleContext pageLifecycleContext,
                                           OperationBinding operationBinding) {
        super.initializeMethodParameters(pageLifecycleContext, operationBinding);
        System.out.println("PageController: initializeMethodParameters");
    }
}
