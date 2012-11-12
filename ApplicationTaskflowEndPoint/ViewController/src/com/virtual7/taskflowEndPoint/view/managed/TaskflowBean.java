package com.virtual7.taskflowEndPoint.view.managed;


import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import org.apache.myfaces.trinidad.event.DisclosureEvent;


public class TaskflowBean {
    public TaskflowBean() {
        super();
    }

    public void firstTabDisclosureListener(DisclosureEvent disclosureEvent) {
        if (!disclosureEvent.isExpanded()) {
            RichRegion region = this.findRegionById("r1");
            region.queueActionEventInRegion(createMethodExpressionFromString("firstRegionAction"), 
                                            null, null,false, 0, 0, PhaseId.INVOKE_APPLICATION);
        }
    }

    public void secondTabDisclosureListener(DisclosureEvent disclosureEvent) {
        if (!disclosureEvent.isExpanded()) {
            RichRegion region = this.findRegionById("r2");
            region.queueActionEventInRegion(createMethodExpressionFromString("secondRegionAction"), 
                                            null, null,false, 0, 0, PhaseId.INVOKE_APPLICATION);
        }
    }
    
    private RichRegion findRegionById(String regId){
      FacesContext fctx = FacesContext.getCurrentInstance();
      UIViewRoot viewRoot = fctx.getViewRoot();
      RichRegion region = (RichRegion) viewRoot.findComponent(regId);
      return region;
    }
    
    private MethodExpression createMethodExpressionFromString(String s){
      FacesContext fctx = FacesContext.getCurrentInstance();
      ELContext elctx = fctx.getELContext();
      ExpressionFactory exprFactory = fctx.getApplication().getExpressionFactory();
      MethodExpression methodExpr = exprFactory.createMethodExpression( 
                     elctx,               
                     s,
                     null, 
                     new Class[]{});  
      return methodExpr;
    }
}
