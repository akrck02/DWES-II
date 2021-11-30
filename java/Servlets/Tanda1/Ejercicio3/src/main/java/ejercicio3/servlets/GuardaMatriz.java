/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ejercicio3.servlets;

import ejercicio3.beans.AlmacenMatrices;
import ejercicio3.beans.UrlToolkit;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Server that saves the matrix send by form
 * on a static list or redirects
 * @author akrck02
 */
public class GuardaMatriz extends HttpServlet {

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

            int rows = 0;
            int columns = 0;

            /*
             * Get rows and columns or redirect to index 
             */
            try {
                rows = Integer.parseInt(request.getParameter("rows"));
                columns = Integer.parseInt(request.getParameter("columns"));
            } catch (NumberFormatException e) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
                dispatcher.forward(request, response);
            }

            /*
             * create a matrix and show menu
             * if fails redirects to IntroCeldas with an error
             */
            try {
                int[][] matrix = new int[rows][columns];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        int num = Integer.parseInt(request.getParameter("celda" + (i + 1) + "-" + (j + 1)));
                        matrix[i][j] = num;
                    }
                }

                AlmacenMatrices.addMatrix(matrix);
                String baseUrl = UrlToolkit.baseUrl(request);
                drawSuccessMessage(baseUrl, out, rows, columns);
            } catch (NumberFormatException e) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/celdas");
                request.setAttribute("error", "Debes rellenar correctamente la matriz");
                request.setAttribute("rows", rows);
                request.setAttribute("columns", columns + "");
                dispatcher.forward(request, response);
            }
        }
    }

    /**
     * Draw the success message on the client 
     * with a HTML view
     * 
     * @param baseUrl   The webapp base URL
     * @param out       HTML printer
     * @param rows      row count of the saved matrix
     * @param columns   column count of the saved matrix
     */
    private void drawSuccessMessage(String baseUrl, PrintWriter out, int rows, int columns) {        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Matrices.com - Matriz guardada</title>");
        out.println("<link rel='stylesheet' href='" + baseUrl + "/styles/master.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Matriz de " + rows + " x " + columns +" guardada correctamente</h1>");
        out.println("<p>Numero de matrices guardadas: " + AlmacenMatrices.getMatrixBundle().size() + "</p>");
        out.println("<a href='" + baseUrl + "'>Añadir nueva matriz</a>");
        out.println("<a href='" + baseUrl + "/visor'>Ver matrices</a>");
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
