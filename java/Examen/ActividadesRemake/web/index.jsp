<%-- 
    Document   : index
    Created on : 15-feb-2022, 17:03:10
    Author     : aketz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actividades.com - Login</title>
    </head>
    <body>
        <h1>Login: </h1>
        <form action="ServletLogin" method="POST">
            <label>
                Usuario: &nbsp;
                <input name="usuario" type="text"/> 
            </label>
            <label>
                Password: &nbsp;
                <input name="clave" type="password"/> 
            </label>
            <input type="submit" value="Alumno" name="login" />
            <input type="submit" value="Impartidor" name="login" />
        </form>
        
        <% if(request.getAttribute("error") != null)
            out.print("<p style='color:red'>" + request.getAttribute("error") + "</p>");
         %>
         
    </body>
</html>
