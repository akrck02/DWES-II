<%-- 
    Document   : index
    Created on : 03-feb-2022, 12:34:50
    Author     : aketz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alejandria.biblo - Home</title>
        <link rel="stylesheet" href="./styles/bubbleUI-min.css"/>
    </head>
    <body class="box-center box-column">
        <h1>Biblioteca de Alejandría</h1>
        <p>La biblioteca digital mas grande del metaverso.</p>
        
        <div class="banner box-x-center">
            <a class='link' href='<%= getServletContext().getContextPath()%>/control'>Ver autores</a>
        </div>
    </body>
</html>
