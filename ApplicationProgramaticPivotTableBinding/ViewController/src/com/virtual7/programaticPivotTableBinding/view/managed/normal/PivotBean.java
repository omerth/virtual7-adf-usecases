package com.virtual7.programaticPivotTableBinding.view.managed.normal;

public class PivotBean {
    
    PivotModel dataModel;
    
    public PivotBean() {
        dataModel = new PivotModel();
        dataModel.getRowsetDataSource().setTotalsEnabled(true);
    }

    public PivotModel getDataModel() {
        return dataModel;
    }
}
