package com.virtual7.pivotHeaderLabels.view.managed;

import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;

public class ManagedBean {

    private PivotTableSizingManager sizingManager;
    private UIPivotTable pivotTable;

    public ManagedBean() {
        super();
    }


    public void setSizingManager(PivotTableSizingManager sizingManager) {
        this.sizingManager = sizingManager;
    }

    public PivotTableSizingManager getSizingManager() {
        if (sizingManager == null) {
            sizingManager = new PivotTableSizingManager();
        }
        return sizingManager;
    }

    public void setPivotTable(UIPivotTable pivotTable) {
        this.pivotTable = pivotTable;
        //        pivotTable.setSizingManager(getSizingManager());
    }

    public UIPivotTable getPivotTable() {
        return pivotTable;
    }
}
