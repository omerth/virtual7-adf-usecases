package com.virtual7.dummyDataControl.view.managed;

public class SelectionManagerBean {
    public SelectionManagerBean() {
        super();
    }

    public void setSelectedId(oracle.jbo.domain.Number id) {
        System.out.println("selected id: " + id);
    }
}
