package com.virtual7.employeeAptitudes.view;

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

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.data.RichTable;


import oracle.adf.view.rich.component.rich.input.RichSelectOneListbox;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlListBinding;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.model.RowKeySet;

public class ShuttleBean {
    private RichTable emptToAptTable;
    private RichSelectOneListbox empsListbox;

    public ShuttleBean() {
        super();
    }

    public void createInsert() {

    }

    public void addEmployee(ActionEvent actionEvent) {
        if (getVisibleEmployees().size() != 0) {
            //create new row in employees to aptitude, initialize with employee id, aptitude id, department id
            OperationBinding createInsert = ADFUtils.findOperation("CreateWithParams");
            createInsert.execute();

            //insert the row and refresh iterator
            DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
            empToApt.executeQuery();
            ADFUtils.addPartialTarget(getEmptToAptTable());
        }
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
            Key key = (Key)((List)selRowsiterator.next()).get(0);
            //find the row corresponding to the key in the table and remove it
            Row row = empToAptIterator.getRow(key);
            row.remove();
        }
        //refresh the table
        empToApt.executeQuery();
        ADFUtils.addPartialTarget(getEmptToAptTable());
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
                    if (((EmployeesViewRowImpl) currentRow).getEmployeeId().getSequenceNumber().intValue() ==
                        ((EmployeesToAptitudeViewRowImpl) empToAptRows[j]).getEmployeeId().intValue()) {
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

        if (empToAptRows.length != 0) {
            for (int i = 0; i < empToAptRows.length; i++) {
                empToAptRows[i].remove();
            }
        }
        ADFUtils.addPartialTarget(getEmptToAptTable());
    }

    public List<SelectItem> getVisibleEmployees() {
        List<SelectItem> remainingEmployees = new ArrayList<SelectItem>();

        //get the list of employees
        FacesCtrlListBinding empListBinding =
            (FacesCtrlListBinding)JSFUtils.resolveExpression("#{bindings.EmployeesView1}");
        List<SelectItem> empItems = empListBinding.getItems();

        //get the employees from the table on the left
        DCIteratorBinding empToApt = ADFUtils.findIterator("EmployeesToAptitudeView1Iterator");
        Row[] empToAptRows = empToApt.getAllRowsInRange();

        //store the employees list in a local variable
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

        ADFUtils.addPartialTarget(getEmpsListbox());
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
}
