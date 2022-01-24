<%-- 
    Document   : movements
    Created on : 16-ene-2022, 18:43:03
    Author     : aketz
--%>

<%@page import="bean.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  


<% final Account account = (Account) session.getAttribute("account");%>
<c:if test="${account == null}">  
    <c:redirect url="${request.getServletContext().getContextPath()}"/>  
</c:if> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>myBank - movements</title>
        <link rel="stylesheet" href="./styles/bubbleUI-min.css"/>
    </head>
    <body>
        <h1>Movimientos</h1><br>

        <form action="<%= getServletContext().getContextPath()%>/Movement" method="post">
            <table>
                <tr>
                    <td>Titular</td>
                    <td><c:out value="${account.getOwner()}"/></td>
                </tr>
                <tr>
                    <td>Saldo actual</td>
                    <td><c:out value="${account.getBalance()}"/></td>
                </tr>
                <tr>
                    <td>Cantidad</td>
                    <td><input type="number" name="quantity" step="0.01"></td>
                </tr>
            </table>
            <div class="box-center" id="button-wrap">
                <input type="submit" value="ingresar" name="deposit" />
                <input type="submit" value="gastar" name="spend" />
            </div>    
        </form>

        <% final String error = (String) request.getAttribute("error");%>
        <c:if test="${error != null}">  
            <p class="text-error"> <c:out value="${error}"/></p>
        </c:if> 

    </body>
</html>
