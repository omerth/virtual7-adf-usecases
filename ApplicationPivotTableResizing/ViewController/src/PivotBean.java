import java.util.Vector;

import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.pivotTable.CellFormat;
import oracle.adf.view.faces.bi.component.pivotTable.DataCellContext;
import oracle.adf.view.faces.bi.component.pivotTable.HeaderCellContext;
import oracle.adf.view.faces.bi.component.pivotTable.HeaderFormatManager;
import oracle.adf.view.faces.bi.component.pivotTable.SizingManager;
import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;

import oracle.dss.util.QDR;

public class PivotBean {
    private UIPivotTable pivotTable;
    static int i = 0;

    public PivotBean() {
    }
    
    public void setSize() {
        
    }

    public CellFormat dataFormat(DataCellContext dataCellContext) {
        return null;
    }

    public void setPivotTable(UIPivotTable pivotTable) {
        this.pivotTable = pivotTable;
    }

    public UIPivotTable getPivotTable() {
        return pivotTable;
    }

    public CellFormat headerFormat(HeaderCellContext headerCellContext) {
        SizingManager manager = getPivotTable().getSizingManager();
        //manager.setDefaultRowHeaderColumnWidth(350);
        return null;
    }

    public void setSize(ActionEvent actionEvent) {
    }
}
