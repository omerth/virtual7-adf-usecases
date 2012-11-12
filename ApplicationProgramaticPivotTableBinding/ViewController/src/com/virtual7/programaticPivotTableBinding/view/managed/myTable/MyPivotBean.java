package com.virtual7.programaticPivotTableBinding.view.managed.myTable;

import com.virtual7.programaticPivotTableBinding.view.managed.normal.FilterSpec;
import com.virtual7.programaticPivotTableBinding.view.managed.normal.RowsetDataSource;
import com.virtual7.util.view.ADFUtils;

import com.virtual7.util.view.JSFUtils;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCControlBindingDef;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;
import oracle.adf.view.faces.bi.model.DataModel;

import oracle.adfdt.model.binding.BindingInfo;

import oracle.adfinternal.view.faces.dvt.model.binding.pivotTable.ActivePivotTableModel;
import oracle.adfinternal.view.faces.dvt.model.binding.pivotTable.FacesPivotTableBinding;

import oracle.adfinternal.view.faces.dvt.model.binding.pivotTable.FacesPivotTableBindingDef;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;
import oracle.binding.OperationBinding;

import oracle.dss.dataView.LocalDataSource;
import oracle.dss.util.CubeDataDirector;
import oracle.dss.util.DataException;

import oracle.dss.util.DataMap;
import oracle.dss.util.DataSource;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerMetadataMap;

import oracle.dss.util.LayerOutOfRangeException;

import oracle.jbo.Row;

public class MyPivotBean {
    //All the comented out code belongs to an initial implementation that worked, but had to be changed due to the fact 
    //there was a requirement for sums to be displayed in the pivot table; 

    private MyPivotModel dataModel;
    private MyPivotModel dynamicDataModel;
    private Object[][] rowsetData = null;
    private String[] columnNames = null;
    private HashMap<String, FilterSpec> filterSpecs = new HashMap<String, FilterSpec>();
    private ArrayList<String> colLayout = new ArrayList<String>();
    private ArrayList<String> rowLayout = new ArrayList<String>();
    private ArrayList<String> dataAttributes = new ArrayList<String>();
    private UIPivotTable pivotTable;
    private String dataItemForPageDef = "Salary";

    public MyPivotBean() {
        super();
        //initDepts();
        //initJobs();
        //dataModel = new MyPivotModel(columnNames, colLayout, rowLayout, dataAttributes, rowsetData, filterSpecs);

        String buildRowsetString = buildRowsetString(new String[] { "DepartmentId", "LastName", "Salary" }, 
                                                     new String[] {"row", "row", "data"}, 
                                                     new String[] {"string", "string", "double"}, 
                                                     "EmployeesView1Iterator");
        dataModel = new MyPivotModel(buildRowsetString, null);

        OperationBinding op1 = ADFUtils.getBindingContainer().getOperationBinding("changeDynamicQuerry");
        if (ADFUtils.getBindingContainer().getOperationBinding("changeDynamicQuerry") != null) {
            OperationBinding op = ADFUtils.findOperation("changeDynamicQuerry");
            Map map = op.getParamsMap();
            map.put("querry", "select * from employees");
            op.execute();
        }
        String dynamicRowsetString = buildRowsetString(new String[] { "DEPARTMENT_ID", "LAST_NAME", "SALARY" }, 
                                                     new String[] {"row", "row", "data"}, 
                                                     new String[] {"string", "string", "double"}, 
                                                     "DynamicVO1Iterator");
        dynamicDataModel = new MyPivotModel(dynamicRowsetString, null);
    }
    
    private String buildRowsetString(String[] colNames, String[] cols, String[] colTypes, String iteratorBinding) {
        String cn = "";
        String c = "";
        String ct = "";
        for (int i = 0; i < colNames.length; i++) {
            if (i != colNames.length - 1) {
                cn = cn + colNames[i] + ", ";
                c = c + cols[i] + ", ";
                ct = ct + colTypes[i] + ", ";
            } else {
                cn = cn + colNames[i];
                c = c + cols[i];
                ct = ct + colTypes[i];
            }
        }
        return cn + "\n" + c + "\n" + ct + "\n" + buildRowsetData(iteratorBinding, colNames);
    }
    
