package com.virtual7.textMessenger.controller;

import java.io.IOException;

import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class AHttpServlet extends HttpServlet {
    protected static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public static final String RENDER_CONTEXT = "RENDER_CONTEXT";

    public static HashMap<String, Object> getRenderContext(HttpServletRequest request) {
        return (HashMap<String, Object>)request.getAttribute(RENDER_CONTEXT);
    }

    protected abstract void doView(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                                    IOException;

    protected abstract String[] getParamNames();

    protected abstract void doAction(HashMap<String, String> params);

    protected abstract HashMap<String, Object> doRender(HashMap<String, String> params);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HashMap<String, String> params = readParams(request);

        //VIEW
        //responsible to visualize model
        HashMap<String, Object> renderContext = doRender(params);
        request.setAttribute(RENDER_CONTEXT, renderContext);
        doView(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, String> params = readParams(request);

        //CONTROLLER
        //responsible to control application workflow
        doAction(params);

        //VIEW
        //responsible to visualize model
        HashMap<String, Object> renderContext = doRender(params);
        request.setAttribute(RENDER_CONTEXT, renderContext);
        doView(request, response);
    }


    private HashMap<String, String> readParams(HttpServletRequest request) {
        HashMap<String, String> params = new HashMap<String, String>();
        for (String paramName : getParamNames()) {
            String paramValue = "";
            try {
                paramValue = request.getParameter(paramName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            params.put(paramName, paramValue);
        }
        return params;
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

}
