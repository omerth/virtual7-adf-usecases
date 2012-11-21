package com.virtual7.pdfGenerator.model.pdf;

import com.virtual7.pdfGenerator.model.views.EmployeesVORowImpl;

import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

 /**
  * This class generates a an xml string with the employee names in the employee table.
  */
 public class XmlStringGenerator {
     
     /**
      * Generates the xml string.
      *
      * @param vo the view object of the employees table.
      * @return the xml string.
      */
     public static String generateXMLString(ViewObjectImpl vo) {
         // Append start tags.
         StringBuffer xml = new StringBuffer();
         xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
         xml.append("<ROOT>");
        
         
         // Append content.
         Row[] employeeRows = vo.getAllRowsInRange();
         for (Row r : employeeRows) {
             EmployeesVORowImpl row = (EmployeesVORowImpl) r;
             xml.append("<EMPLOYEE_NAME>");
             xml.append("<EMPLOYEE_FIRST_NAME>" + row.getFirstName() + "</EMPLOYEE_FIRST_NAME>");
             xml.append("<EMPLOYEE_LAST_NAME>" + row.getLastName() + "</EMPLOYEE_LAST_NAME>");
             xml.append("</EMPLOYEE_NAME>");
         }

         // Append end tags.
         
         xml.append("</ROOT>");
         return xml.toString();
     }
 }
