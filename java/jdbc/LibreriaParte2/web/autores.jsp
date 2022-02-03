<%-- 
    Document   : autores
    Created on : 03-feb-2022, 12:00:35
    Author     : aketz
--%>

<%@page import="java.util.LinkedList"%>
<%@page import="bean.Autor"%>
<%@page import="bean.Libro"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  

<%
    LinkedList<Autor> autores = (LinkedList<Autor>) request.getAttribute("autores");

    if (autores == null) {
        response.sendRedirect(getServletContext().getContextPath() + "/control");
    }

    pageContext.setAttribute("autores", autores);

    LinkedList<Libro> librosAutor = (LinkedList<Libro>) request.getAttribute("librosAutor");
    String autor = (String) request.getAttribute("autor");
    if (librosAutor == null) {
        librosAutor = new LinkedList();
    }

    pageContext.setAttribute("librosAutor", librosAutor);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alejandría.biblio - Autores</title>
        <link rel="stylesheet" href="./styles/bubbleUI-min.css"/>
    </head>
    <body>
        <h1>Lista de autores: </h1>
        <c:if test="${autores.size() > 0}">
            <table>
                <tr>
                    <th>Nombre</th>
                    <th>Fecha de nacimiento</th>
                    <th>Nacionalidad</th>
                    <th>Ver libros</th>
                </tr>

                <c:forEach items="${autores}" var="autor">
                    <tr>
                        <td>${autor.getNombre()}</td>
                        <td><fmt:formatDate value="${autor.getFechanac()}" pattern='yyyy/MM/dd'/></td>
                        <td>${autor.getNacionalidad()}</td>
                        <td><a href='<%=getServletContext().getContextPath()%>/control?autor=${autor.getId()}'>Ver</a></td>
                    </tr>
                </c:forEach>

            </table>
        </c:if>

        <c:if test="${librosAutor.size() > 0}">
            <h1>Libros de ${autor}</h1>
            <ul>
                <c:forEach items="${librosAutor}" var="libro">
                    <li><a href="<%=getServletContext().getContextPath()%>/control?prestar=${libro.getId()}">${libro.getTitulo()}</a></li>
                </c:forEach>
            </ul>
        </c:if>
    
        <c:if test="${error != null}">
            <p class="text-error">${error}</p>
        </c:if>
        
        <c:if test="${message != null}">
            <p class="text-info">${message}</p>
        </c:if>
            
        <br>
        <h1>Añadir autor</h1>
        <form action="<%=getServletContext().getContextPath()%>/control" method="POST">
            <table>
                <tr>
                    <td><label for='nombre'>Nombre:</label></td>
                    <td><input type='text' name='nombre' id='nombre'></td>
                </tr>
                <tr>
                    <td><label for='fechanac'>Fecha de nacimiento:</label></td>
                    <td><input type='text' name='fechanac' id='fechanac'></td>
                </tr>
                <tr>
                    <td><label for='nacionalidad'>Nacionalidad:</label></td>
                    <td><input type='text' name='nacionalidad' id='nacionalidad'></td>
                </tr>
                <tr>
                    <td><input type='submit' name='nuevoAutor' value='Añadir'></td>
                </tr>
            </table>
        </form>

    </body>
</html>
