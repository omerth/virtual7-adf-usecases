package com.virtual7.programaticPivotTableBinding.view.managed;

import oracle.dss.util.transform.DataCellInterface;
import oracle.dss.util.transform.TransformException;

public class CellInterfaceImpl implements DataCellInterface
    {
        protected Object m_value = null;
        
        public CellInterfaceImpl(Object value)
        {
            m_value = value;
        }
        
        // Return the data value for the given DataMap type
        public Object getData(String type)
        {                
            return m_value;
        }

        public boolean setData(Object value, String type) throws TransformException
        {
            return false;
        }        
    }
