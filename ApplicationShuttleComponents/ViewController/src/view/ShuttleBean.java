package view;

import com.virtual7.util.model.BCUtils;
import com.virtual7.util.view.ADFUtils;

import com.virtual7.util.view.JSFUtils;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

import java.util.Map;

import javax.faces.event.ActionEvent;

import javax.faces.event.ActionListener;

import oracle.adf.view.rich.component.rich.input.RichSelectOneListbox;

import oracle.jbo.domain.Number;

import model.views.AptitudeViewRowImpl;

import model.views.DepartmentsToAptitudeRowImpl;
import model.views.DepartmentsViewRowImpl;
import model.views.DepartmentsViewRowImpl;
import model.views.EmployeesViewRowImpl;

import model.views.EmployeestoaptitudeViewRowImpl;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSetIterator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

import org.python.parser.ast.For;

public class ShuttleBean {
    private oracle.jbo.domain.Number aptId;
    private oracle.jbo.domain.Number empId;
    private RichTreeTable treeTable1;
    private String searchType = "CONTAIN";
    private RichSelectOneListbox empList;
    private RichSelectOneListbox deptList;

    public ShuttleBean() {
        super();
    }

    public oracle.jbo.domain.Number getAptId() {
        DCIteratorBinding iterator = ADFUtils.findIterator("AptitudeView1Iterator");
        AptitudeViewRowImpl curRow = (AptitudeViewRowImpl)iterator.getCurrentRow();
        aptId = curRow.getId().getSequenceNumber();
        return aptId;
    }

    public oracle.jbo.domain.Number getEmpId() {
        DCIteratorBinding iterator = ADFUtils.findIterator("EmployeesView2Iterator");
        EmployeesViewRowImpl curRow = (EmployeesViewRowImpl)iterator.getCurrentRow();
        empId = curRow.getEmployeeId().getSequenceNumber();
        return empId;
    }

