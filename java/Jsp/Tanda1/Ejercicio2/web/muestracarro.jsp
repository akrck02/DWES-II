<%-- 
    Document   : muestracarro.jsp
    Created on : 15-ene-2022, 20:42:11
    Author     : aketz
--%>

<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <br>
        <h1>Tu carro: </h1>
        <br>
        <%

            HashMap<String,Integer> purchased = (HashMap<String,Integer>)session.getAttribute("purchased");
            for (String product : purchased.keySet()) {
                
                out.print("<li>");
                out.print("<b class='bold'>" + product + "</b>");
                out.print("&nbsp;&nbsp;&nbsp; " + purchased.get(product) + " unidades ");
                out.print("</li>");
            }

        %>
    </body>
</html>
