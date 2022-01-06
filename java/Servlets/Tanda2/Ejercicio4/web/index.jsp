<%-- 
    Document   : index
    Created on : 06-ene-2022, 12:53:25
    Author     : aketz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session = request.getSession(true);
    session.invalidate();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiniela.com - Inicio</title>
        <link rel="stylesheet" href="./styles/main.css"/>
    </head>
    <body class="box-column box-center">
        <h1>Inicia tu apuesta:</h1>
        <br>
        <form action="/Ejercicio4/WriteBet" method="post" class="box-row">
            <label>
                Nombre: &nbsp;
                <input type="text" name="name" value="" />
            </label>
            <input type="submit" value="Escribir apuesta" name="write">
        </form>
        
        <p class="text-error"> 
        <%
            if(request.getParameter("InvalidName") != null){
              out.print("Introdúzca un nómbre válido");
            }
        %>
        </p>
        
    </body>
</html>
