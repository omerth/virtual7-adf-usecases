package com.virtual7.adfDevelopmentPattern.model.context;


import com.virtual7.util.model.context.AFilterContext;

import oracle.jbo.domain.Number;

public class ADFPatternFilterContext extends AFilterContext {

    private static final long serialVersionUID = 1L;

    public static final Number DEFAULT_DEPARTMENT = new Number(100);

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
