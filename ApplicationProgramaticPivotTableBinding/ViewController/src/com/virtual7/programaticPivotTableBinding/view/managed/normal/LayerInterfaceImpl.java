package com.virtual7.programaticPivotTableBinding.view.managed.normal;


import oracle.dss.util.DataMap;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.transform.LayerInterface;
import oracle.dss.util.transform.LayerMetadata;
import oracle.dss.util.transform.TransformException;

public class LayerInterfaceImpl implements LayerInterface
{
    protected String m_value = null;
    
    public LayerInterfaceImpl(String value)
    {
        super();
        m_value = value;
    }
    
    // Compare two layers based on the getValue()
    public boolean equals(LayerInterface layer)
    {
        try
        {
            return m_value.equals(layer.getValue());
        }
        catch (TransformException e)
        {
            return false;
        }
    }
    
    // Generate a hash code for this layer based on the getValue()
    public int hashCode()
    {
        return m_value.hashCode();
    }
    
    // Represent this layer as its getValue() String
    public String toString()
    {
        return m_value;
    }
    
    // Return the basic value used as the unique key for this value (i.e., LayerMetadataMap.LAYER_METADATA_NAME)
    public String getValue() throws TransformException
    {
        return m_value;
    }
    
    // Return the appropriate layer metadata for the given LayerMetadataMap type
    public Object getMetadata(String type) throws TransformException
    {
        if (type.equals(LayerMetadata.MEASURE))
            return Boolean.valueOf(m_value.equals(BaseProjectionImpl.MEASDIM));
        else if(type.equals(LayerMetadata.LABEL) && m_value.equals(BaseProjectionImpl.MEASDIM))
            return "Measure";            
        return m_value;
    }
    
    public LayerMetadataMap getSupportedLayerMetadataMap()
    {
        return null;
    }
    
    public MetadataMap getSupportedMetadataMap()
    {
        return null;
    }

    public DataMap getSupportedDataMap()
    {
        return null;
    }        
}
