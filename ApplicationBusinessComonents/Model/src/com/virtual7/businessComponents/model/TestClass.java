package com.virtual7.businessComponents.model;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;
import oracle.jbo.mom.DefinitionManager;
import oracle.jbo.server.DefObject;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowSetImpl;

public class TestClass {
    public static void tuning(int fetchSize, int defaultRangeSize, int rangeSize) {
        String amDef = "com.virtual7.businessComponents.model.AppModule";
        String config = "AppModuleLocal";
        ApplicationModule am = Configuration.createRootApplicationModule(amDef, config);
        
        ViewObjectImpl empcrit = (ViewObjectImpl) am.findViewObject("EmployeesWithCriteriasView");
        
        System.out.println("++++++++++++++++++++++++++++++");
        long t1 = System.currentTimeMillis();
        empcrit.setFetchSize((short)fetchSize);
        empcrit.setDefaultRangeSize(defaultRangeSize);
        empcrit.setRangeSize(rangeSize);
        //empcrit.setAccessMode(ViewObjectImpl.FETCH_ALL);
        
        
        System.out.println("VO fetch size: " + empcrit.getFetchSize());
        System.out.println("VO default range size: " + empcrit.getDefaultRangeSize());
        System.out.println("VO range size: " + empcrit.getRangeSize());
        System.out.println("------------------------------");
        
        ViewRowSetImpl defaultRowSet = empcrit.getDefaultRowSet();
        Row[] allRowsInRange1 = defaultRowSet.getAllRowsInRange();
        System.out.println("DefaultRS fetched row count: " + defaultRowSet.getFetchedRowCount());
        System.out.println("DefaultRS range size: " + defaultRowSet.getRangeSize());
        for (int i = 0; i < allRowsInRange1.length; i++) {
            System.out.println(i+1 + ". Name: " + allRowsInRange1[i].getAttribute("FirstName") + allRowsInRange1[i].getAttribute("LastName"));
        }
        
        System.out.println("------------------------------");
        ViewCriteria vc = empcrit.getViewCriteria("SalaryVC");
        RowIterator byViewCriteria = empcrit.findByViewCriteria(vc, 1000, ViewObjectImpl.FETCH_DEFAULT);
        Row[] allRowsInRange = byViewCriteria.getAllRowsInRange();
        //byViewCriteria.setRangeSize(20);
        System.out.println("ByViewCrit fetched row count: " + byViewCriteria.getFetchedRowCount());
        System.out.println("ByViewCrit range size: " + byViewCriteria.getRangeSize());
        for (int i = 0; i < allRowsInRange.length; i++) {
            System.out.println(i+1 + ". Name: " + allRowsInRange[i].getAttribute("FirstName") + allRowsInRange[i].getAttribute("LastName"));
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Time passed: " + (t2 - t1));
        System.out.println("++++++++++++++++++++++++++++++");
    }   
    
    public static void main(String[] args) {
        tuning(26, 25, 25);
    }
}
