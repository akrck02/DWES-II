<%-- 
    Document   : compra
    Created on : 15-ene-2022, 19:37:55
    Author     : aketz
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Compras.com - Productos</title>
        <link rel="stylesheet" href="./styles/bubbleUI-min.css"/>
    </head>
    <body>
        <%
            ArrayList<String> products = (ArrayList<String>) session.getAttribute("products");
            HashMap<String, Integer> purchased = (HashMap<String, Integer>) session.getAttribute("purchased");

            if (purchased == null) {
                purchased = new HashMap();
            }

            if (request.getParameter("buy") != null) {
                String product = request.getParameter("buy");
                if (!purchased.containsKey(product)) {
                    purchased.put(product, 1);
                } else {
                    purchased.put(product, purchased.get(product)  + 1);
                }
            }

            session.setAttribute("purchased", purchased);
        %>
        <table>
            <tr>
                <th>Poducto</th>
                <th>Pedir</th>
            </tr>
            <%
                for (String product : products) {
                    out.print("<tr>");
                    out.print("<td>" + product + "</td>");
                    out.print("<td>");
                    out.print("<form action='" + getServletContext().getContextPath() + "/compra.jsp' method='get'>");
                    out.print("<input type='submit' value='Adquirir unidad'>");
                    out.print("<input type='hidden' name='buy' value='" + product + "'>");
                    out.print("</form>");
                    out.print("</td>");
                    
                    if(purchased.containsKey(product))
                        out.print("<td>" +purchased.get(product) + " unidades</td>");
                    
                    out.print("</tr>");
                }
            %>
            
            
        </table>
            
        <jsp:include page="/muestracarro.jsp"></jsp:include>

    </body>
</html>