    public void removeEmployeeFromTree(ActionEvent actionEvent) {
        RichTreeTable treeTable = this.getTreeTable1();

        RowKeySet rks = treeTable.getSelectedRowKeys();
        Iterator rksIterator = rks.iterator();

        if (rksIterator.hasNext()) {

            List key = (List)rksIterator.next();
            JUCtrlHierBinding treeTableBinding = null;
            treeTableBinding = (JUCtrlHierBinding)((CollectionModel)treeTable.getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = treeTableBinding.findNodeByKeyPath(key);
            String nodeStuctureDefname = nodeBinding.getHierTypeBinding().getStructureDefName();

            String employeesDef = "model.views.EmployeestoaptitudeView";
            String departmentsDef = "model.views.DepartmentsToAptitude";

            if (nodeStuctureDefname.equalsIgnoreCase(employeesDef)) {

                removeEmployee(treeTableBinding, nodeBinding);

            } else if (nodeStuctureDefname.equalsIgnoreCase(departmentsDef)) {

                //                while (nodeBinding.getChildren() != null && nodeBinding.getChildren().size() > 0) {
                //                    ArrayList children = nodeBinding.getChildren();
                //                    JUCtrlHierNodeBinding facesHier = (JUCtrlHierNodeBinding)children.get(children.size() - 1);
                //
                //                    EmployeestoaptitudeViewRowImpl childRow =
                //                        (EmployeestoaptitudeViewRowImpl)facesHier.getCurrentRow();
                //                    childRow.remove();
                //                }
                //                ADFUtils.executeOperation("Commit");
                //                treeTableBinding.executeQuery();
                //
                //                ADFUtils.addPartialTarget(getTreeTable1());
                removeDepartment(treeTableBinding, nodeBinding);
            }
        }
    }

    //remove department and child employees from tree table

    private void removeDepartment(JUCtrlHierBinding treeTableBinding, JUCtrlHierNodeBinding nodeBinding) {
        // Get all emplyees from the employeestoaptitude related to the department

        // For eafch employee remove it
        while (nodeBinding.getChildren() != null && nodeBinding.getChildren().size() > 0) {
            ArrayList children = nodeBinding.getChildren();
            JUCtrlHierNodeBinding facesHier = (JUCtrlHierNodeBinding)children.get(children.size() - 1);

            EmployeestoaptitudeViewRowImpl childRow = (EmployeestoaptitudeViewRowImpl)facesHier.getCurrentRow();
            childRow.remove();
        }
        ADFUtils.executeOperation("Commit");
        treeTableBinding.executeQuery();

        ADFUtils.addPartialTarget(getTreeTable1());
        
        DepartmentsToAptitudeRowImpl deptRow = (DepartmentsToAptitudeRowImpl) nodeBinding.getRow();
        RowIterator employeestoaptitudeView = deptRow.getEmployeestoaptitudeView();
        //employeestoaptitudeView


        //        DCIteratorBinding childEmps = nodeBinding.getChildIteratorBinding();
        //        RowSetIterator iterator = childEmps.getRowSetIterator();
        //        while (iterator.hasNext()) {
        //            iterator.next().remove();
        //        }
        //        treeTableBinding.executeQuery();
        //        ADFUtils.addPartialTarget(getTreeTable1());
    }

    // Remove the employees from tree table

    private void removeEmployee(JUCtrlHierBinding treeTableBinding, JUCtrlHierNodeBinding nodeBinding) {
        EmployeestoaptitudeViewRowImpl empTreeRow = (EmployeestoaptitudeViewRowImpl)nodeBinding.getRow();

        empTreeRow.remove();

        //when there are no more employees to remove the department is also removed
        if (nodeBinding.getParent().getChildren() == null) {
            treeTableBinding.executeQuery();
        }
        ADFUtils.addPartialTarget(getTreeTable1());
    }

    private void shuttleAllLeft() {
        // Get all departments from the departments to aptitude

        // For each department remove all employees

        // Refresh the iterator for the tree table

        // Refresh the tree table
    }

    public void setTreeTable1(RichTreeTable treeTable1) {
        this.treeTable1 = treeTable1;
    }

    public RichTreeTable getTreeTable1() {
        return treeTable1;
    }

    public void shuttleRight() {
        // Obtain values that should be added for new row.

        // Now insert a new row to DB.

        // Refresh the iterator

        // Set focus on the new created row.

        // Refresh the component by adding it as partial trigger.
    }

    public void addEmployeeToTree(ActionEvent actionEvent) {
        // Add a new row.
        //        OperationBinding op = ADFUtils.findOperation("createInsert");
        //        Map map = op.getParamsMap();
        //        map.put("empId", getEmpId());
        //        map.put("aptId", getAptId());
        //        op.execute();

        ADFUtils.executeOperation("createInsert");
        //if no commit action is performed the inserted rows will not appear in the tree table
        //ADFUtils.executeOperation("Commit");
       
        RichTreeTable treeTable = getTreeTable1();
        DCIteratorBinding deptsToApt = ADFUtils.findIterator("DepartmentsToAptitude1Iterator");
        RowIterator employeestoaptitudeView = ((DepartmentsToAptitudeRowImpl) deptsToApt.getCurrentRow()).getEmployeestoaptitudeView();
        employeestoaptitudeView.reset();
        
        
        //refresh is needed if not the tree table will not be refreshed
        deptsToApt.executeQuery();

        ADFUtils.addPartialTarget(treeTable);

        List searchAttributes = new ArrayList();
        searchAttributes.add("EmployeeId");

        TreeTableUtils.searchTreeTable(treeTable, getEmpId().toString(), "CONTAIN", searchAttributes);

    }

    public void setEmpList(RichSelectOneListbox empList) {
        this.empList = empList;
    }

    public RichSelectOneListbox getEmpList() {
        return empList;
    }

    public void setDeptList(RichSelectOneListbox deptList) {
        this.deptList = deptList;
    }

    public RichSelectOneListbox getDeptList() {
        return deptList;
    }
}
