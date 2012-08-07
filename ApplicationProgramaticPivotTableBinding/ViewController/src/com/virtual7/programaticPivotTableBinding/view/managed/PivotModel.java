package com.virtual7.programaticPivotTableBinding.view.managed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import oracle.adf.view.faces.bi.model.PivotTableModel;

import oracle.dss.util.DataException;
import oracle.dss.util.transform.total.AggLocation;
import oracle.dss.util.transform.total.AggMethod;
import oracle.dss.util.transform.total.AggOptions;
import oracle.dss.util.transform.total.AggSpec;
import oracle.dss.util.transform.total.AggType;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class PivotModel extends PivotTableModel {
    public PivotModel() {
        super();
        loadData();
    }

    public void loadData() {
        System.out.println("Loading new rowset data - " + (new Date()).toString());
        Object[][] rowsetData = null;
        String[] columnNames = null;
        HashMap<String, FilterSpec> filterSpecs = 
            new HashMap<String, FilterSpec>();
        ArrayList<String> colLayout = new ArrayList<String>();
        ArrayList<String> rowLayout = new ArrayList<String>();
        ArrayList<String> dataAttributes = new ArrayList<String>();
        ArrayList<AggSpec[]> aggSpecs = new ArrayList<AggSpec[]>();
        int[] columnDataTypes = null;

        ArrayList<String[]> lines = getRowsetData();

        // fetch column names from first line in csv file           
        columnNames = (String[])lines.get(0);
        for (int i = 0; i < columnNames.length; i++)
            columnNames[i] = columnNames[i].trim();

        int numCols = columnNames.length;
        int startAt = 1;

        String[] totalLine = null;
        String[] layoutLine = null;
        String[] typeLine = null;

        for (int i = 1; i < 4 && i < lines.size(); i++) {
            String[] _line = lines.get(i);
            if (isTypeLine(_line)) {
                typeLine = _line;
                startAt++;
            } else if (isLayoutLine(_line)) {
                layoutLine = _line;
                startAt++;
            } else if (isTotalLine(_line)) {
                totalLine = _line;
                startAt++;
            } else
                break;
        }

        columnDataTypes = new int[numCols];
        // determine column data types
        if (typeLine != null) {
            // check to see if the second line of data defines data types!
            for (int j = 0; j < numCols; j++) {
                String value = typeLine[j];
                if (value.trim().equalsIgnoreCase("double")) {
                    columnDataTypes[j] = PivotModel.DOUBLE_TYPE;
                    dataAttributes.add(columnNames[j]); // numbers are interpreted as data items by default!!
                    filterSpecs.put(columnNames[j], 
                                    new FilterSpec(columnNames[j], j, (long)0, 
                                                   (long)0, (long)0, (long)0));
                } else if (value.trim().equalsIgnoreCase("string")) {
                    columnDataTypes[j] = PivotModel.STRING_TYPE;
                } else if (value.trim().equalsIgnoreCase("date")) {
                    columnDataTypes[j] = PivotModel.DATE_TYPE;
                } else if (value.trim().equalsIgnoreCase("boolean")) {
                    columnDataTypes[j] = PivotModel.BOOLEAN_TYPE;
                }
            }
        } else {
            // determine data types from first line of data!
            String[] firstLine = lines.get(startAt);
            for (int j = 0; j < numCols; j++) {
                String value = firstLine[j];
                if (isDouble(value)) {
                    columnDataTypes[j] = PivotModel.DOUBLE_TYPE;
                    dataAttributes.add(columnNames[j]); // numbers are interpreted as data items by default!!
                    filterSpecs.put(columnNames[j], 
                                    new FilterSpec(columnNames[j], j, (long)0, 
                                                   (long)0, (long)0, (long)0));
                } else if (isDate(value)) {
                    columnDataTypes[j] = PivotModel.DATE_TYPE;
                } else if (isBoolean(value)) {
                    columnDataTypes[j] = PivotModel.BOOLEAN_TYPE;
                } else {
                    columnDataTypes[j] = PivotModel.STRING_TYPE;
                }
            }
        }
//
        // determine layout 
        if (layoutLine != null) {
            dataAttributes.clear(); // ignore default data item mapping!
            colLayout.add(BaseProjectionImpl.MEASDIM); // put the measure dimension on the column edge
            for (int j = 0; j < numCols; j++) {
                String value = layoutLine[j];
                String colName = columnNames[j];
                if (value.trim().equalsIgnoreCase("COLUMN") || 
                    value.equalsIgnoreCase("COL"))
                    colLayout.add(colName);
                else if (value.trim().equalsIgnoreCase("ROW"))
                    rowLayout.add(colName);
                else if (value.trim().equalsIgnoreCase("DATA"))
                    dataAttributes.add(colName);
            }
        } else {
              colLayout.add(BaseProjectionImpl.MEASDIM); // put the measure dimension on the column edge
              int nextItem;
              if (columnNames.length - dataAttributes.size() == 1)
                  nextItem = 
                          0; // don't add items to the column edge, if there is only 1 item!
              else
                  nextItem = 
                          addItemsToLayout(colLayout, columnNames, columnDataTypes, 
                                           0, 1);
              addItemsToLayout(rowLayout, columnNames, columnDataTypes, nextItem, 
                               columnNames.length - 1);
        }
//
        // determine agg specs
        if (totalLine != null) {
            for (int j = 0; j < numCols; j++) {
                AggType aggType = getAggType(totalLine[j]);
                // don't add agg types for data items
                if (!(dataAttributes.contains(columnNames[j]) || 
                      aggType == AggType.NONE))
                    aggSpecs.add(new AggSpec[] { new AggSpec(columnNames[j], 
                                                             new MemberInterfaceImpl(columnNames[j] + 
                                                                                     " Total (" + 
                                                                                     totalLine[j].trim() + 
                                                                                     ")"), 
                                                             new AggOptions(AggLocation.AFTER), 
                                                             new AggMethod[] { new AggMethod(aggType) }) });
            }
        } else {
            for (int j = 0; j < numCols; j++) {
                int type = columnDataTypes[j];
                if ((type == PivotModel.STRING_TYPE) && 
                    !(dataAttributes.contains(columnNames[j])))
                    aggSpecs.add(new AggSpec[] { new AggSpec(columnNames[j], 
                                                             new MemberInterfaceImpl(columnNames[j] + 
                                                                                     " Total"), 
                                                             new AggOptions(AggLocation.AFTER), 
                                                             new AggMethod[] { new AggMethod(AggType.SUM) }) });
            }
        }

        rowsetData = new Object[lines.size() - startAt][];
        for (int i = startAt; i < lines.size(); i++) {
            String[] values = (String[])lines.get(i);
            Object[] rowData = new Object[values.length];
            for (int j = 0; j < values.length; j++) {
                switch (columnDataTypes[j]) {
                case PivotModel.DOUBLE_TYPE:
                    rowData[j] = toDouble(values[j]);
                    updateFilterValues(filterSpecs.get(columnNames[j]), 
                                       (Double)rowData[j]);
                    break;
              case PivotModel.DATE_TYPE:
                    rowData[j] = toDate(values[j]);
                    break;
              case PivotModel.BOOLEAN_TYPE:
                    rowData[j] = toBoolean(values[j]);
                     break;             
                case PivotModel.STRING_TYPE:
                default:
                    rowData[j] = values[j].trim();
                    break;
                }
            }
            rowsetData[i - startAt] = rowData;
        }
        
        /*
         * System.out.println("\nrowset data\n");
        for (int i = 0; i < rowsetData.length; i++)
        {
          for (int j = 0; j < rowsetData[i].length; j++)
              System.out.println(rowsetData[i][j].toString());
        }
        System.out.println("\n column names\n");
        for (int i = 0; i < columnNames.length; i++)
              System.out.println("-"+columnNames[i].toString()+"-");
        System.out.println("\n data attributes\n");
        System.out.println("-"+dataAttributes.toString()+"-");
        System.out.println("\n col layout\n");
        System.out.println("-"+colLayout.toString()+"-");
        System.out.println("\n row layout\n");
        System.out.println("-"+rowLayout.toString()+"-");
          */
        
          RowsetDataSource rs = new RowsetDataSource();
          rs.setFilterSpecs(filterSpecs);
          rs.setData(rowsetData);
          rs.setColumns(columnNames);
          rs.setDataAttributes(dataAttributes);
          AggSpec[][] specs = new AggSpec[aggSpecs.size()][];
          for (int i = 0; i < aggSpecs.size(); i++)
              specs[i] = aggSpecs.get(i);
          rs.setAggSpecs(specs);
          rs.setColumnLayout(colLayout);
          rs.setRowLayout(rowLayout);
          setDataSource(rs);
    } // end loadData()

    public int addItemsToLayout(ArrayList<String> layout, 
                                String[] m_columnNames, 
                                int[] m_columnDataTypes, int startIndex, 
                                int numItems) {
        int count = 0;
        int i;
        for (i = startIndex; i < m_columnNames.length && count < numItems; 
             i++) {
            if (m_columnDataTypes[i] != PivotModel.DOUBLE_TYPE) {
                count++;
                layout.add(m_columnNames[i]);
            }
        }
        return i;
    }
    
    // refresh data after filtering, ... 
    // This command reloads the cube with the filtered rowset, but doesn't reload the data / base rowset.  Reloading the 
    // data from the base rowset will throw out updated values. 
    public void refreshData() throws DataException{
        ((RowsetDataSource)getDataSource()).refreshData(true);
    }
    
    public RowsetDataSource getRowsetDataSource() {
        return (RowsetDataSource)getDataSource();      
    }
    
    protected ArrayList<String[]> getRowsetDataFromFile(UploadedFile file)
    {
        ArrayList<String[]> lines = new ArrayList<String[]>();
        try {
            InputStream in = file.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bReader = new BufferedReader(reader);
            String line = null;
            int count = 0;
            while ((line = bReader.readLine()) != null) {
                count++;
                if (count > 
                    PivotModel.MAX_ROWS) // don't allow too many rows to be fetched. 
                    break;
                lines.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }  

    protected ArrayList<String[]> getRowsetDataFromString(String data)
    {
          ArrayList<String[]> lines = new ArrayList<String[]>();
          String[] _lines = data.split("\n");
          for (int i = 0; i < _lines.length; i++)
              lines.add(_lines[i].split(","));
          return lines;
    } 

    protected boolean isTypeLine(String[] line) {
          boolean isTypeLine = true;
          for (int j = 0; j < line.length; j++) {
              String value = line[j];
              if (value.trim().equalsIgnoreCase("double") || 
                  value.trim().equalsIgnoreCase("string") || 
                  value.trim().equalsIgnoreCase("date"))
                  continue;
              else {
                  isTypeLine = false;
                  break;
              }
          }
          return isTypeLine;
    } // end isTypeLine
    
    protected boolean isLayoutLine(String[] line) {
        boolean isLayoutLine = true;
        for (int j = 0; j < line.length; j++) {
            String value = line[j];
            if (value.trim().equalsIgnoreCase("row") || 
                value.trim().equalsIgnoreCase("column") || 
                value.trim().equalsIgnoreCase("page") || 
                value.trim().equalsIgnoreCase("data"))
                continue;
            else {
                isLayoutLine = false;
                break;
            }
        }
        return isLayoutLine;
    }

    protected boolean isTotalLine(String[] line) {
        boolean isTotalLine = true;
        for (int j = 0; j < line.length; j++) {
            String value = line[j];
            if (value.trim().equalsIgnoreCase("SUM") || 
                value.trim().equalsIgnoreCase("AVG") || 
                value.trim().equalsIgnoreCase("MIN") || 
                value.trim().equalsIgnoreCase("COUNT") || 
                value.trim().equalsIgnoreCase("NONE") || 
                value.trim().equalsIgnoreCase("STDDEV") || 
                value.trim().equalsIgnoreCase("VARIANCE") || 
                value.trim().equalsIgnoreCase("MAX"))
                continue;
            else {
                isTotalLine = false;
                break;
            }
        }
        return isTotalLine;
    }
    
    public boolean isDouble(String value) {
        try {
            Double dVal = Double.valueOf(value);
        } catch (Throwable t) {
            return false;
        }
        return true;
    }

    public boolean isDate(String value) {
          try {
              DateFormat df = new SimpleDateFormat();
              Date dVal = df.parse(value);
          } catch (Throwable t) {
              return false;
          }
          return true;   
    }
    
      public boolean isBoolean(String value) {
          try {
              Boolean bVal = Boolean.valueOf(value);
          } catch (Throwable t) {
              return false;
          }
          return true;
      }
      
    
    protected AggType getAggType(String type) {
        if (type.trim().equalsIgnoreCase("SUM"))
            return AggType.SUM;
        if (type.trim().equalsIgnoreCase("AVG"))
            return AggType.AVERAGE;
        if (type.trim().equalsIgnoreCase("MIN"))
            return AggType.MIN;
        if (type.trim().equalsIgnoreCase("MAX"))
            return AggType.MAX;
        if (type.trim().equalsIgnoreCase("COUNT"))
            return AggType.COUNT;
        if (type.trim().equalsIgnoreCase("NONE"))
            return AggType.NONE;
        if (type.trim().equalsIgnoreCase("STDDEV"))
            return AggType.STDDEV;
        if (type.trim().equalsIgnoreCase("VARIANCE"))
            return AggType.VARIANCE;
        return AggType.NONE;
    }

    
    private void updateFilterValues(FilterSpec filter, Number value) {
        if (filter != null) {
            long longValue = value.longValue();
            if (filter.getMax().longValue() < longValue) {
                filter.setMax(value.longValue());
                filter.setRangeMax(value.longValue()); // init the value to be the maximum value. 
            }
            if (filter.getMin().longValue() > longValue) {
                filter.setMin(value.longValue());
                filter.setRangeMin(value.longValue()); // init the value to be the minimum value. 
            }
        }
    }
      
    public Date toDate(String value){
          try {
              DateFormat df = new SimpleDateFormat(("MM/dd/yyyy"));
              Date dVal = df.parse(value.trim());
              return dVal;
          } catch (Throwable t) {
              
          }
          return null;    
    }
      public Boolean toBoolean(String value) {
            try {
            Boolean bVal = Boolean.valueOf(value);
                return bVal;
            } catch (Throwable t) {
            }
            return false;
        
      }
      
    public Double toDouble(String value) {
        try {
            Double dVal = Double.valueOf(value);
            return dVal;
        } catch (Throwable t) {
        }
        return -1.0;
    }
    
    //////////////////////////////////////////////////////////////////////////
    // BaseRowsetModel class attributes
    
    //////////////////////////////////////////////////////////////////////////
    // rowset datasource properties
    
    protected ArrayList<String[]> getRowsetData() {
        return getRowsetDataFromString(PivotModel.SAMPLE_ROWSET);
    }

    private static final String SAMPLE_ROWSET = 
          "Time, Product, Channel, Geography, Sales, Units\n" +
          "row, row, column, column, data, data\n" +
          "string, string, string, string, double, double\n" +
          "2007, Tents, All Channels, World, 20000, 200\n" +
          "2007, Tents, All Channels, Boston, 500, 50\n" +
          "2007, Canoes, All Channels, World, 15000, 75\n" +
          "2007, Canoes, All Channels, Boston, 1500, 8\n" +
          "2006, Tents, All Channels, World, 10000, 100\n" +
          "2006, Tents, All Channels, Boston, 250, 25\n" +
          "2006, Canoes, All Channels, World, 7500, 40\n" +
          "2006, Canoes, All Channels, Boston, 750, 4\n" +
          "2005, Tents, All Channels, World, 5000, 50\n" +
          "2005, Tents, All Channels, Boston, 125, 15\n" +
          "2005, Canoes, All Channels, World, 3750, 20\n" +
          "2005, Canoes, All Channels, Boston, 375, 2";
    
    private static final int MAX_ROWS = 6000;
    
    protected static final int DOUBLE_TYPE = 0;
    protected static final int DATE_TYPE = 1;
    protected static final int STRING_TYPE = 2;
    protected static final int BOOLEAN_TYPE = 3;

}
