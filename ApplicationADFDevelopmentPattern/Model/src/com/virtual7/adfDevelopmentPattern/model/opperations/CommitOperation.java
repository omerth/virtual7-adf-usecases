package com.virtual7.adfDevelopmentPattern.model.opperations;

import com.virtual7.util.model.BaseAppModuleImpl;
import com.virtual7.util.model.context.AChangeContext;
import com.virtual7.util.model.context.AFilterContext;
import com.virtual7.util.model.operations.AChangeOperation;

public class CommitOperation extends AChangeOperation {
    public CommitOperation() {
        super();
    }

    public boolean execute(BaseAppModuleImpl appModule, AChangeContext changeContext, AFilterContext filterContext) {
        appModule.commit();
        return false;
    }
}
