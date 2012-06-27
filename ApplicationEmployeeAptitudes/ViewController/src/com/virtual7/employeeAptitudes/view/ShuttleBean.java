package com.virtual7.employeeAptitudes.view;

import com.virtual7.employeeAptitudes.model.views.AptitudeViewRowImpl;
import com.virtual7.employeeAptitudes.model.views.DepartmentsViewRowImpl;
import com.virtual7.employeeAptitudes.model.views.EmployeesViewRowImpl;
import com.virtual7.employeeAptitudes.model.views.EmployeesToAptitudeViewRowImpl;
import com.virtual7.util.view.ADFUtils;

import com.virtual7.util.view.JSFUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Set;

import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlListBinding;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.model.RowKeySet;

public class ShuttleBean {
    private RichTable emptToAptTable;

    public ShuttleBean() {
        super();
    }

    public void createInsert() {

    }

    public void addEmployee(ActionEvent actionEvent) {
        //create new row in employees to aptitude, initialize with employee id, aptitude id, department id
        OperationBinding createInsert = ADFUtils.findOperation("CreateWithParams");
        createInsert.execute();

        //insert the row and refresh iterator
        DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
        empToApt.executeQuery();
        ADFUtils.addPartialTarget(getEmptToAptTable());
    }

    public void removeEmployee(ActionEvent actionEvent) {
        //get the key values for the selected rows 
        RowKeySet selectedRows = getEmptToAptTable().getSelectedRowKeys();
        Iterator selRowsiterator = selectedRows.iterator();
            
        //get the employees to aptitude iterator binding and row set iterator
        DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
        RowSetIterator empToAptIterator = empToApt.getRowSetIterator();
        
        while (selRowsiterator.hasNext()) {
            //get the key value from the selected rows
            Key key = (Key) ((List)selRowsiterator.next()).get(0);
            //find the row corresponding to the key in the table and remove it
            Row row = empToAptIterator.getRow(key);
            row.remove();
        }
        //refresh the table
        empToApt.executeQuery();
        ADFUtils.addPartialTarget(getEmptToAptTable());
    }

    public void addAllEmployees(ActionEvent actionEvent) {
        DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
        DCIteratorBinding employees = ADFUtils.findIterator("EmployeesView1Iterator");
        Row[] empRows = employees.getAllRowsInRange();       

        for (int i = 0; i < empRows.length; i++) {
            employees.setCurrentRowIndexInRange(i);
            OperationBinding createInsert = ADFUtils.findOperation("CreateWithParams");
            createInsert.execute(); 
        }

        
        empToApt.executeQuery();
        ADFUtils.addPartialTarget(getEmptToAptTable());
    }

    public void removeAllEmployees(ActionEvent actionEvent) {
        
        DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
        Row[] empToAptRows = empToApt.getAllRowsInRange();

        for (int i = 0; i < empToAptRows.length; i++) {
            empToAptRows[i].remove();
        }
        
        ADFUtils.addPartialTarget(getEmptToAptTable());
    }
    
    public String getVisibleEmployees() {
        FacesCtrlListBinding empItems = (FacesCtrlListBinding) JSFUtils.resolveExpression("#{bindings.EmployeesView1.items}");
        DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
        Row[] empToAptRows = empToApt.getAllRowsInRange();
        
//        for (int i = 0; i < empRows.length; i++) {
//            for (int j = 0; j < empToAptRows.length; j++) {
//                EmployeesViewRowImpl empCurRow = (EmployeesViewRowImpl) empRows[i];
//                if (empCurRow.getEmployeeId().getSequenceNumber() == 
//                    ((EmployeesToAptitudeViewRowImpl) empToAptRows[j]).getEmployeeId()) {
//                        return null;
//                } else {
//                    return empCurRow.getFirstName() + " " + empCurRow.getLastName();
//                }
//            }
//        }
        return null;
    }
    
//    public Number getEmployeeId() {
//        //returns the employee id for the creat insert method
//        DCIteratorBinding employees = ADFUtils.findIterator("EmployeesView1Iterator");
//        EmployeesViewRowImpl empRow = (EmployeesViewRowImpl) employees.getCurrentRow();
//        Number empId = empRow.getEmployeeId().getSequenceNumber();
//        return empId;
//    }
//    
//    public Number getAptitudeId() {
//        //returns the aptitude id for the creat insert method
//        DCIteratorBinding aptitudes = ADFUtils.findIterator("AptitudeView1Iterator");
//        AptitudeViewRowImpl aptRow = (AptitudeViewRowImpl) aptitudes.getCurrentRow();
//        return aptRow.getId().getSequenceNumber();
//    }
//    
//    public Number getDepartmentId() {
//        //returns the department id for the creat insert method
//        DCIteratorBinding departments = ADFUtils.findIterator("DepartmentsView1Iterator");
//        DepartmentsViewRowImpl deptRow = (DepartmentsViewRowImpl) departments.getCurrentRow();
//        return deptRow.getDepartmentId().getSequenceNumber();
//    }

//    public boolean isRendered(Object key) {
//        
//        String attrName = (String)key;
//        boolean isSame = false;
//        // get the currently processed row, using row expression #{row}
//        JUCtrlHierNodeBinding row = (JUCtrlHierNodeBinding) JSFUtils.resolveExpression("#{row)");
//        JUCtrlHierBinding tableBinding = row.getHierBinding();
//        
//        int rowRangeIndex = row.getViewObject().getRangeIndexOf(row.getRow());
//        Object currentAttrValue = row.getRow().getAttribute(attrName);
//        
//        if (rowRangeIndex > 0) {
//            Object previousAttrValue = tableBinding.getAttributeFromRow(rowRangeIndex - 1, attrName);
//            isSame = currentAttrValue != null && currentAttrValue.equals(previousAttrValue);
//        } else if (tableBinding.getRangeStart() > 0) {
//            // previous row is in previous range, we create separate rowset iterator,
//            // so we can change the range start without messing up the table rendering which uses
//            // the default rowset iterator
//            int absoluteIndexPreviousRow = tableBinding.getRangeStart() - 1;
//            RowSetIterator rsi = null;
//            try {
//                rsi = tableBinding.getViewObject().getRowSet().createRowSetIterator(null);
//                rsi.setRangeStart(absoluteIndexPreviousRow);
//                Row previousRow = rsi.getRowAtRangeIndex(0);
//                Object previousAttrValue = previousRow.getAttribute(attrName);
//                isSame = currentAttrValue != null && currentAttrValue.equals(previousAttrValue);
//            } finally {
//                rsi.closeRowSetIterator();
//            }
//        }
//        return isSame;
//    }

    public void setEmptToAptTable(RichTable emptToAptTable) {
        this.emptToAptTable = emptToAptTable;
    }

    public RichTable getEmptToAptTable() {
        return emptToAptTable;
    }
}
