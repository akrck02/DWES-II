<%-- 
    Document   : alumno.jsp
    Created on : 04-feb-2022, 10:12:53
    Author     : aketza
--%>

<%@page import="java.util.HashSet"%>
<%@page import="java.util.HashMap"%>
<%@page import="bean.Actividad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Alumno"%>
<%@page import="javax.enterprise.context.SessionScoped"%>
<%@page import="dao.Dao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
    // Si no se ha hecho login como alumno, redirige al login
    if (session.getAttribute("Alumno") == null) {
        response.sendRedirect(getServletContext().getContextPath());
        return;
    }

    // Coge información necesaria para cargar la página
    final Alumno alumno = (Alumno) session.getAttribute("Alumno");
    final ArrayList<Actividad> actividadesParticipa = (ArrayList<Actividad>) request.getAttribute("actividadesParticipa");
    final ArrayList<Actividad> actividadesNoParticipa = (ArrayList<Actividad>) request.getAttribute("actividadesNoParticipa");
    final HashSet<Integer> actividadesPendientes = (HashSet<Integer>) session.getAttribute("inscripcionesPendientes");
    
    // Si no existe la información necesaria redirige al servlet inscripcion
    if(actividadesParticipa == null || actividadesNoParticipa == null) {
        response.sendRedirect(getServletContext().getContextPath() + "/ServletInscripcion");
        return;
    }
    
    // Guarda las constantes en la página
    pageContext.setAttribute("alumno", alumno);
    pageContext.setAttribute("actividadesParticipa", actividadesParticipa);
    pageContext.setAttribute("actividadesNoParticipa", actividadesNoParticipa);
    pageContext.setAttribute("actividadesPendientes", actividadesPendientes);

%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actividades.com - <c:out value="${alumno.getNombre()}"/> <c:out value="${alumno.getApellidos()}"/></title>
    </head>
    <body>
        <h1>SOCIO: <c:out value="${alumno.getNombre()}"/> <c:out value="${alumno.getApellidos()}"/> </h1>

        <table border="0">
            <caption>Actividades asignadas</caption>
            <tr>
                <th>Actividad</th>
                <th>Precio</th>
                <th>Impartidor</th>

            </tr>

            <c:forEach items="${actividadesParticipa}" var="actividad">
                <tr>
                    <td>${actividad.getNombre()}</td>
                    <td>${actividad.getCosteEntero()}€</td>
                    <td>${actividad.getImpartidor().getNombre()} ${actividad.getImpartidor().getApellido()}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <br>
    <table border="0">
        <caption>Actividades disponibles</caption>
        <tr>
            <th>Actividad</th>
            <th>Impartidor</th>
            <th>Apuntarse</th>
        </tr>

        <c:forEach items="${actividadesNoParticipa}" var="actividad">
                <c:choose>
                    <c:when test='${!actividadesPendientes.contains(actividad.getId())}'>
                        <tr>
                            <td>${actividad.getNombre()}</td>
                            <td>${actividad.getImpartidor().getNombre()} ${actividad.getImpartidor().getApellido()}</td>
                            <td ><a href='<%= getServletContext().getContextPath()%>/ServletInscripcion?apuntar=${actividad.getId()}'>Apuntarse</a></td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                         <tr style="background: #f1f1f1">
                            <td>${actividad.getNombre()}</td>
                            <td>${actividad.getImpartidor().getNombre()} ${actividad.getImpartidor().getApellido()}</td>
                            <td ><a href='<%= getServletContext().getContextPath()%>/ServletInscripcion?anular=${actividad.getId()}'>Anular</a></td> 
                         </tr>
                    </c:otherwise>
                </c:choose>            
        </c:forEach>
                         
        <c:if test="${actividadesPendientes.size() > 0}">
            <tr><td colspan="3" style="padding:1rem"><a href="<%= getServletContext().getContextPath()%>/ServletInscripcion?grabar">GUARDAR LAS ${actividadesPendientes.size()} NUEVAS INSCRIPCIONES</a></td></tr>
        </c:if>
    </tbody>
</table>




</body>
</html>
