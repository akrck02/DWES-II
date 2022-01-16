<%-- 
    Document   : newaccount
    Created on : 16-ene-2022, 14:38:04
    Author     : aketz
--%>

<%@page import="bean.Account"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>myBank - new account</title>
        <link rel="stylesheet" href="./styles/bubbleUI-min.css"/>
    </head>
    <body>
        <%
            Account account = (Account) request.getAttribute("account");
            if(account == null)
                account = new Account(null,null);
        %>
        <h1>Nueva cuenta!</h1>
        <form action="<%= getServletContext().getContextPath() %>/NewAccount" method="post">
            <table>
                <tr>
                    <td><label for="owner">Titular: </label></td>
                    <td><input id="owner" type='text' name='owner' value='<%= account.getOwner() != null? account.getOwner() : ""%>'></td>
                </tr>
                <tr>
                    <td><label for="balance">Saldo inicial: </label></td>
                    <td><input id="balance" type='number' name='balance' value='<%= account.getBalance()!= null? account.getBalance() : ""%>'></td>
                </tr>
            </table>
            <input type="submit" value="Crear cuenta corriente" name='' />
        </form><br>
            
        <%
            final ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");
            
            if(errors != null)
            for (String error : errors) {
                    out.print("<p class='text-error'>");
                    out.print(error);
                    out.print("</p>");
            }
        
        %>
    </body>
</html>
