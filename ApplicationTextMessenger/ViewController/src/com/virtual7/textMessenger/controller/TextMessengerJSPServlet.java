package com.virtual7.textMessenger.controller;

import java.io.IOException;

import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TextMessengerJSPServlet extends ATextMessengerServlet {
    private static final String JSP_PAGE = "/WEB-INF/com/virtual7/textMessenger/view/textMessenger.jsp";


    protected void doView(HttpServletRequest request, HttpServletResponse response) throws IOException,
                                                                                           ServletException {
        ServletContext servletContext = request.getSession().getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(JSP_PAGE);
        requestDispatcher.include(request, response);
    }


}
