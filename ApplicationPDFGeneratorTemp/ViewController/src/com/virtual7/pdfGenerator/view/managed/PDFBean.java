package com.virtual7.pdfGenerator.view.managed;

import com.virtual7.pdfGenerator.view.pdf.XmlStringGenerator;
import com.virtual7.util.view.ADFUtils;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.binding.OperationBinding;

public class PDFBean {
    public PDFBean() {
        super();
    }
    
    
    public void generatePDF(ActionEvent actionEvent) {
        //@SuppressWarnings(actionEvent)
            
        // Get the xml string.
        String xml = XmlStringGenerator.generateXMLString(ADFUtils.findIterator("EmployeesVOIterator"));
        
        // Generate the PDF.
        OperationBinding generatePDF = ADFUtils.findOperation("generatePDF");
        Map params = generatePDF.getParamsMap();
        params.put("xml", xml);
        generatePDF.execute();
    }
}
