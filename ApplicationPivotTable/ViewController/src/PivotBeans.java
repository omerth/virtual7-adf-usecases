import java.util.HashMap;

import oracle.adf.view.faces.bi.component.pivotTable.CellFormat;
import oracle.adf.view.faces.bi.component.pivotTable.HeaderCellContext;

import oracle.dss.util.QDR;

public class PivotBeans {
    public PivotBeans() {
    }

    public CellFormat headerFormat(HeaderCellContext headerCellContext) {
        CellFormat cellFormat = new CellFormat(null, null, null);
        cellFormat.setCustomData(new HashMap<String, Object>());
        int layer = headerCellContext.getLayer();
        QDR qdr = headerCellContext.getQDR();
        if (layer == 0) {
            if (qdr.getDimMember("PhaseId") != null) {
                cellFormat.setStyle("border-bottom:none;");
            } else if (qdr.getDimMember("GroupId") != null) {
                cellFormat.setStyle("display:none;");
            }

        } else if (layer == 1) {
            if (qdr.getDimMember("DataLayer") != null) {
                cellFormat.setStyle("display:none;");
            } else if (qdr.getDimMember("Id") != null) {
                cellFormat.setStyle("border-left:none;");
            }
        }
        return cellFormat;

    }
}
