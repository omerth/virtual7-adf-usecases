package com.virtual7.programaticPivotTableBinding.view.managed.normal;


import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import oracle.dss.util.QDR;
import oracle.dss.util.transform.DataCellInterface;
import oracle.dss.util.transform.LayerInterface;
import oracle.dss.util.transform.MemberInterface;
import oracle.dss.util.transform.RowIterator;
import oracle.dss.util.transform.RowProjection;

public class BaseProjectionImpl implements RowProjection
{
    public static final String MEASDIM = "MeasDim";
    public static final String DATA_ONLY = "!dataOnly!";
    
    
    protected String[][] m_layout = null;
    protected String[] m_dataItems = null;
    protected Object[][] m_data;
    protected String[] m_columns = null;
    protected Hashtable<QDR, DataCellInterface> m_dataCache = new Hashtable<QDR, DataCellInterface>();
    protected boolean m_nodatainColumns = false;

    public BaseProjectionImpl(String[][] layout, String[] dataItems, Object[][] data, String[] columns, Hashtable<QDR, DataCellInterface> dataCache)
    {
        super();
        m_layout = layout;
        m_dataItems = dataItems;
        if (columns != null)
        {
            m_data = data;
            m_columns = columns;
        }
        else
        {
            if (data != null)
            {
                // Vet the columns against layout & data items
                Vector cols = new Vector();
                Vector trueLoc = new Vector();
                for (int i = 0; i < data[0].length; i++)
                {
                    if (isIn((String)data[0][i], m_dataItems) || isIn((String)data[0][i], m_layout))
                    {
                        cols.addElement(data[0][i]);
                        trueLoc.addElement(new Integer(i));
                    }
                }
                m_columns = new String[cols.size()];
                for (int i = 0; i < m_columns.length; i++)
                    m_columns[i] = (String)cols.elementAt(i);
                m_data = new Object[data.length-1][];
                for (int i = 0; i < m_data.length; i++)
                {
                    m_data[i] = new Object[m_columns.length];
                    for (int c = 0; c < m_columns.length; c++)
                        m_data[i][c] = data[i+1][((Integer)trueLoc.elementAt(c)).intValue()];
                }
            }
        }
        if (dataCache != null)
            m_dataCache = dataCache;
    }
    
    private boolean isIn(String val, String[] list)
    {
        if (val == null || list == null)
            return false;
        
        for (int i = 0; i < list.length; i++)
            if (list[i] != null && list[i].equals(val))
                return true;
        return false;
    }
    
    private boolean isIn(String val, String[][] array)
    {
        if (val == null || array == null)
            return false;
        
        for (int i = 0; i < array.length; i++)
            if (isIn(val, array[i]))
                return true;
        return false;
    }
    
    public BaseProjectionImpl(String[][] layout, String[] dataItems, Object[][] data)
    {
        this(layout, dataItems, data, null);
    }
    
    public BaseProjectionImpl(String[][] layout, String[] dataItems, Object[][] data, String[] columns)
    {
        this(layout, dataItems, data, columns, null);
    }
    
    // Retrieves the layout of this projection as a 2-D array containing the layer IDs
    public String[][] getLayout()
    {
        return m_layout;
    }
    
    // Retrieves the ID representing the data layer (i.e. where the dataItems appear) in the layout
    public LayerInterface getDataLayer()
    {
        return new LayerInterfaceImpl(MEASDIM);
    }
    
    // Retrieves the RowIterator containing the data for the projection
    public RowIterator getRowIterator()
    {
        return new RowIterImpl(m_columns, m_data, m_dataCache, getDataItems(), m_layout);
    }
    
    // Retrieves a list of MemberInterfaces representing DataItems 
    // (corresponding to CellInterfaces in the RowIterator)
    public MemberInterface[] getDataItems()
    {            
        if (m_dataItems == null)
            return null;
        
        MemberInterface[] vals = new MemberInterface[m_dataItems.length];
        for (int i = 0; i < vals.length; i++)
            vals[i] = new MemberInterfaceImpl(m_dataItems[i]);
        return vals;
    }

    // Retrieves a LayerInterface implementation for the given layer ID
    public LayerInterface getLayer(String layer)
    {
        return new LayerInterfaceImpl(layer);
    }
        
    public DataCellInterface getData(Map<String, Object> qdr)
    {
        return m_dataCache.get(qdr);
    }        
    
    public String[] getIgnoredColumns()
    {
        return null;
    }
}
