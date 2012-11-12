package com.virtual7.adfDevelopmentPattern.model.opperations;

import com.virtual7.util.model.BaseAppModuleImpl;
import com.virtual7.util.model.context.AChangeContext;
import com.virtual7.util.model.context.AFilterContext;
import com.virtual7.util.model.operations.AChangeOperation;

public class RollbackOperation extends AChangeOperation {
    public RollbackOperation() {
        super();
    }

    public boolean execute(BaseAppModuleImpl appModule, AChangeContext changeContext, AFilterContext filterContext) {
        appModule.rollback();
        return false;
    }
}
