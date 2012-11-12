package com.virtual7.employeeAptitudes.view.managed;

import com.virtual7.employeeAptitudes.model.views.AptitudeViewRowImpl;
import com.virtual7.employeeAptitudes.model.views.DepartmentsViewRowImpl;
import com.virtual7.employeeAptitudes.model.views.EmployeesViewRowImpl;
import com.virtual7.employeeAptitudes.model.views.EmployeesToAptitudeViewRowImpl;
import com.virtual7.util.view.ADFUtils;

import com.virtual7.util.view.JSFUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Set;

import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.data.RichTable;


import oracle.adf.view.rich.component.rich.input.RichSelectManyListbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneListbox;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlListBinding;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.model.RowKeySet;

public class ShuttleBean {
    private RichTable emptToAptTable;
    private RichSelectOneListbox empsListbox;
    private RichSelectManyListbox selectManyEmployees;

    public ShuttleBean() {
        super();
    }

    public void createInsert() {

    }

    public void addEmployee(ActionEvent actionEvent) {
        if (getVisibleEmployees().size() != 0) {
            //get the selected rows indices
            BindingContext bctx = BindingContext.getCurrent();
            BindingContainer bindings = bctx.getCurrentBindingsEntry();
            JUCtrlListBinding list = (JUCtrlListBinding)bindings.get("EmployeesView1");
            int[] indices = list.getSelectedIndices();

            DCIteratorBinding emp = ADFUtils.findIterator("EmployeesView1Iterator");
            RowSetIterator empIterator = emp.getRowSetIterator();

            for (int i = 0; i < indices.length; i++) {
                Row row = list.getRowAtRangeIndex(indices[i]);
                empIterator.setCurrentRow(row);
                
                OperationBinding createInsert = ADFUtils.findOperation("CreateWithParams");
                createInsert.execute();
            }

            //refresh iterator
            DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
            empToApt.executeQuery();
            ADFUtils.addPartialTarget(getEmptToAptTable());
        }
    }

    //    public void addEmployee(ActionEvent actionEvent) {
    //        if (getVisibleEmployees().size() != 0) {
    //            //create new row in employees to aptitude, initialize with employee id, aptitude id, department id
    //            OperationBinding createInsert = ADFUtils.findOperation("CreateWithParams");
    //            createInsert.execute();
    //
    //            //insert the row and refresh iterator
    //            DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
    //            empToApt.executeQuery();
    //            ADFUtils.addPartialTarget(getEmptToAptTable());
    //        }
    //    }

    public void removeEmployee(ActionEvent actionEvent) {
        //get the key values for the selected rows
        RowKeySet selectedRows = getEmptToAptTable().getSelectedRowKeys();
        Iterator selRowsiterator = selectedRows.iterator();

        if (selectedRows.size() > 0) {
            //get the employees to aptitude iterator binding and row set iterator
            DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
            RowSetIterator empToAptIterator = empToApt.getRowSetIterator();

            while (selRowsiterator.hasNext()) {
                //get the key value from the selected rows
                Key key = (Key)((List)selRowsiterator.next()).get(0);
                //find the row corresponding to the key in the table and remove it
                Row row = empToAptIterator.getRow(key);
                if (row != null) {
                    row.remove();
                }
            }
            //refresh the table
            empToApt.executeQuery();
            ADFUtils.addPartialTarget(getEmptToAptTable());
            ADFUtils.addPartialTarget(getSelectManyEmployees());
        }
    }

    public void addAllEmployees(ActionEvent actionEvent) {
        if (getVisibleEmployees().size() != 0) {
            DCIteratorBinding employees = ADFUtils.findIterator("EmployeesView1Iterator");
            Row[] empRows = employees.getAllRowsInRange();

            DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
            Row[] empToAptRows = empToApt.getAllRowsInRange();

            for (int i = 0; i < empRows.length; i++) {
                employees.setCurrentRowIndexInRange(i);
                Row currentRow = employees.getCurrentRow();
                boolean isInEmpToApt = false;

                //get the current row from employees and if it is in the emp to apt table set the flag to true
                for (int j = 0; j < empToAptRows.length; j++) {
                    if (((EmployeesViewRowImpl)currentRow).getEmployeeId().getSequenceNumber().intValue() ==
                        ((EmployeesToAptitudeViewRowImpl)empToAptRows[j]).getEmployeeId().intValue()) {
                        isInEmpToApt = true;
                    }
                }
                if (!isInEmpToApt) {
                    OperationBinding createInsert = ADFUtils.findOperation("CreateWithParams");
                    createInsert.execute();
                }
            }

            empToApt.executeQuery();
            ADFUtils.addPartialTarget(getEmptToAptTable());
        }
    }

