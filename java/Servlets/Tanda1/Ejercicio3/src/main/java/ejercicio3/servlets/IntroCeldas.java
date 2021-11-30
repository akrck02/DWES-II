/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ejercicio3.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aketz
 */
public class IntroCeldas extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            try {
                
                String rowsString = request.getParameter("rows") != null ? request.getParameter("rows") : request.getAttribute("rows") + "";
                String columnsString = request.getParameter("columns") != null ? request.getParameter("columns") : request.getAttribute("columns") + "";
                
                int rows = Integer.parseInt(rowsString);
                int columns = Integer.parseInt(columnsString);
                boolean background = Boolean.parseBoolean(request.getParameter("background"));


                String error = (String) request.getAttribute("error");
                drawMatrix(out, rows, columns, background, error);
            } catch (NumberFormatException e) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
                dispatcher.forward(request, response);
            }

        }
    }

    private void drawMatrix(PrintWriter out, int rows, int columns, boolean background,String error) {
        /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Matrices.com - Rellenar</title>");
        out.println("<link rel='stylesheet' href='http://localhost:8080/Ejercicio3/styles/master.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<form action='http://localhost:8080/Ejercicio3/guardar' method='post'>");
        out.println("<h1>Introduce valores: </h1>");
        
        out.print("<table id='matrix' style='background:" + (background ? "#f1f1f1" : "none") + "'>");
        for (int i = 1; i <= rows; i++) {
            out.print("<tr>"); 
            for (int j = 1; j <= columns; j++) {
                out.print("<td>");
                out.print("<input type='text' name='celda" + i + "-" + j + "'>");
                out.print("</td>");
            }
            out.print("</tr>");
        }
        out.print("</table>");
        
        if(error != null)
            out.print("<p class='error'>" + error +"</p>");
        
        out.print("<input type='hidden' name='rows' value='" + rows + "'>");
        out.print("<input type='hidden' name='columns' value='" + columns + "'>");
        out.print("<input type='hidden' name='background' value='" + background + "'>");
        out.print("<input type='submit' name='save' value='Guardar matriz'>");
        out.print("<input type='reset' value='Restablecer'>");
        out.println("</form>");
        out.println("</body>");

        out.println("</html>");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
