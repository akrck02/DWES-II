<%-- 
    Document   : impartidor
    Created on : 15-feb-2022, 19:08:30
    Author     : aketza
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="bean.Alumno"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="bean.Actividad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Impartidor"%>
<%
    // Si no ha hecho login como impartidor redirige al login
    if (session.getAttribute("impartidor") == null) {
        response.sendRedirect(getServletContext().getContextPath());
        return;
    }

    // Recoge los datos necesarios para mostrar la página
    final Impartidor impartidor = (Impartidor) session.getAttribute("impartidor");
    final ArrayList<Actividad> actividadesImpartidor = (ArrayList<Actividad>) request.getAttribute("actividadesImpartidor");
    final LinkedHashMap<Alumno, Date> mapaAsistenciaActividad = (LinkedHashMap<Alumno, Date>) request.getAttribute("mapaAsistenciaActividad");

    // Si los datos están vacios redirige a ServletAvisos
    if (actividadesImpartidor == null) {
        response.sendRedirect(getServletContext().getContextPath() + "/ServletAvisos");
        return;
    }

    // Añadir datos a la página
    pageContext.setAttribute("impartidor", impartidor);
    pageContext.setAttribute("actividadesImpartidor", actividadesImpartidor);
    pageContext.setAttribute("mapaAsistenciaActividad", mapaAsistenciaActividad);
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actividades.com - <%= impartidor.getApellido()%> <%= impartidor.getNombre()%></title>
    </head>
    <body>
        <h1><%= impartidor.getApellido()%> <%= impartidor.getNombre()%></h1>
        <hr>
        <table>
            <% for (Actividad actividad : actividadesImpartidor) {%>
            <tr>
                <td><%= actividad.getNombre()%></td>
                <td><a href="ServletAvisos?mostrar=<%= actividad.getId()%>"> Asistencia </a></td>
            </tr>        
            <% } %>
        </table>

        <br>
        <% if(request.getAttribute("avisoRealizado") != null){ %>
        <p style="color: forestgreen">Aviso realizado exitosamente.</p><br>
        <% } %>

        <% if (mapaAsistenciaActividad != null) { %>
        <form action="" method="POST">
            <table>
                <tr>
                    <th>Nombre</th>
                    <th>Telefono</th>
                    <th>Email</th>
                    <th>Última asistencia</th>
                    <th>TIPO DE AVISO</th>
                </tr>
                
                <% for (Alumno alumno : mapaAsistenciaActividad.keySet()) {
                    
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    Date ultima = mapaAsistenciaActividad.get(alumno);
                    long diferencia =  new Date().getTime() - ultima.getTime();
                
                %>
                <tr>
                    <% if(diferencia / 1000 / 60 / 60 / 24 > 100) { %>
                    <td><%= alumno.getNombre() %> <%= alumno.getApellidos()%></td>
                    <td><input name="Telefono-<%= alumno.getDni()%>" value="<%= alumno.getTelefono()%>"/></td>
                    <td><input name="Email-<%= alumno.getDni()%>" value="<%= alumno.getEmail()%>"/></td>
                    <td><%= formatter.format(ultima) %></td>
                    <td>
                        <label> Email <input type="radio" name="medio-<%= alumno.getDni()%>" value="Email"></label>
                        <label> Telefono <input type="radio" name="medio-<%= alumno.getDni()%>" value="Telefono"></label>
                        <button type="submit" value="<%= alumno.getDni()%>" name="avisar">avisar</button>
                    </td>
                    <% } else {%>
                    <td><%= alumno.getNombre() %> <%= alumno.getApellidos() %></td>
                    <td><%= alumno.getTelefono()%></td>
                    <td><%= alumno.getEmail()%></td>
                    <td><%= formatter.format(ultima) %></td> 
                    <% } %>
                </tr>
                <% } %>
            </table>
        </form>

        <% }%>

    </body>
</html>
