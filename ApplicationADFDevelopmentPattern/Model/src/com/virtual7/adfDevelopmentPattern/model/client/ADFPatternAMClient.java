package com.virtual7.adfDevelopmentPattern.model.client;

import com.virtual7.adfDevelopmentPattern.model.common.ADFPatternAM;

import com.virtual7.util.model.context.AChangeContext;
import com.virtual7.util.model.context.AFilterContext;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Nov 05 13:47:36 EET 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ADFPatternAMClient extends ApplicationModuleImpl implements ADFPatternAM {
    /**
     * This is the default constructor (do not remove).
     */
    public ADFPatternAMClient() {
    }

    public void rollback() {
        Object _ret = this.riInvokeExportedMethod(this,"rollback",null,null);
        return;
    }

    public void postChanges() {
        Object _ret = this.riInvokeExportedMethod(this,"postChanges",null,null);
        return;
    }


    public void commit() {
        Object _ret = this.riInvokeExportedMethod(this,"commit",null,null);
        return;
    }

    public void filterDataModel(AFilterContext filterContext) {
        Object _ret =
            this.riInvokeExportedMethod(this,"filterDataModel",new String [] {"com.virtual7.util.model.context.AFilterContext"},new Object[] {filterContext});
        return;
    }

    public void changeDataModel(AChangeContext changeContext, AFilterContext filterContext) {
        Object _ret =
            this.riInvokeExportedMethod(this,"changeDataModel",new String [] {"com.virtual7.util.model.context.AChangeContext","com.virtual7.util.model.context.AFilterContext"},new Object[] {changeContext, filterContext});
        return;
    }
}
