package com.virtual7.pdfGenerator.model.client;

import com.virtual7.pdfGenerator.model.common.EmployeesAppModule;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Sep 21 10:23:30 EEST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EmployeesAppModuleClient extends ApplicationModuleImpl implements EmployeesAppModule {
    /**
     * This is the default constructor (do not remove).
     */
    public EmployeesAppModuleClient() {
    }


    public void generatePDF(String xml) {
        Object _ret = this.riInvokeExportedMethod(this,"generatePDF",new String [] {"java.lang.String"},new Object[] {xml});
        return;
    }

    public void generatePDF() {
        Object _ret = this.riInvokeExportedMethod(this,"generatePDF",null,null);
        return;
    }
}
