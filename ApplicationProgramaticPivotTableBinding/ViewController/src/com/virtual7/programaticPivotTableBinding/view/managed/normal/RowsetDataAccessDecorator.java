package com.virtual7.programaticPivotTableBinding.view.managed.normal;


import java.util.ArrayList;
import java.util.HashMap;

import oracle.adfinternal.view.faces.bi.renderkit.pivotTable.DataAccessDecorator;

import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DataMap;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.QDR;
import oracle.dss.util.QDRMember;
import oracle.dss.util.RowOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;

public class RowsetDataAccessDecorator extends DataAccessDecorator {
    public RowsetDataAccessDecorator(DataAccess dataAccess, HashMap<String,FilterSpec> filterSpecs, HashMap updatedValues) {
        super(dataAccess);
        __filterSpecs = filterSpecs; 
        __updatedValues = updatedValues;
    }


    public Object getValue(int row, int column, String type) throws RowOutOfRangeException, ColumnOutOfRangeException
    {
        try {
            if(type.equals("dataItem")) // used by graph in pt demo!
                return getDataAttribute(row,column);
            if(type.equals("dataAttributeMinimum"))                
                return getDataAttributeMinimum(getDataAttribute(row,column));
            else if(type.equals("dataAttributeMaximum"))                
                return getDataAttributeMaximum(getDataAttribute(row,column));
            else if(type.equals(DataMap.DATA_VALUE))
            {
              QDR valueQDR = getValueQDR(row,column,DataAccess.QDR_WITHOUT_PAGE);              
              String key = valueQDR.toString();
              if(__updatedValues.containsKey(key))
                 return __updatedValues.get(key); 
            }        
            else if (type.equals("tabularData"))
            {
              ArrayList _list = new ArrayList(1);
              _list.add(new Object[]{super.getValue(row,column,MetadataMap.METADATA_VALUE), getDataAttribute(row,column), super.getValue(row,column,"formattedDataValue")});
              return _list;
            }
            return super.getValue(row, column,type);
        }catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public boolean setValue(Object data, int row, int col, String type) throws RowOutOfRangeException, ColumnOutOfRangeException {
        if(type!=null && type.equals(DataMap.DATA_VALUE))
        {
            QDR valueQDR = getValueQDR(row,col,DataAccess.QDR_WITHOUT_PAGE);              
            String key = valueQDR.toString();
            if(data instanceof Number)
                data = new Double(((Number)data).doubleValue());
            __updatedValues.put(key,data);
            return true;
        }
        return false;
    }
    
    public Object getMemberMetadata(int edge, int layer, int slice, String type) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException
    {
        if(type.equals("dataItem")) // for the moment, this is only used in the graph in pt demo to determine if the measure dim is on the edge ( via dataItem!=null )              
            return getDataAttribute(edge,layer,slice);
        if(type.equals("dataAttributeMinimum"))                
            return getDataAttributeMinimum(getDataAttribute(edge,layer,slice));
        else if(type.equals("dataAttributeMaximum"))                
            return getDataAttributeMaximum(getDataAttribute(edge,layer,slice));
        else
            return super.getMemberMetadata(edge,layer,slice,type);
    }

    public String getDataAttribute(int row, int column)  throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException 
    {
      String dataAttribute = getDataAttribute(DataDirector.ROW_EDGE,0,row);
      if(dataAttribute==null)
          dataAttribute = getDataAttribute(DataDirector.COLUMN_EDGE,0,column);
      return dataAttribute;
    }
    
    public String getDataAttribute(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException, SliceOutOfRangeException
    {
      QDR sliceQDR = getSliceQDR(edge,slice,DataAccess.QDR_WITH_PAGE);
      QDRMember dimMember = sliceQDR.getDimMember(sliceQDR.getMeasureDim()); 
      if(dimMember!=null)
          return dimMember.toString();          
      else
          return null;
    }
    
    public Number getDataAttributeMinimum(String dataAttribute) {
        FilterSpec filter = __filterSpecs.get(dataAttribute);
        return filter.getMin();
    }
    public Number getDataAttributeMaximum(String dataAttribute) {
        FilterSpec filter = __filterSpecs.get(dataAttribute);
        return filter.getMax();
    }
    
    HashMap<String,FilterSpec> __filterSpecs = null;
    HashMap __updatedValues = null;
}