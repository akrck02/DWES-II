<%-- 
    Document   : impartidor
    Created on : 04-feb-2022, 10:13:13
    Author     : aketz
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="bean.Alumno"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Actividad"%>
<%@page import="bean.Impartidor"%>
<%@page import="dao.Dao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
    // Si no se ha hecho login con el impartidor se devuelve al login
    if (session.getAttribute("Impartidor") == null) {
        response.sendRedirect(getServletContext().getContextPath());
        return;
    }

    // Recoge los datos necesarios para cargar la página
    final Impartidor impartidor = (Impartidor) session.getAttribute("Impartidor");
    final ArrayList<Actividad> actividadesImpartidor = (ArrayList<Actividad>) request.getAttribute("actividadesImpartidor");
    final HashMap<Alumno, Date> mapaAsistencia = (HashMap<Alumno, Date>) request.getAttribute("mapaAsistencia");
    final Integer actividadSeleccionada = (Integer) request.getAttribute("actividad");

    // Si no existe la información necesaria redirige al servlet avisos
    if(actividadesImpartidor == null || mapaAsistencia == null) {
        response.sendRedirect(getServletContext().getContextPath() + "/ServletAvisos");
        return;
    }

%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2><%= impartidor.getApellido()%> <%= impartidor.getNombre()%></h2>
        <hr>

        <table>
            <%
                for (Actividad actividad : actividadesImpartidor) {
            %>

            <tr>
                <td><%= actividad.getNombre()%></td>
                <td><a href='<%= getServletContext().getContextPath() + "/ServletAvisos?actividad=" + actividad.getId()%>'>Asistencia</a></td>
            </tr>

            <%
                }
            %>
        </table>

        <br>
        
        <% if(request.getAttribute("avisoRealizado") != null) { %>
            <p style="color:forestgreen">Aviso realizado exitosamente.</p><br>
        <% } %>

        <% if (actividadSeleccionada != null) { %>
        <form action="ServletAvisos" method="POST">
            <table>
                <tr>
                    <th>Nombre</th>
                    <th>Telefono</th>
                    <th>Email</th>
                    <th>Última asistencia</th>
                    <th>TIPO DE AVISO</th>
                </tr>
                
                <% 
                    for (Alumno alumno : mapaAsistencia.keySet()) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date ultima = mapaAsistencia.get(alumno);
                        long diferencia =  new Date().getTime() - ultima.getTime();
                    
                %>    
                
                <tr>
                    <td><%= alumno.getNombre() %> <%= alumno.getApellidos()%></td>
                    
                    <% if(diferencia / 1000 / 60 / 60 / 24 > 100) { %>
                    
                        <td><input type="text" name="Telefono-<%=alumno.getDni()%>" value="<%= alumno.getTelefono() %>"></td>
                        <td><input type="text" name="Email-<%=alumno.getDni()%>" value="<%= alumno.getEmail() %>"></td>
                        <td><%= formatter.format(ultima)%></td>
                        <td>
                            <label>Email <input type="radio" name="medio-<%=alumno.getDni()%>" value="Email" /></label>
                            <label>Telefono <input type="radio" name="medio-<%=alumno.getDni()%>" value="Telefono" /></label>
                            <button type="submit" value="<%=alumno.getDni()%>" name="avisar" >Avisar</button>
                        </td>
                        
                    <% } else {%>
                    
                       <td><%= alumno.getTelefono() %></td>
                       <td><%= alumno.getEmail() %></td>
                       <td><%= formatter.format(ultima)%></td>
                       
                    <% } %>
                </tr>
                        
                <%        
                    }
                %>
            </table>
        </form>
        <% }%>
    </body>
</html>
