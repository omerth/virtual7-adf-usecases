package com.virtual7.util.model.operations;

import com.virtual7.util.model.context.AChangeContext;
import com.virtual7.util.model.context.AFilterContext;
import com.virtual7.util.model.BaseAppModuleImpl;

public abstract class AChangeOperation {
    public AChangeOperation() {
        super();
    }


    public abstract boolean execute(BaseAppModuleImpl appModule, AChangeContext changeContext,
                                    AFilterContext filterContext);
}
