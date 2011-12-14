package com.virtual7.exceptionHandler.view;


import com.virtual7.exceptionHandler.view.managed.SessionManagedBean;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletResponse;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCErrorHandlerImpl;
import oracle.adf.share.ADFContext;


public class CustomErrorHandler extends DCErrorHandlerImpl {
    public CustomErrorHandler() {
        super(true);
    }

    @Override
    public void reportException(DCBindingContainer dCBindingContainer, Exception exception) {
        System.out.println("Error from CustomErrorHandler");
        SessionManagedBean bean =
            (SessionManagedBean)ADFContext.getCurrent().getSessionScope().get("sessionManagedBean");

        if (bean.getTypeErrorPage().equalsIgnoreCase(bean.getExceptionHandleType())) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ViewHandler vh = fc.getApplication().getViewHandler();
            UIViewRoot view = vh.createView(fc, "/faces/error.jspx");
            fc.setViewRoot(view);
            fc.renderResponse();
        } else {
//            FacesContext.getCurrentInstance().addMessage(null,
//                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Exception handler message"));
            super.reportException(dCBindingContainer, exception);
        }
    }

    @Override
    public String getDisplayMessage(BindingContext ctx, Exception th) {
        return "Error handler message";
    }
}
