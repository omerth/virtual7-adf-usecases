package com.virtual7.pdfGenerator.view.managed;


import com.virtual7.pdfGenerator.model.EmployeesAppModuleImpl;
import com.virtual7.util.view.ADFUtils;

import java.io.IOException;
import java.io.OutputStream;

import javax.faces.context.FacesContext;


public class PDFBean {
    public PDFBean() {
        super();
    }
    
    
    public void generatePDF(FacesContext facesContext, OutputStream outputStream) {

        EmployeesAppModuleImpl applicationModule = (EmployeesAppModuleImpl)ADFUtils.getApplicationModuleForDataControl("EmployeesAppModuleDataControl");
        
        // Generate the PDF.
        byte[] pdf = applicationModule.generatePDF();
        
        try {
            outputStream.write(pdf);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
