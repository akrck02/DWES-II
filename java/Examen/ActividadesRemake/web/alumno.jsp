<%-- 
    Document   : alumno
    Created on : 15-feb-2022, 19:08:40
    Author     : aketz
--%>
<%@page import="java.util.HashSet"%>
<%@page import="bean.Alumno"%>
<%@page import="bean.Actividad"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%

    // Si no ha hecho login como alumno, redirige al login
    if (session.getAttribute("alumno") == null) {
        response.sendRedirect(getServletContext().getContextPath());
        return;
    }

    // Recoger datos necesarios para la página
    final Alumno alumno = (Alumno) session.getAttribute("alumno");
    final ArrayList<Actividad> actividadesParticipa = (ArrayList<Actividad>) request.getAttribute("actividadesParticipa");
    final ArrayList<Actividad> actividadesNoParticipa = (ArrayList<Actividad>) request.getAttribute("actividadesNoParticipa");
    final HashSet<Integer> actividadesPendientes = (HashSet<Integer>) session.getAttribute("actividadesPendientes");

    // Si los datos no están redirige al servlet inscripcion
    if (actividadesParticipa == null || actividadesNoParticipa == null || actividadesPendientes == null) {
        response.sendRedirect(getServletContext().getContextPath() + "/ServletInscripcion");
        return;
    }

    // Añade a la página los datos necesarios
    pageContext.setAttribute("alumno", alumno);
    pageContext.setAttribute("actividadesParticipa", actividadesParticipa);
    pageContext.setAttribute("actividadesNoParticipa", actividadesNoParticipa);
    pageContext.setAttribute("actividadesPendientes", actividadesPendientes);

%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actividades.com - <c:out value="${alumno.getNombre()}"/> <c:out value="${alumno.getApellidos()}"/></title>
    </head>
    <body>
        <h1>SOCIO: <c:out value="${alumno.getNombre()}"/> <c:out value="${alumno.getApellidos()}"/></h1>

        <table>
            <caption>Actividades asignadas</caption>
            <tr>
                <th>Actividad</th>
                <th>Precio</th>
                <th>Impartidor</th>
            </tr>
            <c:forEach items="${actividadesParticipa}" var="actividad">
                <tr>
                    <td>${actividad.getNombre()}</td>
                    <td>${actividad.getCosteMensualEntero()}€</td>
                    <td>${actividad.getImpartidor().getNombre()} ${actividad.getImpartidor().getApellido()}</td>
                </tr>
            </c:forEach>
        </table>

        <br>

        <table>
            <caption>Actividades disponibles</caption>
            <tr>
                <th>Actividad</th>
                <th>Impartidor</th>
                <th>Apuntarse</th>
            </tr>
            <c:forEach items="${actividadesNoParticipa}" var="actividad">

                <c:choose>
                    <c:when test="${actividadesPendientes.contains(actividad.getId())}"> 
                        <tr style="background: #f1f1f1">
                            <td>${actividad.getNombre()}</td>
                            <td>${actividad.getImpartidor().getNombre()} ${actividad.getImpartidor().getApellido()}</td>
                            <td>
                                <a href="ServletInscripcion?anular=<c:out value="${actividad.getId()}"/>"> anular </a>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>${actividad.getNombre()}</td>
                            <td>${actividad.getImpartidor().getNombre()} ${actividad.getImpartidor().getApellido()}</td>
                            <td>
                                <a href="ServletInscripcion?apuntar=<c:out value="${actividad.getId()}"/>"> apuntarse </a>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
                        
            <c:if test="${actividadesPendientes.size() > 0}">
                <tr>
                    <td colspan="3" style="padding:1rem">
                        <a href="ServletInscripcion?grabar">GUARDAR LAS ${actividadesPendientes.size()} NUEVAS INSCRIPCIONES</a>
                    </td>
                </tr>
            </c:if>
        </table>

    </body>
</html>