    private String buildRowsetData(String iteratorBinding, String[] attNames) {
        if (ADFUtils.getBindingContainer().get(iteratorBinding) != null) {
        DCIteratorBinding iter = ADFUtils.findIterator(iteratorBinding);
        //iter.executeQuery();
        Row[] allRowsInRange = iter.getAllRowsInRange();
        String data = "";
        for (int i = 0; i < allRowsInRange.length; i++) {
            String row = "";
            for (int j = 0; j < attNames.length; j++) {
                if (j != attNames.length - 1) {
                    row = row + allRowsInRange[i].getAttribute(attNames[j]) + ", ";
                } else {
                    row = row + allRowsInRange[i].getAttribute(attNames[j]) + "\n";
                }
            }
            data = data + row;
        }
        return data;
        }
        return "";
    }
    
    
//    private Object[][] buildRowsetData(String iteratorBinding, String[] attNames) {
//        DCIteratorBinding iter = ADFUtils.findIterator(iteratorBinding);
//        Row[] allRowsInRange = iter.getAllRowsInRange();
//        Object[][] rowsetData = new Object[allRowsInRange.length][];
//        for (int i = 0; i < allRowsInRange.length; i++) {
//            Object[] data = new Object[attNames.length];
//            for (int j = 0; j < attNames.length; j++) {
//                data[j] = allRowsInRange[i].getAttribute(attNames[j]);
//            }
//            rowsetData[i] = data;
//        }
//        return rowsetData;
//    }

    public MyPivotModel getDataModel() {
        return dataModel;
    }

    public void valueChanged(ValueChangeEvent valueChangeEvent) {
        if ("0".equals(valueChangeEvent.getNewValue())) {
//            initDepts();
//            dataModel.setDataSource(buildDS());
            String buildRowsetString = buildRowsetString(new String[] { "DepartmentId", "LastName", "Salary" }, 
                                                         new String[] {"row", "row", "data"}, 
                                                         new String[] {"string", "string", "double"}, 
                                                         "EmployeesView1Iterator");
            System.out.println(buildRowsetString);
            
            dataModel = new MyPivotModel(buildRowsetString, null);
            dataModel.getRowsetDataSource().setTotalsEnabled(false);
        } else if ("1".equals(valueChangeEvent.getNewValue())) {
//            initJobs();
//            dataModel.setDataSource(buildDS());
            String buildRowsetString = buildRowsetString(new String[] { "JobId", "LastName", "Salary" }, 
                                             new String[] {"row", "row", "data"}, 
                                             new String[] {"string", "string", "double"}, 
                                             "EmployeesView1Iterator");
            System.out.println(buildRowsetString);
     
            dataModel = new MyPivotModel(buildRowsetString, null);
            dataModel.getRowsetDataSource().setTotalsEnabled(false);
        } else if ("2".equals(valueChangeEvent.getNewValue())) {
            String buildRowsetString = buildRowsetString(new String[] { "DepartmentId", "LastName", "Salary" }, 
                                                         new String[] {"row", "row", "data"}, 
                                                         new String[] {"string", "string", "double"}, 
                                                         "EmployeesView1Iterator");
            System.out.println(buildRowsetString);
            
            dataModel = new MyPivotModel(buildRowsetString, null);
            dataModel.getRowsetDataSource().setTotalsEnabled(true);
        } else {
            String buildRowsetString = buildRowsetString(new String[] { "JobId", "LastName", "Salary" }, 
                                             new String[] {"row", "row", "data"}, 
                                             new String[] {"string", "string", "double"}, 
                                             "EmployeesView1Iterator");
            System.out.println(buildRowsetString);
            
            dataModel = new MyPivotModel(buildRowsetString, null);
            dataModel.getRowsetDataSource().setTotalsEnabled(true);
        }
    }

//    private RowsetDataSource buildDS() {
//        RowsetDataSource rs = new RowsetDataSource();
//        rs.setFilterSpecs(filterSpecs);
//        rs.setData(rowsetData);
//        rs.setColumns(columnNames);
//        rs.setDataAttributes(dataAttributes);
//        rs.setColumnLayout(colLayout);
//        rs.setRowLayout(rowLayout);
//        return rs;
//    }
//
//    private void initDepts() {
//        String[] cols = { "DepartmentId", "JobId", "LastName", "Salary" };
//        columnNames = cols;
//        rowLayout.clear();
//        colLayout.clear();
//        dataAttributes.clear();
//        filterSpecs.clear();
//        rowLayout.add("DepartmentId");
//        rowLayout.add("LastName");
//        colLayout.add("MeasDim");
//        dataAttributes.add("Salary");
//        DCIteratorBinding iter = ADFUtils.findIterator("EmployeesView1Iterator");
//        Row[] allRowsInRange = iter.getAllRowsInRange();
//        rowsetData = new Object[allRowsInRange.length][];
//        for (int i = 0; i < allRowsInRange.length; i++) {
//            Object[] data =
//            { allRowsInRange[i].getAttribute("DepartmentId"), allRowsInRange[i].getAttribute("JobId"),
//              allRowsInRange[i].getAttribute("LastName"), allRowsInRange[i].getAttribute("Salary") };
//            rowsetData[i] = data;
//        }
//    }
//
//    private void initJobs() {
//        rowLayout.clear();
//        colLayout.clear();
//        dataAttributes.clear();
//        filterSpecs.clear();
//        rowLayout.add("JobId");
//        rowLayout.add("LastName");
//        colLayout.add("MeasDim");
//        dataAttributes.add("Salary");
//    }

