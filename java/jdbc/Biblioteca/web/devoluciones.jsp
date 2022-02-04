<%-- 
    Document   : index
    Created on : 27-ene-2022, 13:25:45
    Author     : aketz
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashSet"%>
<%@page import="bean.Book"%>
<%@page import="java.util.List"%>
<%@page import="dao.DbGestor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
    
    
    final List<Book> books = (ArrayList<Book>) session.getAttribute("books");
    
    if(books == null) {
        response.sendRedirect(getServletContext().getContextPath() + "/return");
    }
    
    HashSet<Integer> selected = (HashSet<Integer>) session.getAttribute("return");
    if (selected == null) {
        selected = new HashSet<Integer>();
    }

    pageContext.setAttribute("books", books);
    pageContext.setAttribute("selected", selected);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alejandria.biblio - Devoluciones</title>
        <link rel="stylesheet" href="./styles/bubbleUI-min.css"/>
    </head>
    <body>
        <h1>Libros sin devolver: </h1>
        <table>
            <c:forEach items="${books}" var="book" varStatus="status" begin="${0}" >
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${book.getTitle()}</td> 
                    
                    <c:choose>
                        <c:when test="${selected.contains(book.getId())}">
                            <td><a class="text-error" href='<%=getServletContext().getContextPath()%>/return?id=${book.getId()}&revert'>Revertir devolución</a></td>
                        </c:when>
                        <c:otherwise>
                            <td><a href='<%=getServletContext().getContextPath()%>/return?id=${book.getId()}'>Marcar devolución</a></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>              
   
        </table>
        <a href='<%=getServletContext().getContextPath()%>/return?save'>Grabar devoluciones</a>
    </body>
</html>
