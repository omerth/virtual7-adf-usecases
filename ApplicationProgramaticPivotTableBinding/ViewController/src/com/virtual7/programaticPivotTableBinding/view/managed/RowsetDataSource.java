package com.virtual7.programaticPivotTableBinding.view.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.el.ELContext;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adfinternal.view.faces.bi.renderkit.pivotTable.RichPivotTableRenderer;

import oracle.dss.util.CubeDataAccess;
import oracle.dss.util.CubeDataDirector;
import oracle.dss.util.DataAccess;
import oracle.dss.util.DataAccessAdapter;
import oracle.dss.util.DataAvailableEvent;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DataDirectorException;
import oracle.dss.util.DataDirectorListener;
import oracle.dss.util.DataException;
import oracle.dss.util.DataMap;
import oracle.dss.util.DataSource;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.QDR;
import oracle.dss.util.QDRMember;
import oracle.dss.util.QDRSliceSortInfo;
import oracle.dss.util.RelationalDataAccess;
import oracle.dss.util.RelationalDataDirector;
import oracle.dss.util.SortInfo;
import oracle.dss.util.transform.ResultTable;
import oracle.dss.util.transform.RowBasedCubicDataAccess;
import oracle.dss.util.transform.RowProjection;
import oracle.dss.util.transform.total.AggSpec;

import org.apache.myfaces.trinidad.model.CollectionModel;

public class RowsetDataSource extends DataAccessAdapter implements CubeDataDirector, CubeDataAccess, RelationalDataDirector, RelationalDataAccess, DataSource
{                           
    public RowsetDataSource()
    {                 
    }                 

    public void setData(Object data) {
        m_data = data;
    }

    public Object getData() {
        return m_data;
    }

    public HashMap<String,FilterSpec> getFilterSpecs() {
        return m_filterSpecs;
    }
    
    public void setFilterSpecs(HashMap<String,FilterSpec> filterSpecs) {
        m_filterSpecs = filterSpecs;
    }

