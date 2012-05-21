package com.virtual7.textMessenger.controller;


import com.virtual7.textMessenger.model.Message;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TextMessengerServlet extends ATextMessengerServlet {


    protected void doView(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                           IOException {
        HashMap<String, Object> renderContext = getRenderContext(request);

        //implement view of the application here
        List<Message> messages = (List<Message>)renderContext.get(RENDERCONTEXT_MESSAGES);

        StringBuffer responseText = new StringBuffer();
        responseText.append("<html>");
        responseText.append("<head><title>DemoServlet</title></head>");
        responseText.append("<body>");
        responseText.append("<table>");
        responseText.append("<tr><th>ID</th><th>CONTENT</th></tr>");
        for (Message msg : messages) {
            responseText.append("<tr>");
            responseText.append("<td>" + msg.getId() + "</td>");
            responseText.append("<td>" + msg.getContent() + "</td>");
            responseText.append("<td>");
            responseText.append("<form name=\"input" + msg.getId() + "\" action=\"textmessenger\" method=\"post\">");
            responseText.append("<input type=\"hidden\" name=\"" + PARAM_OPERATION + "\" value=\"" +
                                PARAM_OPERATION_DELETE + "\"/>");
            responseText.append("<input type=\"hidden\" name=\"" + PARAM_ID + "\" value=\"" + msg.getId() + "\"/>");
            responseText.append("<input type=\"submit\" value=\"Delete\"/>");
            responseText.append("</form>");
            responseText.append("</td>");
            responseText.append("</tr>");
        }
        responseText.append("</table>");

        responseText.append("<form name=\"input\" action=\"textmessenger\" method=\"post\">");
        responseText.append("<input type=\"hidden\" name=\"" + PARAM_OPERATION + "\" value=\"" + PARAM_OPERATION_SAVE +
                            "\"/>");
        responseText.append("<input type=\"text\" name=\"" + PARAM_CONTENT + "\"/>");
        responseText.append("<input type=\"submit\" value=\"Post\"/>");
        responseText.append("</form>");
        responseText.append("</body></html>");


        response.setContentType(CONTENT_TYPE);


        PrintWriter out = response.getWriter();
        out.println(responseText.toString());

        out.close();
    }
}
