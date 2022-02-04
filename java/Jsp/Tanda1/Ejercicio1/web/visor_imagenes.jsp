<%-- 
    Document   : visor_imagenes
    Created on : 15-ene-2022, 13:37:53
    Author     : aketz
--%>

<%@page import="bean.Image"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    final String IMAGES = "images";

    /**
     * Get images for the given directory
     *
     * @param directory The given directory
     * @return A list of images
     */
    public ArrayList<Image> getImages(String path, String realPath) {

        final File directory = new File(realPath);
        ArrayList<Image> images = new ArrayList();

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File image : files) {
                images.add(new Image(path + "/" + image.getName(), image.length()));
            }

        } else {
            directory.mkdirs();
        }

        return images;
    }
%>    


<%

    final ArrayList<Image> IMAGE_LIST = this.getImages(this.IMAGES, getServletContext().getRealPath(this.IMAGES));
    final String selected = request.getParameter("image");
    String error = null;
    Image image = null;

    if (request.getParameter("watch") != null) {
        if (selected == null) {
            error = "Seleccione una imagen";
        } else {
            if (request.getParameter("size") == null) {
                error = "Seleccione un tamaño";
            } else {
                image = IMAGE_LIST.get(Integer.parseInt(selected));
            }
        }
    }

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>VisorDeImágenes.org - ver</title>
        <link rel="stylesheet" href="./styles/bubbleUI-min.css"/>
    </head>
    <body>
        <form action="http://localhost:8080<%= getServletConfig().getServletContext().getContextPath()%>" method="get">
            <label>
                Imagen: 
                <select name="image">
                    <%
                        for (int i = 0; i < IMAGE_LIST.size(); i++) {

                            if ((i + "").equals(selected)) {
                                out.print("<option selected value='" + i + "'>");
                            } else {
                                out.print("<option value='" + i + "'>");
                            }

                            out.print(IMAGE_LIST.get(i).getName());
                            out.print("</option>");
                        }

                    %>
                </select>
            </label>
            <span>
                Tamaño:
                <label><input type="radio" name="size" value="300">300px</label>
                <label><input type="radio" name="size" value="400">400px</label>
                <label><input type="radio" name="size" value="500">500px</label>
            </span>
            <input type="submit" name="watch" value="Ver imagen">
        </form>
        <br>

        <%           
            if (error != null) {
                out.print("<p class='text-error'>" + error + "</p>");
            } else {
                out.print("<p><span class='bold'>Tamaño: </span>" + image.BrokenDownSize() + "</p>");
                out.print("<img src='" + image.getPath() + "' style='width:" + request.getParameter("size") + "px'>");
            }
        %>

    </body>
</html>
