package com.virtual7.adfDevelopmentPattern.model.context;


import com.virtual7.util.model.context.AChangeContext;

import oracle.jbo.domain.Timestamp;

public class ADFPatternChangeContext extends AChangeContext {
    private static final long serialVersionUID = 4684697966515426645L;

    public static final String JOB_ID = "ST_MAN";

    private Timestamp hireDate;

    public ADFPatternChangeContext() {
        super();
    }

    public void setHireDate(Timestamp hireDate) {
        this.hireDate = hireDate;
    }

    public Timestamp getHireDate() {
        return hireDate;
    }
}