    public void generateVO(ActionEvent actionEvent) throws EdgeOutOfRangeException, LayerOutOfRangeException {
        OperationBinding op = ADFUtils.findOperation("changeDynamicQuerry");
        Map map = op.getParamsMap();
        map.put("querry", "select * from employees");
        op.execute();    
    }

    public void dynamicValueChanged(ValueChangeEvent valueChangeEvent) {
        if ("0".equals(valueChangeEvent.getNewValue())) {
            String buildRowsetString = buildRowsetString(new String[] { "DEPARTMENT_ID", "LAST_NAME", "SALARY" }, 
                                                         new String[] {"row", "row", "data"}, 
                                                         new String[] {"string", "string", "double"}, 
                                                         "DynamicVO1Iterator");
            System.out.println(buildRowsetString);
            
            dynamicDataModel = new MyPivotModel(buildRowsetString, null);
            dynamicDataModel.getRowsetDataSource().setTotalsEnabled(false);
        } else if ("1".equals(valueChangeEvent.getNewValue())) {
            String buildRowsetString = buildRowsetString(new String[] { "JOB_ID", "LAST_NAME", "SALARY" }, 
                                             new String[] {"row", "row", "data"}, 
                                             new String[] {"string", "string", "double"}, 
                                             "DynamicVO1Iterator");
            System.out.println(buildRowsetString);
        
            dynamicDataModel = new MyPivotModel(buildRowsetString, null);
            dynamicDataModel.getRowsetDataSource().setTotalsEnabled(false);
        } else if ("2".equals(valueChangeEvent.getNewValue())) {
            String buildRowsetString = buildRowsetString(new String[] { "DEPARTMENT_ID", "LAST_NAME", "SALARY" }, 
                                                         new String[] {"row", "row", "data"}, 
                                                         new String[] {"string", "string", "double"}, 
                                                         "DynamicVO1Iterator");
            System.out.println(buildRowsetString);
            
            dynamicDataModel = new MyPivotModel(buildRowsetString, null);
            dynamicDataModel.getRowsetDataSource().setTotalsEnabled(true);
        } else {
            String buildRowsetString = buildRowsetString(new String[] { "JOB_ID", "LAST_NAME", "SALARY" }, 
                                             new String[] {"row", "row", "data"}, 
                                             new String[] {"string", "string", "double"}, 
                                             "DynamicVO1Iterator");
            System.out.println(buildRowsetString);
            
            dynamicDataModel = new MyPivotModel(buildRowsetString, null);
            dynamicDataModel.getRowsetDataSource().setTotalsEnabled(true);
        }
    }

    public MyPivotModel getDynamicDataModel() {
        return dynamicDataModel;
    }

    public void setPivotTable(UIPivotTable pivotTable) {
        this.pivotTable = pivotTable;
    }

    public UIPivotTable getPivotTable() {
        return pivotTable;
    }

    public void setDataItemForPageDef(String dataItemForPageDef) {
        this.dataItemForPageDef = dataItemForPageDef;
    }

    public String getDataItemForPageDef() {
        return dataItemForPageDef;
    }
}
