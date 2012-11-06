package com.virtual7.util.model.operations;

import com.virtual7.util.model.context.AFilterContext;
import com.virtual7.util.model.BaseAppModuleImpl;

public abstract class AFilterOperation {
    public AFilterOperation() {
        super();
    }

    public abstract boolean execute(BaseAppModuleImpl appModule, AFilterContext filterContext);
}
