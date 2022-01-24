<%-- 
    Document   : ilegals.jsp
    Created on : 20-ene-2022, 17:44:58
    Author     : aketz
--%>

<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<%
  if(getServletContext().getAttribute("banned") == null){
      response.sendRedirect(getServletContext().getContextPath());
      return;
  } 
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>myBank - ilegals</title>
        <link rel="stylesheet" href="./styles/bubbleUI-min.css"/>
    </head>
    <style>
        input[type=text]#username {
            max-width: 10rem;
            margin: 1rem;
        }
    </style>
    <body>
        <h1>Opciones de seguridad: </h1>
        <form action="<%= getServletContext().getContextPath()%>/Ilegal" method="post" class="box-row box-y-center">
            <label for="username"> Nombre de usuario: </label>
            <input type="text" name="username" id="username">
            <input type="submit" name="ban" value="bannear">
        </form>

        <%
            final String error = (String) request.getAttribute("error");
            final String success = (String) request.getAttribute("success");
            final ArrayList<String> users = (ArrayList<String>) getServletContext().getAttribute("banned");
        %>

        <c:if test="${error != null}">  
            <p class="text-error"><c:out value="${error}" /></p>
        </c:if>  
            
        <c:if test="${success != null}">  
            <p class="text-success"><c:out value="${success}" /></p>
        </c:if>  
            
        <br>
        <h1>Lista negra: </h1>
        <br>
        
        <c:forEach items="${users}" var="username" >
                <li>${username} </li>
        </c:forEach>
                
        <% 
             for (String user : users) {
                out.print("<li>" + user + "</li>");
             }
        %>
    </body>
</html>


