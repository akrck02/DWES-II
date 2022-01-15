/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aketz
 */
public class PrepareProductsSevlet extends HttpServlet {

    private static String PRODUCT_FILE = "files/productos.txt";

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

        System.out.println("Request get!");
        System.out.println("Params: " + request.getParameter("category"));

        HttpSession session = request.getSession(true);
        final String category = request.getParameter("category");
        final ArrayList<String> products = this.getProductsByCategory(category);

        session.setAttribute("category", category);
        session.setAttribute("products", products);

        final RequestDispatcher dispacher = request.getRequestDispatcher("/compra.jsp");
        dispacher.forward(request, response);

    }

    /**
     * Get element list of a given category if category is not provided returns
     * a list of all products
     *
     * @param category The category
     * @return products of a category or all products
     */
    public ArrayList<String> getProductsByCategory(String category) {
        final ArrayList<String> products = new ArrayList();
        String realPath = this.getServletContext().getRealPath(PrepareProductsSevlet.PRODUCT_FILE);
        try (BufferedReader reader = new BufferedReader(new FileReader(realPath))) {

            String line = reader.readLine();
            while (line != null) {
                String[] params = line.split(";");
                
                if (params.length >= 2) {
                    if("".equals(category) == false && category != null){
                        if(params[0].equals(category))
                             products.add(params[1]); 
                    } else products.add(params[1]); 
                }
                line = reader.readLine();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return products;
        }
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("Method not allowed");
        }
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
