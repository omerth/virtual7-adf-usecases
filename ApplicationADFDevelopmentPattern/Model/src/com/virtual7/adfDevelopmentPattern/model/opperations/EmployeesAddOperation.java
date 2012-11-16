package com.virtual7.adfDevelopmentPattern.model.opperations;


import com.virtual7.adfDevelopmentPattern.model.ADFPatternAMImpl;
import com.virtual7.adfDevelopmentPattern.model.context.ADFPatternChangeContext;
import com.virtual7.adfDevelopmentPattern.model.context.ADFPatternFilterContext;
import com.virtual7.adfDevelopmentPattern.model.views.EmployeesVORowImpl;
import com.virtual7.util.model.BaseAppModuleImpl;
import com.virtual7.util.model.context.AChangeContext;
import com.virtual7.util.model.context.AFilterContext;
import com.virtual7.util.model.operations.AChangeOperation;


public class EmployeesAddOperation extends AChangeOperation {
    public EmployeesAddOperation() {
        super();
    }

    public boolean execute(BaseAppModuleImpl appModule, AChangeContext changeContext, AFilterContext filterContext) {
        ADFPatternFilterContext filterCtx = (ADFPatternFilterContext)filterContext;
        ADFPatternChangeContext changeCtx = (ADFPatternChangeContext)changeContext;

        ADFPatternAMImpl patternAM = (ADFPatternAMImpl)appModule;
        EmployeesVORowImpl addedEmployee = (EmployeesVORowImpl)patternAM.getEmployeesTable().createRow();
        addedEmployee.setHireDate(changeCtx.getHireDate());
        addedEmployee.setJobId(changeCtx.JOB_ID);
        addedEmployee.setDepartmentId(filterCtx.getDepartmentId() == null ? filterCtx.DEFAULT_DEPARTMENT :
                                      filterCtx.getDepartmentId());
        patternAM.getEmployeesTable().insertRow(addedEmployee);
        return true;
    }
}