    public Object[][] fetchData() {
        HashMap<String,FilterSpec> filters = m_filterSpecs;
        ArrayList<Object[]> rows = new ArrayList<Object[]>();
        if(m_data instanceof CollectionModel)
        {
            CollectionModel model = (CollectionModel)m_data;

            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            context.getExternalContext().getRequestMap().put("dvt_sample_collectionModel", model);
            
            String[] columns = getColumns();
            int rowCount = model.getRowCount();
            int colCount = columns.length;
            for(int r=0;r<rowCount;r++)
            {
                Object[] row = new Object[colCount];
                for(int c=0;c<colCount;c++) {
                    String elExpr = "#{request.dvt_sample_collectionModel." + m_columns[c] + "}";
                    row[c] = elContext.getELResolver().getValue(elContext, null, elExpr);                                        
                }
                if(!filterRow(filters, row))
                  rows.add(row);                
            }
        } else {
            Object[][] data = (Object[][])m_data;
            for(int r=0;r<data.length;r++) 
            {
                Object[] row = data[r];
                if(!filterRow(filters, row))
                  rows.add(row);
            }   
        }
        if(rows.size()==0)
        {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"No data selected.  Please modify the rowset datasource filters in order to display more data.",""));
        }

        return rows.toArray(new Object[rows.size()][]);
    }

    protected boolean filterRow(HashMap<String,FilterSpec> filterSpecs, Object[] row) {
        boolean bFilterRow = false;
        Iterator<FilterSpec> iterator = filterSpecs.values().iterator();
        while(iterator.hasNext())
        {            
            FilterSpec filter = iterator.next();
            if(!filter.includeRow(row))
            {
                bFilterRow = true;
                break;
            }
        }
        return bFilterRow;
    }
    
    public FilterSpec getFilterSpec(String dataAttribute) {
        return getFilterSpecs().get(dataAttribute);
    }

    public void setDataAttributes(ArrayList<String> dataAttributes) {
        m_dataAttributes = dataAttributes;
    }

    public ArrayList<String> getDataAttributes() {
        return m_dataAttributes;
    }

    public void setColumns(String[] columns) {
        m_columns = columns;
    }

    public String[] getColumns() {
        return m_columns;
    }

    public ArrayList<String> getColumnLayout() {
        return m_colLayout;
    }

    public void setColumnLayout(ArrayList<String> colLayout) {
        m_colLayout = colLayout;
    }

    public ArrayList<String> getRowLayout() {
        return m_rowLayout;
    }

    public void setColumnSectionLayout(ArrayList<String> colSectionLayout) {
        m_colSectionLayout = colSectionLayout;
    }
 
    public ArrayList<String> getColumnSectionLayout() {
        return m_colSectionLayout;
    }

    public void setRowSectionLayout(ArrayList<String> rowSectionLayout) {
        m_rowSectionLayout = rowSectionLayout;
    }
    
    public ArrayList<String> getRowSectionLayout() {
        return m_rowSectionLayout;
    }
    public void setRowLayout(ArrayList<String> rowLayout) {
        m_rowLayout = rowLayout;
    }

    public AggSpec[][] getAggSpecs() {
        return m_aggSpecs;
    }

    public void setAggSpecs(AggSpec[][] aggSpecs) {
        m_aggSpecs = aggSpecs;
    }

    public boolean isTotalsEnabled() {
        return m_totalsEnabled;
    }

    public void setTotalsEnabled(boolean totalsEnabled) {
        m_totalsEnabled=totalsEnabled;
    }
    
    public void refreshData(boolean preserveSort) throws DataException{
        m_da = null;
        m_updatedValues.clear();
        getDataAccess(); // bootstrap the new data access before applying sorts. 
        if(preserveSort)
            setSorts(getSorts());
    }
    
    public DataAccess getDataAccess() {
        // Test data column projections (no connections needed)
        
        try
        {
            if(m_da==null)
            {
                Object [][] data = fetchData();
                String [] colSectionLayout = null;
                if(m_colSectionLayout!=null)
                    colSectionLayout = m_colSectionLayout.toArray(new String[0]);

                String [] rowSectionLayout = null;
                if(m_rowSectionLayout!=null)
                    rowSectionLayout = m_rowSectionLayout.toArray(new String[0]);
                
                String layout[][] = null;
                if((rowSectionLayout!=null)&&(colSectionLayout!=null))
                    layout = new String[][] {m_colLayout.toArray(new String[0]), m_rowLayout.toArray(new String[0]), rowSectionLayout, colSectionLayout};
                else
                    layout = new String[][] {m_colLayout.toArray(new String[0]), m_rowLayout.toArray(new String[0])};

                HashMap<String,Double> dataAttributeMinimums = new HashMap<String,Double>();
                HashMap<String,Double> dataAttributeMaximums = new HashMap<String,Double>();

                ArrayList<String> dataAttributes = new ArrayList<String>();                                
                
                for(int i=0;i<m_dataAttributes.size();i++) {
                    String dataAttribute = m_dataAttributes.get(i);
                    FilterSpec filter = getFilterSpecs().get(dataAttribute);
                    if(filter!=null)
                    {  
                        // only show data attribute if filter is enabled. 
                        if(filter.isDataEnabled())
                        {
                            dataAttributeMinimums.put(dataAttribute,filter.getMin().doubleValue());        
                            dataAttributeMaximums.put(dataAttribute,filter.getMax().doubleValue());            
                            dataAttributes.add(dataAttribute);
                        } 
                    } else
                        dataAttributes.add(dataAttribute);
                        
                }
                
                RowProjection proj = new BaseProjectionImpl(layout, dataAttributes.toArray(new String[0]), data, m_columns);
                AggSpec[][] aggSpecs = null;
                if(isTotalsEnabled())
                    aggSpecs = getAggSpecs();
                RowBasedCubicDataAccess cubicDA = new RowBasedCubicDataAccess(new ResultTable(proj,aggSpecs));
                m_cubicDA = cubicDA;
                // wrap the data access with a decorator, so that we can add values to 
                // DataAccess
                m_da = new RowsetDataAccessDecorator(cubicDA, getFilterSpecs(), m_updatedValues);
                m_isDirty = false;
            }
            return m_da;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean updateMatch(QDR qdr, Object[] row, String[] columns, String dataAttribute, Object newValue) {
        Iterator<String> i = qdr.keySet().iterator();
        String measureDim = qdr.getMeasureDim();
        while(i.hasNext()) {
            String key = i.next();
            // dont compare measure dim
            if(!key.equals(measureDim))
            {
                String member = qdr.getDimMember(key).toString();
                for(int j=0;j<columns.length;j++) {
                    if(key.equals(columns[j])) {
                        if(!row[j].equals(member))
                            return false;
                    }
                }
            }
        }
        for(int j=0;j<columns.length;j++) {
            if(dataAttribute.equals(columns[j])) {
                if(newValue instanceof Number)
                  row[j] = new Double(((Number)newValue).doubleValue());
                else 
                  row[j] = newValue;
            }
        }        
        return true;
    }    

    //returns whether the max or min changed asa result of the value change
    public boolean valueChanged(QDR qdr, Object newObjectValue, Object oldObjectValue) {
       
       
        String dataAttribute = getDataAttribute(qdr);
                                
        boolean bLimitChanged = false;
        m_isDirty = true; // mark the DataAccess for update on the next sort, since sort doesn't typically reload the rowset
        if(newObjectValue!=null)
        {
            // update the underlying rowset!
            if(m_data instanceof Object[][])
            {
                Object[][] data = (Object[][])m_data;
                for(int i=0;i<data.length;i++) {
                    Object[] row = data[i];
                    if(updateMatch(qdr, row,m_columns,dataAttribute, newObjectValue))
                        break;
                }
            }
            
            if(newObjectValue instanceof Number)
            {
                Number newValue = (Number)newObjectValue;
                Number oldValue = (Number)oldObjectValue;
    
                // update the limits
                Iterator<FilterSpec> iterator = getFilterSpecs().values().iterator();
                while(iterator.hasNext())
                {
                    FilterSpec filter = iterator.next();
                    if(filter.getLabel().equals(dataAttribute)) {
                        long _newValue = newValue.longValue();
                        if(_newValue > filter.getMax().longValue())
                        {
                            if(filter.getMax().equals(filter.getRangeMax()))
                                filter.setRangeMax(new Long(_newValue));
                            filter.setMax(new Long(_newValue));
                            
                            bLimitChanged = true;
                        } else if(_newValue < filter.getMin().longValue())
                        {
                            if(filter.getRangeMin().equals(filter.getRangeMin()))
                                filter.setRangeMin(new Long(_newValue));
                            filter.setMin(new Long(_newValue));
                            bLimitChanged = true;
                        } else {
                            
                            // if the old value was the max/min, then 
                            // determine the the new max/min
                            if(oldValue!=null)
                            {
                                long _oldValue = oldValue.longValue();
                                if(_oldValue==filter.getMax().longValue()) {
                                    // find new maximum, skipping over current cell value that still hasn't changed. 
                                    long newMax = computeDataAttributeMaxValue(qdr, dataAttribute,_newValue);
                                    filter.setMax(new Long(newMax));
                                    if(filter.getRangeMax().equals(oldValue))
                                        filter.setRangeMax(filter.getMax());
                                    bLimitChanged = true;                            
                                } else if(_oldValue==filter.getMin().longValue()){
                                    // find new mimimum, skipping over current cell value that still hasn't changed. 
                                    long newMin = computeDataAttributeMinValue(qdr, dataAttribute,_newValue);
                                    filter.setMin(new Long(newMin));
                                    if(filter.getRangeMin().equals(oldValue))
                                        filter.setRangeMin(filter.getMax());
                                    bLimitChanged = true;                                                        
                                }
                            }
                        }                
                    }
                }
            }
        }
        return bLimitChanged;
    }

    // find the new max for the data attribute, but skip the updatingQDR, since it still has its old value
    // pass in the new value of the updating cell, which may be the new max
    protected long computeDataAttributeMaxValue(QDR updatingQDR, String dataAttribute, long newValue) {
        return computeDataAttributeLimit(updatingQDR, dataAttribute, newValue, true);
    }

    // find the new min for the data attribute, but skip the updatingQDR, since it still has its old value
    // pass in the new value of the updating cell, which may be the new min
    protected long computeDataAttributeMinValue(QDR updatingQDR, String dataAttribute, long newValue) {
        return computeDataAttributeLimit(updatingQDR, dataAttribute, newValue, false);
    }

    protected long computeDataAttributeLimit(QDR updatingQDR, String dataAttribute, long newValue, boolean isMax) {
        // iterate over the entire data set looking for data attribute values that are larger
        DataAccess da = getDataAccess();
        try {
            int colExtent = da.getEdgeExtent(DataDirector.COLUMN_EDGE);
            int rowExtent = da.getEdgeExtent(DataDirector.ROW_EDGE);
            for(int c=0;c<colExtent;c++) {
                for(int r=0;r<rowExtent;r++) {
                    QDR qdr = da.getValueQDR(r,c,DataAccess.QDR_WITH_PAGE);
                    String _dataAttribute = getDataAttribute(qdr);
                    // only try to find max values for the same data attribute. 
                    if(_dataAttribute!=null && _dataAttribute.equals(dataAttribute)) {
                        // skip the qdr/cell value being updated, since it still has its old value, which 
                        // was the old max/min.  
                        if(!qdr.equals(updatingQDR))
                        {
                            Object value = da.getValue(r,c,DataMap.DATA_VALUE);
                            if(value instanceof Number) {
                                double _value = ((Number)value).doubleValue();
                                if(isMax)
                                {
                                    if(_value>newValue)
                                        newValue = Math.round(_value);
                                } else
                                {
                                    if(_value<newValue)
                                        newValue = Math.round(_value);
                                }
                                    
                            }
                        }
                    }
                }
            }
        } catch ( DataException e) {
            e.printStackTrace();
        }
        return newValue;
    }

    protected String getDataAttribute(QDR qdr) {
        QDRMember member = qdr.getDimMember(qdr.getMeasureDim());
        if(member!=null)
            return member.toString();
        else
            return null;
    }
    
    public Object getProperty(String property) throws DataDirectorException {
        if(property.equals(DataDirector.PROP_SORTS_SUPPORTED))
            return Boolean.TRUE;
        return super.getProperty(property);
    }

    public SortInfo[] getSorts() 
    {
        return m_sortInfo;
    }
    
    public boolean setSorts(SortInfo[] sortInfo) throws LayerOutOfRangeException, DataDirectorException {
        m_sortInfo = sortInfo;
        QDRSliceSortInfo sliceInfo = null;
        if (sortInfo != null)
        {
            for (int i = 0; i < sortInfo.length; i++)
            {
                if (sortInfo[i] instanceof QDRSliceSortInfo)
                {
                    sliceInfo = (QDRSliceSortInfo)sortInfo[i];
                    // This is the only type we do right now: turn this into a call on the data access
                    int edge = sliceInfo.getEdge();
                    if (edge > -1)
                    {
                        int[] directions = sliceInfo.getDirection();
                        if (directions != null && directions.length > 0)
                        {
                            int direction = directions[0];
                            int slice = sliceInfo.getSlice();
                            if (slice != -1)
                            {    
                                try {
                                    if(m_isDirty) {
                                        refreshData(true);
                                        return true;
                                    } else
                                        return m_cubicDA.sort(edge, slice, direction, sliceInfo.isNullsFirst(), sliceInfo.isGrouped());
                                } catch(DataException e) {
                                    throw new DataDirectorException("Sort Failed", e);
                                }
                            }
                        }
                    }
                }
            }
        }              
        return false;
    }

    public boolean pivot(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException
    {        
        m_da=null;
        m_cubicDA=null;
        m_sortInfo = null;
        ArrayList<String> toList = getEdgeLayout(toEdge);
        ArrayList<String> fromList = getEdgeLayout(fromEdge);
        switch(flags) {
            case DataDirector.PIVOT_MOVE_AFTER:
            {
                String tLayer = toList.get(toLayer);
                String fLayer = fromList.remove(fromLayer);
                toList.add(toList.indexOf(tLayer) + 1, fLayer);                
                break;
            }
            case DataDirector.PIVOT_MOVE_TO:
            {
                String fLayer = fromList.remove(fromLayer);
                toList.add(fLayer);                
                break;
            }
            case DataDirector.PIVOT_MOVE_BEFORE:
            {
                String tLayer = toList.get(toLayer);
                String fLayer = fromList.remove(fromLayer);
                toList.add(toList.indexOf(tLayer), fLayer);                
                break;
            }
            case DataDirector.PIVOT_SWAP:
            {
                String fLayer = fromList.get(fromLayer);
                String tLayer = toList.get(toLayer);
                fromList.set(fromLayer,tLayer);
                toList.set(toLayer,fLayer);
                break;
            }
        }
        return true;        
    }

    public ArrayList<String> getEdgeLayout(int edge) {
        if(edge == DataDirector.ROW_EDGE)
            return m_rowLayout;
        else if(edge == DataDirector.COLUMN_EDGE)
            return m_colLayout;
        else if(edge == RichPivotTableRenderer.COLUMN_SECTION_EDGE)
            return m_colSectionLayout;
        else if(edge == RichPivotTableRenderer.ROW_SECTION_EDGE)
            return m_rowSectionLayout;
        else
            return null;
    }

    public boolean pivotOK(int fromEdge, int toEdge, int fromLayer, int toLayer, int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException, DataDirectorException
    {        
        return true;        
    }

    // METHODS

    // Other constructors will allow custom local data cubes to be set up,
    // and other methods will allow them to be filled with data more efficiently
    public DataDirector createDataDirector()
    {
        return this;
    }
    
    public CubeDataDirector createCubeDataDirector() {
        return this;
    }
    
    public RelationalDataDirector createRelationalDataDirector() {
        return this;
    }
    
    public void addDataDirectorListener(DataDirectorListener l) {
        listener = l;
        l.viewDataAvailable(new DataAvailableEvent(this, this.getDataAccess()));
    }

    public void removeDataDirectorListener(DataDirectorListener l) {
    }
    
    public Object clone() {
        return null;
    }
        
    public DataMap getDataMap() {
        return new DataMap("");
    }

    public MetadataMap getMetadataMap(int edge, int layer) throws EdgeOutOfRangeException, LayerOutOfRangeException {
        return new MetadataMap((String)null);
    }

    public DataMap getSupportedDataMap()
    {   
        String supported[] = {DataMap.DATA_UNFORMATTED};
        return new DataMap(supported);
    }
    
    public MetadataMap getSupportedMetadataMap()
    {
        String supported[] = {MetadataMap.METADATA_LONGLABEL,
                              MetadataMap.METADATA_DRILLSTATE};
        return new MetadataMap(supported);
    }
    
    public LayerMetadataMap getSupportedLayerMetadataMap()
    {
        String supported[] = {LayerMetadataMap.LAYER_METADATA_LONGLABEL};
        return new LayerMetadataMap(supported);
    }

    private HashMap m_updatedValues = new HashMap();
    
    protected DataDirectorListener listener;
    protected ArrayList<String> m_colLayout = null;
    protected ArrayList<String> m_rowLayout = null;    
    protected ArrayList<String> m_colSectionLayout = null;
    protected ArrayList<String> m_rowSectionLayout = null;    
    protected ArrayList<String> m_dataAttributes = null;
    protected String[] m_columns = null;
    protected AggSpec[][] m_aggSpecs = null;
    protected Object m_data = null;
    protected DataAccess m_da = null;
    protected RowBasedCubicDataAccess m_cubicDA = null;
    protected SortInfo[] m_sortInfo = null;
    HashMap<String,FilterSpec> m_filterSpecs=null;
    boolean m_totalsEnabled=false;
    boolean m_isDirty = false;
    
    
    
}
