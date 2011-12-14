package com.virtual7.textMessenger.controller;


import com.virtual7.textMessenger.model.DemoModel;
import com.virtual7.textMessenger.model.Message;

import java.util.HashMap;
import java.util.List;

public abstract class ATextMessengerServlet extends AHttpServlet {
    public static final String PARAM_OPERATION = "operation";
    public static final String PARAM_OPERATION_SAVE = "save";
    public static final String PARAM_OPERATION_DELETE = "delete";
    public static final String PARAM_CONTENT = "content";
    public static final String PARAM_ID = "id";
    private String[] ALL_PARAMS = new String[] { PARAM_OPERATION, PARAM_CONTENT, PARAM_ID };

    public static final String RENDERCONTEXT_MESSAGES = "RENDERCONTEXT_MESSAGES";

    protected String[] getParamNames() {
        return ALL_PARAMS;
    }

    protected void doAction(HashMap<String, String> params) {
        String operation = params.get(PARAM_OPERATION);
        String messageContent = params.get(PARAM_CONTENT);
        String messageID = params.get(PARAM_ID);
        //TODO implement server actions here

        if (PARAM_OPERATION_SAVE.equals(operation)) {
            DemoModel.getModel().saveMessage(messageContent);
        }

        if (PARAM_OPERATION_DELETE.equals(operation)) {
            DemoModel.getModel().deleteMessage(messageID);
        }
    }

    protected HashMap<String, Object> doRender(HashMap<String, String> params) {
        HashMap<String, Object> renderContext = new HashMap<String, Object>();
        List<Message> messages = DemoModel.getModel().getMessages();
        renderContext.put(RENDERCONTEXT_MESSAGES, messages);
        return renderContext;
    }

}
