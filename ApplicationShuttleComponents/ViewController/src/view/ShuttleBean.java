package view;

import com.virtual7.util.model.BCUtils;
import com.virtual7.util.view.ADFUtils;

import com.virtual7.util.view.JSFUtils;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

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

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class ShuttleBean implements ActionListener {
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

    public void shuttleLeft(ActionEvent actionEvent) {
        RichTreeTable treeTable = this.getTreeTable1();
        //get selected row keys. This could be a single entry or
        //multiple entries dependent on whether the tree table is
        //configured for multi row selection or single row selection
        RowKeySet rks = treeTable.getSelectedRowKeys();
        Iterator rksIterator = rks.iterator();
        //assuming single select use case. Otherwise, this is where you
        //need to add iteration over the entries in the multi select use case
        if (rksIterator.hasNext()) {
            //a key is a list object that containe the node path information
            //for the selected node
            List key = (List)rksIterator.next();
            //            Iterator it = key.iterator();
            //            while (it.hasNext()) {
            //                String string = it.next().toString();
            //            }
            //determine the selected node. Note that the tree table binding is
            //an instance of JUCtrlHierBinding
            JUCtrlHierBinding treeTableBinding = null;
            //We can get the binding information without using EL in our Java,
            //which you always should try to do. Using EL in Java is good to
            //use, but only second best as a solution
            treeTableBinding = (JUCtrlHierBinding)((CollectionModel)treeTable.getValue()).getWrappedData();
            //the row data is represented by the JUCtrlHierNodeBinding class at
            //runtime. We get the node value from the tree binding at runtime
            JUCtrlHierNodeBinding nodeBinding = treeTableBinding.findNodeByKeyPath(key);
            //the JUCtrlHierNodeBinding object allows you to access the row data,
            //so if all you want is to get the selected row data, then you are
            //done already. However, in many cases you need to further
            //distinguish the node, which is what we can do using the
            //HierTypeBinding
            String nodeStuctureDefname = nodeBinding.getHierTypeBinding().getStructureDefName();
            //determine the selected node by the ViewObject name and package
            String employeesDef = "model.views.EmployeestoaptitudeView";
            String departmentsDef = "model.views.DepartmentsToAptitude";
            if (nodeStuctureDefname.equalsIgnoreCase(employeesDef)) {
                //work with location node data
                
//                DCIteratorBinding empIterator = ADFUtils.findIterator("EmployeesView2Iterator");
//                //EmployeesViewRowImpl empRow = (EmployeesViewRowImpl)empIterator.getCurrentRow();
//                
//                DCIteratorBinding deptsIterator = ADFUtils.findIterator("DepartmentsView1Iterator");
//                //DepartmentsViewRowImpl deptRow = (DepartmentsViewRowImpl) deptsIterator.getCurrentRow();
//
                EmployeestoaptitudeViewRowImpl empTreeRow = (EmployeestoaptitudeViewRowImpl) nodeBinding.getRow();
//                Object[] keyVal = {empTreeRow.getEmployeeId()};
//                Key empkey = new Key(keyVal);
//                Key[] keys = {empkey};
//                RowIterator byKeyValues = empIterator.findRowsByKeyValues(keys);
//                EmployeesViewRowImpl displayedEmployeeRow = (EmployeesViewRowImpl) byKeyValues.first();
//                displayedEmployeeRow.setDeleted(new Number(0));
                
                
                
                
//                keyVal[0] = empTreeRow.getDepartmentId();
//                keys[0] = new Key(keyVal);
//                byKeyValues = deptsIterator.findRowsByKeyValues(keys);
//                if (((DepartmentsViewRowImpl) byKeyValues.first()).getDeleted().intValue() == 1) {
//                    ((DepartmentsViewRowImpl) byKeyValues.first()).setDeleted(new Number(0));
//                    ADFUtils.executeOperation("Commit");
//                    deptsIterator.executeQuery();
//                    ADFUtils.addPartialTarget(getDeptList());
//                    
//                }
                
                empTreeRow.remove();
                ADFUtils.executeOperation("Commit");
//                empIterator.executeQuery();
//                ADFUtils.addPartialTarget(getEmpList());
                
                if (nodeBinding.getParent().getChildren() == null) {
                    treeTableBinding.executeQuery();
                }
                ADFUtils.addPartialTarget(getTreeTable1());

            } else if (nodeStuctureDefname.equalsIgnoreCase(departmentsDef)) {
                
//                DCIteratorBinding empIterator = ADFUtils.findIterator("EmployeesView2Iterator");
//                DCIteratorBinding deptsIter = ADFUtils.findIterator("DepartmentsView1Iterator");
//                
//                DCIteratorBinding deptsIterator = ADFUtils.findIterator("DepartmentsView1Iterator");
//                DepartmentsToAptitudeRowImpl deptTreeRow = (DepartmentsToAptitudeRowImpl) nodeBinding.getRow();
//                Object[] keyVal = {deptTreeRow.getDepartmentId()};
//                Key empkey = new Key(keyVal);
//                Key[] keys = {empkey};
//                RowIterator byKeyValues = deptsIterator.findRowsByKeyValues(keys);
//                DepartmentsViewRowImpl deptOnLeft = (DepartmentsViewRowImpl) byKeyValues.first();
//                deptOnLeft.setDeleted(new Number(0));
//                RowIterator detailEmployeesView = deptOnLeft.getEmployeesView();
//                while (detailEmployeesView.hasNext()) {
//                    ((EmployeesViewRowImpl) detailEmployeesView.next()).setDeleted(new Number(0));
//                }


                while (nodeBinding.getChildren() != null && nodeBinding.getChildren().size() > 0) {
                    ArrayList children = nodeBinding.getChildren();
                    JUCtrlHierNodeBinding facesHier = (JUCtrlHierNodeBinding)children.get(children.size() - 1);

                    EmployeestoaptitudeViewRowImpl childRow =
                        (EmployeestoaptitudeViewRowImpl)facesHier.getCurrentRow();
                    childRow.remove();
                }
                ADFUtils.executeOperation("Commit");
                treeTableBinding.executeQuery();
//                deptsIter.executeQuery();
//                empIterator.executeQuery();
//                ADFUtils.addPartialTarget(getDeptList());
//                ADFUtils.addPartialTarget(getEmpList());
                ADFUtils.addPartialTarget(getTreeTable1());
            }
        }
    }

    public void setTreeTable1(RichTreeTable treeTable1) {
        this.treeTable1 = treeTable1;
    }

    public RichTreeTable getTreeTable1() {
        return treeTable1;
    }


    public void processAction(ActionEvent actionEvent) {
        RichTreeTable treeTable = getTreeTable1();
        DCIteratorBinding iterator = ADFUtils.findIterator("DepartmentsToAptitude1Iterator");
        iterator.executeQuery();
        ADFUtils.addPartialTarget(treeTable);
    }

    public void shuttleRight(ActionEvent actionEvent) {
        ADFUtils.executeOperation("createInsert");
        RichTreeTable treeTable = getTreeTable1();
        DCIteratorBinding iterator = ADFUtils.findIterator("DepartmentsToAptitude1Iterator");
        
//        iterator.executeQuery();
//        ADFUtils.addPartialTarget(treeTable);
        
//        DCIteratorBinding empIterator = ADFUtils.findIterator("EmployeesView2Iterator");
//        EmployeesViewRowImpl empRow = (EmployeesViewRowImpl)empIterator.getCurrentRow();
//        
//        DCIteratorBinding deptsIterator = ADFUtils.findIterator("DepartmentsView1Iterator");
//        DepartmentsViewRowImpl deptRow = (DepartmentsViewRowImpl) deptsIterator.getCurrentRow();
//
//        List searchAttributes = new ArrayList();
//        searchAttributes.add("EmployeeId");
//
//        SearchBean search = new SearchBean();
//        search.setSearchAttributes(searchAttributes);
//        search.setSearchString(empRow.getEmployeeId().toString());
//        search.setTree1(treeTable);
//        empRow.setDeleted(new Number(1));
//
//        RowIterator employeesViewIt = deptRow.getEmployeesView();
//        boolean deleteDep = false;
//        while (employeesViewIt.hasNext()) {
//            if ( ((EmployeesViewRowImpl)employeesViewIt.next()).getDeleted().intValue() == 1) {               
//                deleteDep = true;
//            } else {
//                deleteDep = false;
//            }
//        }
//        
//        if (deleteDep) {
//            deptRow.setDeleted(new Number(1));
//            ADFUtils.executeOperation("Commit");
//            deptsIterator.executeQuery();
//            ADFUtils.addPartialTarget(getDeptList());
//        }
            
        ADFUtils.executeOperation("Commit");
        iterator.executeQuery();
        ADFUtils.addPartialTarget(treeTable);
//        empIterator.executeQuery();       
//        ADFUtils.addPartialTarget(getEmpList());
//        search.onSearch(null);

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
