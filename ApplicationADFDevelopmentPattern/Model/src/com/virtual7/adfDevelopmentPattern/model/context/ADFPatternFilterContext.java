package com.virtual7.adfDevelopmentPattern.model.context;

import com.virtual7.util.model.context.AFilterContext;

import oracle.jbo.domain.Number;

public class ADFPatternFilterContext extends AFilterContext {

    private static final long serialVersionUID = 1L;
    private Number departmentId;
    
    public ADFPatternFilterContext() {
        super();
    }

    public void setDepartmentId(Number departmentId) {
        this.departmentId = departmentId;
    }

    public Number getDepartmentId() {
        return departmentId;
    }
}
