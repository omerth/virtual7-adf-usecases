package com.virtual7.pivotHeaderLabels.view.managed;

import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.pivotTable.SizingManagerImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class PivotTableSizingManager extends SizingManagerImpl {
    @SuppressWarnings("compatibility:8451432913935739957")
    private static final long serialVersionUID = 1L;

    public PivotTableSizingManager() {
        super();
    }

    public int computeRowHeaderColumnWidth(String layer) {
        int width = super.computeRowHeaderColumnWidth(layer);
        try {
            ExtendedRenderKitService service =
                (ExtendedRenderKitService)Service.getRenderKitService(FacesContext.getCurrentInstance(),
                                                                      ExtendedRenderKitService.class);
            service.addScript(FacesContext.getCurrentInstance(), "doInitLabels()");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return width;
    }
}
