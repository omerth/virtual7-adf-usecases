package com.virtual7.programaticPivotTableBinding.view.managed.myTable;

import com.virtual7.programaticPivotTableBinding.view.managed.FilterSpec;
import com.virtual7.programaticPivotTableBinding.view.managed.RowsetDataSource;
import com.virtual7.util.view.ADFUtils;

import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.dss.util.DataException;

import oracle.jbo.Row;

public class MyPivotBean {

    private MyPivotModel dataModel;
    private Object[][] rowsetData = null;
    private String[] columnNames = null;
    private HashMap<String, FilterSpec> filterSpecs = new HashMap<String, FilterSpec>();
    private ArrayList<String> colLayout = new ArrayList<String>();
    private ArrayList<String> rowLayout = new ArrayList<String>();
    private ArrayList<String> dataAttributes = new ArrayList<String>();

    public MyPivotBean() {
        super();
        //initDepts();
        //initJobs();
        //dataModel = new MyPivotModel(columnNames, colLayout, rowLayout, dataAttributes, rowsetData, filterSpecs);

        String buildRowsetString = buildRowsetString(new String[] { "DepartmentId", "LastName", "Salary" }, 
                                                     new String[] {"row", "row", "data"}, 
                                                     new String[] {"string", "string", "double"}, 
                                                     "EmployeesView1Iterator");
        System.out.println(buildRowsetString);
 
        dataModel = new MyPivotModel(buildRowsetString, null);
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
        DCIteratorBinding iter = ADFUtils.findIterator(iteratorBinding);
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
}
