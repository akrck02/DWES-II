<%-- 
    Document   : index
    Created on : 15-ene-2022, 19:48:57
    Author     : aketz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Compras.com</title>
        <link rel="stylesheet" href="./styles/bubbleUI-min.css"/>
    </head>
    <body>
        <form action="<%= getServletContext().getContextPath() + "/products" %>" method="get">

            <select name="category">
                <option value="">todos</option>
                <option value="100">100</option>
                <option value="200">200</option>
                <option value="300">300</option>
            </select>
            <input type="submit" value="Ver productos">
        </form>
    </body>
</html>
