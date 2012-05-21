package com.virtual7.exceptionHandler.view;

import com.virtual7.exceptionHandler.view.managed.SessionManagedBean;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.ExceptionHandler;

import oracle.jbo.JboException;


public class CustomExceptionHandler extends ExceptionHandler {

    public CustomExceptionHandler() {
        super();
    }

    public void handleException(FacesContext facesContext, Throwable throwable, PhaseId phaseId) throws Throwable {
        System.out.println("Error from CustomExceptionHandler " + phaseId.getOrdinal());

        SessionManagedBean bean =
            (SessionManagedBean)ADFContext.getCurrent().getSessionScope().get("sessionManagedBean");


        if (bean.getTypeErrorPage().equalsIgnoreCase(bean.getExceptionHandleType())) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ViewHandler vh = fc.getApplication().getViewHandler();
            UIViewRoot view = vh.createView(fc, "/faces/error.jspx");
            fc.setViewRoot(view);
            fc.renderResponse();

            //                ExternalContext ectx = facesContext.getExternalContext();
            //                HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
            //                String url = ectx.getRequestContextPath()+"/faces/error.jspx";
            //
            //                try {
            //                  response.sendRedirect(url);
            //                } catch (Exception ex) {
            //                  ex.printStackTrace();
            //                }
        } else {
            facesContext.addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Exception handler message"));
        }


    }
}
