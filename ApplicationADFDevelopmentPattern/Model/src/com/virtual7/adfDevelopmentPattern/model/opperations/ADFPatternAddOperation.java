package com.virtual7.adfDevelopmentPattern.model.opperations;


import com.virtual7.adfDevelopmentPattern.model.ADFPatternAMImpl;
import com.virtual7.adfDevelopmentPattern.model.views.EmployeesVORowImpl;
import com.virtual7.util.model.BaseAppModuleImpl;
import com.virtual7.util.model.context.AChangeContext;
import com.virtual7.util.model.context.AFilterContext;
import com.virtual7.util.model.operations.AChangeOperation;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;


public class ADFPatternAddOperation extends AChangeOperation {
    public ADFPatternAddOperation() {
        super();
    }

    public boolean execute(BaseAppModuleImpl appModule, AChangeContext changeContext, AFilterContext filterContext) {
        ADFPatternAMImpl patternAM = (ADFPatternAMImpl)appModule;
        EmployeesVORowImpl addedEmployee = (EmployeesVORowImpl)patternAM.getEmployeesTable().createRow();
        addedEmployee.setHireDate(new Timestamp(System.currentTimeMillis()));
        addedEmployee.setJobId("ST_MAN");
        addedEmployee.setDepartmentId(new Number(100));
        patternAM.getEmployeesTable().insertRow(addedEmployee);
        return true;
    }
}
