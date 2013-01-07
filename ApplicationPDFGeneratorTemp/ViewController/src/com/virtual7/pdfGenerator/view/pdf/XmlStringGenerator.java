package com.virtual7.pdfGenerator.view.pdf;

import com.virtual7.pdfGenerator.model.views.EmployeesVORowImpl;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Row;


/**
 * This class generates a an xml string with the employee names in the employee table.
 */
public class XmlStringGenerator {
    
    /**
     * Generates the xml string.
     *
     * @param iterBinding the iterator binding of the employees table.
     * @return the xml string.
     */
    public static String generateXMLString(DCIteratorBinding iterBinding) {
        // Append start tags.
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<ROOT>");
        xml.append("<EMPLOYEE_NAME>");
        
        // Append content.
        Row[] employeeRows = iterBinding.getAllRowsInRange();
        for (Row r : employeeRows) {
            EmployeesVORowImpl row = (EmployeesVORowImpl) r;
            xml.append("<EMPLOYEE_FIRST_NAME>" + row.getFirstName() + "</EMPLOYEE_FIRST_NAME>");
            xml.append("<EMPLOYEE_LAST_NAME>" + row.getLastName() + "</EMPLOYEE_LAST_NAME>");
        }

        // Append end tags.
        xml.append("</EMPLOYEE_NAME>");
        xml.append("</ROOT>");
        return xml.toString();
    }
}
