<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="com.virtual7.textMessenger.model.Message"%>
<%@ page import="com.virtual7.textMessenger.controller.TextMessengerJSPServlet"%>
<%
HashMap<String,Object> renderContext = TextMessengerJSPServlet.getRenderContext(request);
List<Message> messages = (List<Message>)renderContext.get(TextMessengerJSPServlet.RENDERCONTEXT_MESSAGES);
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>demo</title>
  </head>
    <table>
    <tr><th>ID</th><th>CONTENT</th></tr>
    <%
  
  for(Message msg:messages){
  %>
    <tr>
    <td><%=msg.getId()%></td>
    <td><%=msg.getContent()%></td>
    <td>
    <form name="input"  action="textmessengerjsp" method="post">
    <input type="hidden" name="<%= TextMessengerJSPServlet.PARAM_OPERATION %>"  value="<%= TextMessengerJSPServlet.PARAM_OPERATION_DELETE %>">
    <input type="hidden" name="<%= TextMessengerJSPServlet.PARAM_ID %>"  value="<%=msg.getId()%>" >
    <input type="submit" value="Delete">
    </form>
    </td>
    </tr>
    
    <% 
  }
  %>
  </table>
  
  <form name="input" action="textmessengerjsp" method="post">
  <input type="hidden" name="<%= TextMessengerJSPServlet.PARAM_OPERATION %>"  value="<%= TextMessengerJSPServlet.PARAM_OPERATION_SAVE %>" >
  <input type="text" name="<%=TextMessengerJSPServlet.PARAM_CONTENT %>">
  <input type="submit" value="Post">
  </form>
  <%--<form name="input" action= "textmessengerjsp" method="POST">
        <input type="text" name="" size=30>;
        <input type="submit" name="submit" value="Submit">;
        <input type=\"text\" name=\"" + PARAM_CONTENT + "\"/>");
        <input type=\"submit\" value=\"Post\"/>");
  </form>--%>
  </body>
</html>