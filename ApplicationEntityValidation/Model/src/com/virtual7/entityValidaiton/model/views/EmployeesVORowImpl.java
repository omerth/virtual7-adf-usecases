package com.virtual7.entityValidaiton.model.views;

import com.virtual7.entityValidaiton.model.entities.EmployeesEOImpl;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Mar 23 12:09:30 EET 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EmployeesVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Email {
            public Object get(EmployeesVORowImpl obj) {
                return obj.getEmail();
            }

            public void put(EmployeesVORowImpl obj, Object value) {
                obj.setEmail((String)value);
            }
        }
        ,
        EmployeeId {
            public Object get(EmployeesVORowImpl obj) {
                return obj.getEmployeeId();
            }

            public void put(EmployeesVORowImpl obj, Object value) {
                obj.setEmployeeId((Number)value);
            }
        }
        ,
        FirstName {
            public Object get(EmployeesVORowImpl obj) {
                return obj.getFirstName();
            }

            public void put(EmployeesVORowImpl obj, Object value) {
                obj.setFirstName((String)value);
            }
        }
        ,
        LastName {
            public Object get(EmployeesVORowImpl obj) {
                return obj.getLastName();
            }

            public void put(EmployeesVORowImpl obj, Object value) {
                obj.setLastName((String)value);
            }
        }
        ,
        emailsec {
            public Object get(EmployeesVORowImpl obj) {
                return obj.getemailsec();
            }

            public void put(EmployeesVORowImpl obj, Object value) {
                obj.setemailsec((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(EmployeesVORowImpl object);

        public abstract void put(EmployeesVORowImpl object, Object value);

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


    public static final int EMAIL = AttributesEnum.Email.index();
    public static final int EMPLOYEEID = AttributesEnum.EmployeeId.index();
    public static final int FIRSTNAME = AttributesEnum.FirstName.index();
    public static final int LASTNAME = AttributesEnum.LastName.index();
    public static final int EMAILSEC = AttributesEnum.emailsec.index();

    /**
     * This is the default constructor (do not remove).
     */
    public EmployeesVORowImpl() {
    }

    /**
     * Gets EmployeesEO entity object.
     * @return the EmployeesEO
     */
    public EmployeesEOImpl getEmployeesEO() {
        return (EmployeesEOImpl)getEntity(0);
    }


    /**
     * Gets the attribute value for EMAIL using the alias name Email.
     * @return the EMAIL
     */
    public String getEmail() {
        return (String)getAttributeInternal(EMAIL);
    }

    /**
     * Sets <code>value</code> as attribute value for EMAIL using the alias name Email.
     * @param value value to set the EMAIL
     */
    public void setEmail(String value) {
        setAttributeInternal(EMAIL, value);
    }

    /**
     * Gets the attribute value for EMPLOYEE_ID using the alias name EmployeeId.
     * @return the EMPLOYEE_ID
     */
    public Number getEmployeeId() {
        return (Number)getAttributeInternal(EMPLOYEEID);
    }

    /**
     * Sets <code>value</code> as attribute value for EMPLOYEE_ID using the alias name EmployeeId.
     * @param value value to set the EMPLOYEE_ID
     */
    public void setEmployeeId(Number value) {
        setAttributeInternal(EMPLOYEEID, value);
    }

    /**
     * Gets the attribute value for FIRST_NAME using the alias name FirstName.
     * @return the FIRST_NAME
     */
    public String getFirstName() {
        return (String)getAttributeInternal(FIRSTNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for FIRST_NAME using the alias name FirstName.
     * @param value value to set the FIRST_NAME
     */
    public void setFirstName(String value) {
        setAttributeInternal(FIRSTNAME, value);
    }


    /**
     * Gets the attribute value for LAST_NAME using the alias name LastName.
     * @return the LAST_NAME
     */
    public String getLastName() {
        return (String)getAttributeInternal(LASTNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_NAME using the alias name LastName.
     * @param value value to set the LAST_NAME
     */
    public void setLastName(String value) {
        setAttributeInternal(LASTNAME, value);
    }


    /**
     * Gets the attribute value for EMAILSEC using the alias name emailsec.
     * @return the EMAILSEC
     */
    public String getemailsec() {
        return (String) getAttributeInternal(EMAILSEC);
    }

    /**
     * Sets <code>value</code> as attribute value for EMAILSEC using the alias name emailsec.
     * @param value value to set the EMAILSEC
     */
    public void setemailsec(String value) {
        setAttributeInternal(EMAILSEC, value);
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
}
