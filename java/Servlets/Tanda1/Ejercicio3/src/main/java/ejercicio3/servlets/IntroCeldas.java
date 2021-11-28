/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ejercicio3.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
                int rows = Integer.parseInt(request.getParameter("rows"));
                int columns = Integer.parseInt(request.getParameter("columns"));
                drawMatrix(out, rows, columns);
            } catch(NumberFormatException e) { 
                response.sendRedirect("http://localhost:8080/Ejercicio3/");
            }
        }
    }
    
    private void drawMatrix(PrintWriter out, int rows, int columns){
           /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet IntroCeldas</title>");   
           
            out.println("<link rel='stylesheet' href='http://localhost:8080/Ejercicio3/styles/master.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form action='http://localhost:8080/Ejercicio3/celdas' method=''>");
            out.println("<h1>Introduce valores: </h1>");
            
            out.print("<table id='matrix'>");
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
            out.print("<input type='submit' name='' value='Guardar matriz'>");
            out.print("<input type='submit' name='' value='Restablecer'>");
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
