<%-- 
    Document   : index
    Created on : 04-feb-2022, 8:19:00
    Author     : aketz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login: </h1>
        <form action="<%= getServletContext().getContextPath()  %>/ServletLogin" Method="POST">
            <label>
                Usuario: 
                <input type="text" name="usuario" value="" />
            </label>
            <label>
                Password 
                <input type="text" name="clave" value="" />
            </label>
            
            <input type="submit" value="Alumno" name="login" />
            <input type="submit" value="Impartidor" name="login" />
        </form>
        <% if(request.getAttribute("error") != null)
            out.print("<p style='color:red'>" + request.getAttribute("error") + "</p>");
            
         %>
    </body>
</html>
