package com.virtual7.exceptionHandler.view.managed;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import oracle.adf.share.ADFContext;

public class ContentManagedBean {
    public ContentManagedBean() {
    }

    public void generateException() throws Throwable {
        throw new Throwable("Test exception from the backing bean !!!");
    }

    public String getDummyText() {
        String s = null;
        s.toLowerCase();
        return "Dummy text";
    }

    public String getHandledDummyText() {
        try {
            String s = null;
            s.toLowerCase();
            return "Handled dummy text";
        } catch (Exception e) {
            SessionManagedBean bean =
                (SessionManagedBean)ADFContext.getCurrent().getSessionScope().get("sessionManagedBean");

            if (bean.getTypeErrorPage().equalsIgnoreCase(bean.getExceptionHandleType())) {
                String s = null;
                s.toLowerCase();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Handled exception from manage bean at render time"));
            }

            return "Handled dummy text";
        }
    }
}
