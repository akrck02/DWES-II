<%-- 
    Document   : crear_primitiva
    Created on : 24-ene-2022, 18:07:14
    Author     : aketz
--%>

<%@page import="bean.Lottery"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<% 
    final String error = (String) request.getAttribute("error");
    final Lottery lottery = (Lottery) session.getAttribute("lottery");       
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Primitivas.com - Nueva</title>
        <link rel="stylesheet" href="./styles/bubbleUI-min.css"/>
    </head>
    <body>
        <h1>Primitiva</h1>
        <form action="<%= getServletContext().getContextPath() + "/date"%>" method="post">
            <table>
                <tr>
                    <td><label for="day">Dia</label></td>
                    <td><input type="text" name="day" id="day"></td>

                    <td><label for="month">Mes</label></td>
                    <td><input type="text" name="month" id="month"></td>

                    <td><label for="year">Año</label></td>
                    <td><input type="text" name="year" id="year"></td>
                </tr>
                <c:if test="${lottery != null}">  
                    <tr> 
                        <td colspan="6">
                            <b class="bold">Números: </b>
                            <c:forEach items="${lottery.getNumbers()}" var="number" >
                                ${number} &nbsp;
                            </c:forEach>
                            
                            <b class="bold">Reintegro: </b>
                            <c:out value="${lottery.getDrawback()}"/>
                        </td>
                        
                    </tr>
                    <tr>
                        <td colspan="6">
                            <a href="<%=getServletContext().getContextPath()%>/check_apuestas.jsp">Comprobar apuestas</a>
                        </td> 
                    </tr>
                </c:if> 
            </table>            

            <input type="submit" name="new-lottery" value="Crear primitiva">
        </form>
        
        <c:if test="${error != null}">  
            <p class="text-error"> <c:out value="${error}"/></p>
        </c:if> 

    </body>
</html>
