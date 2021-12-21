/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import beans.UrlToolkit;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aketz
 */
public class BookServlet extends HttpServlet {

    private List<String> books;
    private String baseUrl;

    @Override
    public void init() throws ServletException {
        super.init();
        books = Arrays.asList(new String[]{
            "Guerra y paz",
            "El decamerón",
            "La odisea",
            "La casa de los espíritus",});
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (this.baseUrl == null) {
            this.baseUrl = UrlToolkit.baseUrl(request);
        }

        HttpSession session = request.getSession(true);
        if (request.getParameter("reset") != null) {
            session.invalidate();
            session = request.getSession(true);
        }

        List<String> selected = (List<String>) session.getAttribute("selected");

        if (selected == null) {
            selected = new LinkedList<>();
        }

        String error = null;

        if (request.getParameter("send") != null) {

            String book = request.getParameter("books");
            if (book != null) {
                if (selected.contains(book)) {
                    error = "Ya has elegido " + book;
                } else {
                    selected.add(book);
                }
            }

        }

        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");
            drawForm(out, this.books, selected, error);
        }

        session.setAttribute("selected", selected);
    }

    private void drawForm(final PrintWriter out, final List<String> books, final List<String> selected, String error) {

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Libros.com - reservas</title>");
        out.println("<link rel='stylesheet' href='" + baseUrl + "styles/main.css'>");
        out.println("<link rel='stylesheet' href='" + baseUrl + "styles/Ejercicio1.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println(
            "<style>" +
                "body {padding: 10px}" +
                " * {font-family: 'Roboto', sans-serif;}" +
            "</style>"
        );
        out.println("<form action='" + this.baseUrl + "/books' method='post'>");

        if (error != null) {
            out.print("<p class='text-error'> " + error + "</p>");
        }

        out.println("<select name='books'>");

        books.forEach(book -> {
            out.print("<option value='" + book + "'>" + book + "</option>");
        });

        out.println("</select>");
        out.println("<input type='submit' name='send' value='Agregar'>");
        out.println("</form>");

        if (selected.size() > 0) {
            out.println("<h2>Tu elección</h2>");
            out.print("<ul>");
            selected.forEach(book -> {
                out.print("<li>" + book + "</li>");
            });
            out.print("</ul>");

            out.print("<a href='" + baseUrl + "?reset'>Borrar</a>");
        } else {
            out.print("<h2>No hay ninguno seleccionado</h2>");
        }

        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
