package com.virtual7.adfDevelopmentPattern.view.managed;


import com.virtual7.adfDevelopmentPattern.model.ADFPatternOperationFactory;
import com.virtual7.adfDevelopmentPattern.model.context.ADFPatternChangeContext;
import com.virtual7.adfDevelopmentPattern.model.context.ADFPatternFilterContext;


import com.virtual7.util.view.ADFUtils;

import com.virtual7.util.view.JSFUtils;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.util.ResetUtils;

import oracle.jbo.domain.Number;


public class ADFPatternBackingBean {

    private static final String ACTION_FILTER = "filter";

    public ADFPatternBackingBean() {
        super();
    }

    public List<SelectItem> getDepartmentsList() {
        List<SelectItem> items =
            ADFUtils.selectItemsForIterator("DepartmentsLOVIterator", "DepartmentId", "DepartmentName");
        return items;
    }

    public void employeesLOVValueChanged(ValueChangeEvent valueChangeEvent) {
        ADFPatternFilterContext filterContext =
            (ADFPatternFilterContext)ADFUtils.getPageFlowScopeVariable("adfPatternFilterContext");
        filterContext.setDepartmentId((Number)valueChangeEvent.getNewValue());
        filterContext.setFilters(new String[] { ADFPatternOperationFactory.EMPLOYEE_FILTER });
        JSFUtils.navigateToAction(ACTION_FILTER);
    }

    public void saveActionListener(ActionEvent actionEvent) {
        ADFPatternChangeContext changeContext =
            (ADFPatternChangeContext)ADFUtils.getPageFlowScopeVariable("adfPatternChangeContext");
        ADFPatternFilterContext filterContext =
            (ADFPatternFilterContext)ADFUtils.getPageFlowScopeVariable("adfPatternFilterContext");
        changeContext.setChanges(new String[] { ADFPatternOperationFactory.COMMIT });
        filterContext.setFilters(new String[] { ADFPatternOperationFactory.EMPLOYEE_FILTER });
        JSFUtils.navigateToAction("change");
    }
    
    public void cancelActionListener(ActionEvent actionEvent) {
        ADFPatternChangeContext changeContext =
            (ADFPatternChangeContext)ADFUtils.getPageFlowScopeVariable("adfPatternChangeContext");
        ADFPatternFilterContext filterContext =
            (ADFPatternFilterContext)ADFUtils.getPageFlowScopeVariable("adfPatternFilterContext");
        changeContext.setChanges(new String[] { ADFPatternOperationFactory.ROLLBACK });
        filterContext.setFilters(new String[] { ADFPatternOperationFactory.EMPLOYEE_FILTER });
        JSFUtils.navigateToAction("change");
        ResetUtils.reset(actionEvent.getComponent());
    }
    
    public void addEmployeeActionListener(ActionEvent actionEvent) {
        ADFPatternChangeContext changeContext =
            (ADFPatternChangeContext)ADFUtils.getPageFlowScopeVariable("adfPatternChangeContext");
        ADFPatternFilterContext filterContext =
            (ADFPatternFilterContext)ADFUtils.getPageFlowScopeVariable("adfPatternFilterContext");
        changeContext.setChanges(new String[] { ADFPatternOperationFactory.ADD_EMPLOYEE });
        filterContext.setFilters(new String[] { ADFPatternOperationFactory.EMPLOYEE_FILTER });
        JSFUtils.navigateToAction("change");
    }
}
