<%-- 
    Document   : movements
    Created on : 16-ene-2022, 18:43:03
    Author     : aketz
--%>

<%@page import="bean.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("account") == null) {
        response.sendRedirect(getServletContext().getContextPath());
        return;
    }

    final Account account = (Account) session.getAttribute("account");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>myBank - movements</title>
        <link rel="stylesheet" href="./styles/bubbleUI-min.css"/>
    </head>
    <body>
        <h1>Movimientos</h1><br>

        <form action="<%= getServletContext().getContextPath() %>/Movement" method="post">
            <table>
                <tr>
                    <td>Titular</td>
                    <td><%=account.getOwner()%></td>
                </tr>
                <tr>
                    <td>Saldo actual</td>
                    <td><%=account.getBalance()%></td>
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
        <p class="text-error">        
        <%
            if(request.getAttribute("error") != null)
                out.print(request.getAttribute("error"));
        %>
        </p>
    </body>
</html>