    public void removeAllEmployees(ActionEvent actionEvent) {

        DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
        Row[] empToAptRows = empToApt.getAllRowsInRange();

        if (empToAptRows.length > 0) {
            for (int i = 0; i < empToAptRows.length; i++) {
                if (empToAptRows[i] != null) {
                    empToAptRows[i].remove();
                }
            }
        }
        ADFUtils.addPartialTarget(getEmptToAptTable());
        ADFUtils.addPartialTarget(getSelectManyEmployees());
    }

    public List<SelectItem> getVisibleEmployees() {
        //get the list of employees
        FacesCtrlListBinding empListBinding =
            (FacesCtrlListBinding)JSFUtils.resolveExpression("#{bindings.EmployeesView1}");
        List<SelectItem> empItems = empListBinding.getItems();

        //get the employees from the table on the left
        DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
        Row[] empToAptRows = empToApt.getAllRowsInRange();

        //store the employees list in a local variable
        List<SelectItem> remainingEmployees = new ArrayList<SelectItem>();
        remainingEmployees.addAll(empItems);
        Iterator<SelectItem> iter = remainingEmployees.iterator();

        //if a given employee is in the table remove it from the emps list
        while (iter.hasNext()) {
            SelectItem selItem = iter.next();
            for (Row r : empToAptRows) {
                String name =
                    ((EmployeesToAptitudeViewRowImpl)r).getFirstName() + " " + ((EmployeesToAptitudeViewRowImpl)r).getLastName();
                if (selItem.getLabel().equals(name)) {
                    iter.remove();
                    break;
                }
            }
        }

        ADFUtils.addPartialTarget(getSelectManyEmployees());
        return remainingEmployees;
    }


    public void setEmptToAptTable(RichTable emptToAptTable) {
        this.emptToAptTable = emptToAptTable;
    }

    public RichTable getEmptToAptTable() {
        return emptToAptTable;
    }

    public void setEmpsListbox(RichSelectOneListbox empsListbox) {
        this.empsListbox = empsListbox;
    }

    public RichSelectOneListbox getEmpsListbox() {
        return empsListbox;
    }

    public void setSelectManyEmployees(RichSelectManyListbox selectManyEmployees) {
        this.selectManyEmployees = selectManyEmployees;
    }

    public RichSelectManyListbox getSelectManyEmployees() {
        return selectManyEmployees;
    }
    
    public boolean isSame() {
        String attrName = "DepartmentName";
        boolean isSame = false;
        // get the currently processed row, using row expression #{row}
        JUCtrlHierNodeBinding row = (JUCtrlHierNodeBinding)JSFUtils.resolveExpression("#{row}");
        JUCtrlHierBinding tableBinding = row.getHierBinding();
        
        int rowRangeIndex = row.getViewObject().getRangeIndexOf(row.getRow());
        Object currentAttrValue = row.getRow().getAttribute(attrName);
        
        if (rowRangeIndex > 0) {
            Object previousAttrValue = tableBinding.getAttributeFromRow(rowRangeIndex - 1, attrName);
            isSame = currentAttrValue != null && currentAttrValue.equals(previousAttrValue);
        } else if (tableBinding.getRangeStart() > 0) {
            // previous row is in previous range, we create separate rowset iterator,
            // so we can change the range start without messing up the table rendering which uses
            // the default rowset iterator
            int absoluteIndexPreviousRow = tableBinding.getRangeStart() - 1;
            RowSetIterator rsi = null;
            try {
                rsi = tableBinding.getViewObject().getRowSet().createRowSetIterator(null);
                rsi.setRangeStart(absoluteIndexPreviousRow);
                Row previousRow = rsi.getRowAtRangeIndex(0);
                Object previousAttrValue = previousRow.getAttribute(attrName);
                isSame = currentAttrValue != null && currentAttrValue.equals(previousAttrValue);
            } finally {
                rsi.closeRowSetIterator();
            }
        }
        
        return isSame;
    }
}
