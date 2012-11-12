package com.virtual7.util.model;


import com.virtual7.util.model.context.AChangeContext;
import com.virtual7.util.model.context.AFilterContext;
import com.virtual7.util.model.operations.AChangeOperation;
import com.virtual7.util.model.operations.AFilterOperation;
import com.virtual7.util.model.operations.AOperationFactory;

import oracle.jbo.server.ApplicationModuleImpl;


public abstract class BaseAppModuleImpl extends ApplicationModuleImpl {


    public BaseAppModuleImpl() {
        super();
    }

    protected abstract AOperationFactory getOperationFactory();


    public void filterDataModel(AFilterContext filterContext) {
        if (filterContext != null) {
            if (filterContext.getFilters() != null) {
                for (String filter : filterContext.getFilters()) {
                    AFilterOperation filterOp = getOperationFactory().getFilterOperation(filter);
                    if (!filterOp.execute(this, filterContext)) {
                        break;
                    }
                }
            } else {
                AFilterOperation filterOp = getOperationFactory().getInitialFilterOperation();
                filterOp.execute(this, filterContext);
            }
        }
    }

    public void changeDataModel(AChangeContext changeContext, AFilterContext filterContext) {
        if (changeContext != null && changeContext.getChanges() != null) {
            for (String change : changeContext.getChanges()) {
                AChangeOperation changeOp = getOperationFactory().getChangeOperation(change);
                if (!changeOp.execute(this, changeContext, filterContext)) {
                    break;
                }
            }
        }
    }

    public void postChanges() {
        this.getDBTransaction().postChanges();
    }

    public void commit() {
        this.getDBTransaction().commit();
    }

    public void rollback() {
        this.getDBTransaction().rollback();
    }
}
