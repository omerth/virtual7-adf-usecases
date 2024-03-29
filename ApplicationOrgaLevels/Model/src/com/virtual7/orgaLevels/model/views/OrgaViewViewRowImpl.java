package com.virtual7.orgaLevels.model.views;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Feb 16 16:33:03 EET 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class OrgaViewViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        ParentId {
            public Object get(OrgaViewViewRowImpl obj) {
                return obj.getParentId();
            }

            public void put(OrgaViewViewRowImpl obj, Object value) {
                obj.setParentId((Number)value);
            }
        }
        ,
        Id {
            public Object get(OrgaViewViewRowImpl obj) {
                return obj.getId();
            }

            public void put(OrgaViewViewRowImpl obj, Object value) {
                obj.setId((Number)value);
            }
        }
        ,
        Lv {
            public Object get(OrgaViewViewRowImpl obj) {
                return obj.getLv();
            }

            public void put(OrgaViewViewRowImpl obj, Object value) {
                obj.setLv((Number)value);
            }
        }
        ,
        Val {
            public Object get(OrgaViewViewRowImpl obj) {
                return obj.getVal();
            }

            public void put(OrgaViewViewRowImpl obj, Object value) {
                obj.setVal((Number)value);
            }
        }
        ,
        Descr {
            public Object get(OrgaViewViewRowImpl obj) {
                return obj.getDescr();
            }

            public void put(OrgaViewViewRowImpl obj, Object value) {
                obj.setDescr((String)value);
            }
        }
        ,
        OrgaViewView_2 {
            public Object get(OrgaViewViewRowImpl obj) {
                return obj.getOrgaViewView_2();
            }

            public void put(OrgaViewViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        OrgaViewView1 {
            public Object get(OrgaViewViewRowImpl obj) {
                return obj.getOrgaViewView1();
            }

            public void put(OrgaViewViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(OrgaViewViewRowImpl object);

        public abstract void put(OrgaViewViewRowImpl object, Object value);

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
    public static final int PARENTID = AttributesEnum.ParentId.index();
    public static final int ID = AttributesEnum.Id.index();
    public static final int LV = AttributesEnum.Lv.index();
    public static final int VAL = AttributesEnum.Val.index();
    public static final int DESCR = AttributesEnum.Descr.index();
    public static final int ORGAVIEWVIEW_2 = AttributesEnum.OrgaViewView_2.index();
    public static final int ORGAVIEWVIEW1 = AttributesEnum.OrgaViewView1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public OrgaViewViewRowImpl() {
    }

    /**
     * Gets OrgaView entity object.
     * @return the OrgaView
     */
    public EntityImpl getOrgaView() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for PARENT_ID using the alias name ParentId.
     * @return the PARENT_ID
     */
    public Number getParentId() {
        return (Number) getAttributeInternal(PARENTID);
    }

    /**
     * Sets <code>value</code> as attribute value for PARENT_ID using the alias name ParentId.
     * @param value value to set the PARENT_ID
     */
    public void setParentId(Number value) {
        setAttributeInternal(PARENTID, value);
    }

    /**
     * Gets the attribute value for ID using the alias name Id.
     * @return the ID
     */
    public Number getId() {
        return (Number) getAttributeInternal(ID);
    }

    /**
     * Sets <code>value</code> as attribute value for ID using the alias name Id.
     * @param value value to set the ID
     */
    public void setId(Number value) {
        setAttributeInternal(ID, value);
    }

    /**
     * Gets the attribute value for LV using the alias name Lv.
     * @return the LV
     */
    public Number getLv() {
        return (Number) getAttributeInternal(LV);
    }

    /**
     * Sets <code>value</code> as attribute value for LV using the alias name Lv.
     * @param value value to set the LV
     */
    public void setLv(Number value) {
        setAttributeInternal(LV, value);
    }

    /**
     * Gets the attribute value for VAL using the alias name Val.
     * @return the VAL
     */
    public Number getVal() {
        return (Number) getAttributeInternal(VAL);
    }

    /**
     * Sets <code>value</code> as attribute value for VAL using the alias name Val.
     * @param value value to set the VAL
     */
    public void setVal(Number value) {
        setAttributeInternal(VAL, value);
    }

    /**
     * Gets the attribute value for DESCR using the alias name Descr.
     * @return the DESCR
     */
    public String getDescr() {
        return (String) getAttributeInternal(DESCR);
    }

    /**
     * Sets <code>value</code> as attribute value for DESCR using the alias name Descr.
     * @param value value to set the DESCR
     */
    public void setDescr(String value) {
        setAttributeInternal(DESCR, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link OrgaViewView_2.
     */
    public RowIterator getOrgaViewView_2() {
        return (RowIterator)getAttributeInternal(ORGAVIEWVIEW_2);
    }

    /**
     * Gets the view accessor <code>RowSet</code> OrgaViewView1.
     */
    public RowSet getOrgaViewView1() {
        return (RowSet)getAttributeInternal(ORGAVIEWVIEW1);
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
