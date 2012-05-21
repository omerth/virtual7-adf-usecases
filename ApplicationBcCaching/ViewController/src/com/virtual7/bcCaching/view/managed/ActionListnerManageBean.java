package com.virtual7.bcCaching.view.managed;


import com.virtual7.util.view.ADFUtils;
import com.virtual7.util.view.JSFUtils;

import javax.faces.event.ActionEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;


public class ActionListnerManageBean {

    public ActionListnerManageBean() {
        super();
    }

    public void showInConsole(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("readAndPrintDepartment");
        ob.execute();
    }

    public void insertNewDep(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("insertNewDepartment");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void updateDep(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("updateDepartment");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void deleteDep(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("deleteDepartment");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void executeQuery(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("executeQuery");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void executeQuery1(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("executeQuery1");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void executeQuery2(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("executeQuery2");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void executeQuery3(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("executeQuery3");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void newJDBCshowInConsole(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("readAndPrintDepartmentNewJDBC");
        ob.execute();
    }

    public void newJDBCinsertNewDep(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("insertNewDepartmentNewJDBC");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void newJDBCupdateDep(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("updateDepartmentNewJDBC");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void newJDBCdeleteDep(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("deleteDepartmentNewJDBC");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void executeQuery4(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("executeQuery4");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void executeQuery5(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("executeQuery5");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void executeQuery6(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("executeQuery6");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
    }

    public void executeServiceQuery(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("executeServiceQuery");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t1"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t2"));
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t3"));

        JUCtrlHierBinding juCtrlHierBinding1 =
            (JUCtrlHierBinding)ADFUtils.getBindingContainer().getControlBinding("DepartmentsEOVO");
        JUCtrlHierBinding juCtrlHierBinding =
            (JUCtrlHierBinding)ADFUtils.getBindingContainer().getControlBinding("DepartmentservicevoView");
        juCtrlHierBinding.getChildren();
        Row[] rows = juCtrlHierBinding.getAllRowsInRange();
        for (Row row : rows) {
            row.getAttribute(0);
            row.getAttribute(1);
        }
    }

    public void readAndPrintDepartmentFromService(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("readAndPrintDepartmentService");
        ob.execute();
    }

    public void executeServiceQueryEL(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("executeServiceQueryEL");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t3"));
    }

    public void executeServiceQueryVL(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("executeServiceQueryVL");
        ob.execute();
        ADFUtils.addPartialTarget(JSFUtils.findComponentInRoot("t3"));
    }
}
