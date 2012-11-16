package com.virtual7.adfDevelopmentPattern.model.opperations;

import com.virtual7.adfDevelopmentPattern.model.ADFPatternAMImpl;
import com.virtual7.adfDevelopmentPattern.model.context.ADFPatternFilterContext;
import com.virtual7.adfDevelopmentPattern.model.views.EmployeesVOImpl;
import com.virtual7.util.model.BaseAppModuleImpl;
import com.virtual7.util.model.context.AFilterContext;
import com.virtual7.util.model.operations.AFilterOperation;

public class EmployeesFilterOperation extends AFilterOperation {
    public EmployeesFilterOperation() {
        super();
    }

    public boolean execute(BaseAppModuleImpl appModule, AFilterContext filterContext) {
        ADFPatternAMImpl patternAM = (ADFPatternAMImpl)appModule;
        ADFPatternFilterContext patternFilterContext = (ADFPatternFilterContext)filterContext;
        EmployeesVOImpl employees = (EmployeesVOImpl)patternAM.getEmployeesTable();
        employees.setDeptIdVar(patternFilterContext.getDepartmentId());
        employees.executeQuery();
        return true;
    }
}
