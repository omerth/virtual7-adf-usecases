package com.virtual7.programaticPivotTableBinding.view.managed.normal;

import oracle.dss.util.DataDirector;
import oracle.dss.util.transform.MemberInterface;
import oracle.dss.util.transform.MemberMetadata;
import oracle.dss.util.transform.TransformException;
import oracle.dss.util.transform.total.AggMethod;
import oracle.dss.util.transform.total.AggType;

public class MemberInterfaceImpl implements MemberInterface
    {
        protected String m_value = null;
        
        public MemberInterfaceImpl(String value)
        {
            super();
            m_value = value;
        }
        
        // Compare two members based on the getValue()
        public boolean equals(MemberInterface member)
        {
            try
            {
                return m_value.equals(member.getValue());
            }
            catch (TransformException e)
            {
                return false;
            }
        }

        
        // Generate a hash code for this member based on the getValue()
        public int hashCode()
        {
            return m_value == null ? 0 : m_value.hashCode();
        }
        
        // Represent this member as its getValue() String
        public String toString()
        {
            return m_value.toString();
        }
        
        // Return the basic value used as the unique key for this member (i.e., MetadataMap.METADATA_VALUE)
        public String getValue() throws TransformException
        {
            return m_value;
        }
        
        // Return the appropriate member metadata for the given MetadataMap type
        public Object getMetadata(String type) throws TransformException
        {
            if (type.equals(MemberMetadata.COLLAPSED))
                return Boolean.valueOf(false);
            if (type.equals(MemberMetadata.AGGREGATE_POSITION))
                return AggregatePosition.NONE;
            if(type.equals(MemberMetadata.DRILLSTATE)) 
                return DataDirector.DRILLSTATE_NOT_DRILLABLE;
            if (type.equals(MemberMetadata.AGGTYPE))
                return new AggMethod[] {new AggMethod(AggType.NONE)};
            return m_value;
        }

    }
