package com.virtual7.entityValidaiton.model;

import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Mar 23 11:43:35 EET 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppModuleImpl extends ApplicationModuleImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public AppModuleImpl() {
    }

    /**
     * Container's getter for EmployeesVO1.
     * @return EmployeesVO1
     */
    public ViewObjectImpl getEmployeesVO1() {
        return (ViewObjectImpl)findViewObject("EmployeesVO1");
    }
}
