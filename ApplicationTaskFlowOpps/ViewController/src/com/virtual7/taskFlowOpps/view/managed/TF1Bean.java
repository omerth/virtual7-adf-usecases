package com.virtual7.taskFlowOpps.view.managed;

import java.util.Map;
import oracle.adf.share.ADFContext;

public class TF1Bean {
    public TF1Bean() {
    }

    public void spellCheck() {
        ADFContext ctx = ADFContext.getCurrent();
        Map<String, Object> flowScope = ctx.getPageFlowScope();
        String s = (String) flowScope.get("returnedParamByTF4");
        if ("page3".equals(s)) {
            flowScope.put("methodReturn", "fail");
        }      
    }

    public String routingCheck(String s) {
        if ("Go to page1".equals(s)) {
            return "out1";
        } else return "out2";
    }

    public void throwException() {
        throw new NullPointerException();
    }
}
