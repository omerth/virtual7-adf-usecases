package com.virtual7.ambcPoolingRoot.view.managed;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


public class IndexManagedBean {
    public IndexManagedBean() {
    }

    public void resetSession(ActionEvent actionEvent) {
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        String url = contextPath + "/faces/index.jspx";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException e) {
        }
    }
}
