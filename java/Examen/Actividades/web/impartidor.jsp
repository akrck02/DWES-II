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
    if (session.getAttribute("Impartidor") == null) {
        response.sendRedirect(getServletContext().getContextPath());
        return;
    }

    Impartidor impartidor = (Impartidor) session.getAttribute("Impartidor");
    Dao db = (Dao) getServletContext().getAttribute("db");

    ArrayList<Actividad> actividadesImpartidor = db.actividadesImpartidor(impartidor);

    HashMap<Alumno, Date> mapaAsistencia = (HashMap<Alumno, Date>) request.getAttribute("mapaAsistencia");
    Integer actividadSeleccionada = (Integer) request.getAttribute("actividad");

    if (mapaAsistencia == null) {
        mapaAsistencia = new HashMap();
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

        <% if (actividadSeleccionada != null) { %>
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
                    <td><input type="text" value="<%= alumno.getTelefono() %>"></td>
                    <td><input type="text" value="<%= alumno.getEmail() %>"></td>
                    <td>
                        <%= formatter.format(ultima)%>
                    </td>
                    
                    <% if(diferencia / 1000 / 60 / 60 / 24 > 100) { %>
                    <td>
                        <form action="<%= getServletContext().getContextPath()%> "/ServletAvisos" method="POST">
                            <input type="hidden" value="${alumno.getDni()}" name="dni">
                            <label>Email <input type="radio" name="medio" value="Email" /></label>
                            <label>Telefono <input type="radio" name="medio" value="Telefono" /></label>
                            <input type="submit" name="" value="Avisar"> 
                        </form>
                        
                    </td>
                    <% } %>                    
                </tr>
                        
                <%        
                    }
                %>
            </table>
        <% }%>
    </body>
</html>
