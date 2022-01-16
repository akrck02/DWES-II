/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Account;
import java.io.IOException;
import java.io.PrintWriter;
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
public class MovementServlet extends HttpServlet {

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
        
        final HttpSession session = request.getSession(true);
        if(session.getAttribute("account") == null){
            response.sendRedirect(getServletContext().getContextPath() + "/movements.jsp");
            return;
        }
        
        final Account account = (Account) session.getAttribute("account");
        String error = null;
        double quantity = 0;
        try {
            String quantityString = request.getParameter("quantity");
            if("".equals(quantityString) || quantityString == null){
                error = "Cantidad erronea";
            } else {
                quantity = Double.parseDouble(quantityString);   
            }
                
        } catch (NumberFormatException e) {
            error = "Cantidad erronea";
        }
        
        if(request.getParameter("spend") != null){
            final boolean success = account.spend(quantity);
            if(!success)
                error = "No se pudo realizar la operación.";
        }
        
        if(request.getParameter("deposit") != null){
            account.deposit(quantity);
        }
        
        session.setAttribute("account", account);
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/movements.jsp");
        request.setAttribute("error", error);
        dispatcher.forward(request, response);
        
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
        response.sendRedirect(getServletContext().getContextPath() + "/movements.jsp");
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
