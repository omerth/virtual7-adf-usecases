package com.virtual7.phaseListener.view;

import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.model.RegionBinding;
import oracle.adf.model.RegionContext;

public class MyRegionController implements oracle.adf.model.RegionController {
    public MyRegionController() {
        super();
        System.out.println("Region controller initialized");
    }

    public boolean refreshRegion(RegionContext regionCtx) {
        int refreshFlag = regionCtx.getRefreshFlag();
        if (refreshFlag == RegionBinding.PREPARE_MODEL) {
            System.out.println("Refresh region");
        }
        // Propagate the refresh to the inner binding container
        regionCtx.getRegionBinding().refresh(refreshFlag);
        return false;
    }

    public boolean validateRegion(RegionContext regionCtx) {
        System.out.println("Validate region");
        regionCtx.getRegionBinding().validate();
        return false;
    }

    public boolean isRegionViewable(RegionContext regionContext) {
        System.out.println("Is region viewable?");
        return false;
    }

    public String getName() {
        System.out.println("Get name?");
        return "";
    }
}
