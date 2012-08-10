package com.virtual7.dinamicPivotTable.view.managed;

import com.virtual7.util.view.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;

import org.apache.myfaces.trinidad.component.UIXSwitcher;

public class SelectPivotTableBean {
    private int selPivTb = 0;
    private UIPivotTable pivotTable1;
    private UIPivotTable pivotTable2;
    private UIPivotTable pivotTable3;


    public SelectPivotTableBean() {
        super();
    }

    public void setSelPivTb(int selPivTb) {
        this.selPivTb = selPivTb;
    }

    public int getSelPivTb() {
        return selPivTb;
    }

    public void refreshSwitcher(ActionEvent actionEvent) {
        ADFUtils.resetPivotTable(this.getPivotTable1());
        ADFUtils.resetPivotTable(this.getPivotTable2());
        ADFUtils.resetPivotTable(this.getPivotTable3());
    }

    public void setPivotTable1(UIPivotTable pivotTable1) {
        this.pivotTable1 = pivotTable1;
    }

    public UIPivotTable getPivotTable1() {
        return pivotTable1;
    }

    public void setPivotTable2(UIPivotTable pivotTable2) {
        this.pivotTable2 = pivotTable2;
    }

    public UIPivotTable getPivotTable2() {
        return pivotTable2;
    }

    public void setPivotTable3(UIPivotTable pivotTable3) {
        this.pivotTable3 = pivotTable3;
    }

    public UIPivotTable getPivotTable3() {
        return pivotTable3;
    }
}
