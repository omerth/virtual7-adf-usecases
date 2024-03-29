package com.virtual7.employeeAptitudes.model.entities;

import com.virtual7.util.model.BaseEntityImpl;

import oracle.jbo.Key;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jun 26 12:00:46 EEST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EmployeesToAptitudeEOImpl extends BaseEntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Id {
            public Object get(EmployeesToAptitudeEOImpl obj) {
                return obj.getId();
            }

            public void put(EmployeesToAptitudeEOImpl obj, Object value) {
                obj.setId((DBSequence)value);
            }
        }
        ,
        EmployeeId {
            public Object get(EmployeesToAptitudeEOImpl obj) {
                return obj.getEmployeeId();
            }

            public void put(EmployeesToAptitudeEOImpl obj, Object value) {
                obj.setEmployeeId((Number)value);
            }
        }
        ,
        AptitudeId {
            public Object get(EmployeesToAptitudeEOImpl obj) {
                return obj.getAptitudeId();
            }

            public void put(EmployeesToAptitudeEOImpl obj, Object value) {
                obj.setAptitudeId((Number)value);
            }
        }
        ,
        AptitudeEO {
            public Object get(EmployeesToAptitudeEOImpl obj) {
                return obj.getAptitudeEO();
            }

            public void put(EmployeesToAptitudeEOImpl obj, Object value) {
                obj.setAptitudeEO((AptitudeEOImpl)value);
            }
        }
        ,
        Employees {
            public Object get(EmployeesToAptitudeEOImpl obj) {
                return obj.getEmployees();
            }

            public void put(EmployeesToAptitudeEOImpl obj, Object value) {
                obj.setEmployees((EmployeesImpl)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(EmployeesToAptitudeEOImpl object);

        public abstract void put(EmployeesToAptitudeEOImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int ID = AttributesEnum.Id.index();
    public static final int EMPLOYEEID = AttributesEnum.EmployeeId.index();
    public static final int APTITUDEID = AttributesEnum.AptitudeId.index();
    public static final int APTITUDEEO = AttributesEnum.AptitudeEO.index();
    public static final int EMPLOYEES = AttributesEnum.Employees.index();

    /**
     * This is the default constructor (do not remove).
     */
    public EmployeesToAptitudeEOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("com.virtual7.employeeAptitudes.model.entities.EmployeesToAptitudeEO");
        }
        return mDefinitionObject;
    }

    /**
     * Gets the attribute value for Id, using the alias name Id.
     * @return the Id
     */
    public DBSequence getId() {
        return (DBSequence)getAttributeInternal(ID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Id.
     * @param value value to set the Id
     */
    public void setId(DBSequence value) {
        setAttributeInternal(ID, value);
    }

    /**
     * Gets the attribute value for EmployeeId, using the alias name EmployeeId.
     * @return the EmployeeId
     */
    public Number getEmployeeId() {
        return (Number)getAttributeInternal(EMPLOYEEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for EmployeeId.
     * @param value value to set the EmployeeId
     */
    public void setEmployeeId(Number value) {
        setAttributeInternal(EMPLOYEEID, value);
    }

    /**
     * Gets the attribute value for AptitudeId, using the alias name AptitudeId.
     * @return the AptitudeId
     */
    public Number getAptitudeId() {
        return (Number)getAttributeInternal(APTITUDEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for AptitudeId.
     * @param value value to set the AptitudeId
     */
    public void setAptitudeId(Number value) {
        setAttributeInternal(APTITUDEID, value);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }

    /**
     * @return the associated entity AptitudeEOImpl.
     */
    public AptitudeEOImpl getAptitudeEO() {
        return (AptitudeEOImpl)getAttributeInternal(APTITUDEEO);
    }

    /**
     * Sets <code>value</code> as the associated entity AptitudeEOImpl.
     */
    public void setAptitudeEO(AptitudeEOImpl value) {
        setAttributeInternal(APTITUDEEO, value);
    }

    /**
     * @return the associated entity EmployeesImpl.
     */
    public EmployeesImpl getEmployees() {
        return (EmployeesImpl)getAttributeInternal(EMPLOYEES);
    }

    /**
     * Sets <code>value</code> as the associated entity EmployeesImpl.
     */
    public void setEmployees(EmployeesImpl value) {
        setAttributeInternal(EMPLOYEES, value);
    }

    /**
     * @param id key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(DBSequence id) {
        return new Key(new Object[]{id});
    }

}
