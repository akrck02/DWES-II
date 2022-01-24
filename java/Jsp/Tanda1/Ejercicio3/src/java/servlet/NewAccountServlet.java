/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class NewAccountServlet extends HttpServlet {

    private ArrayList<String> BANNED_USERS;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.

        BANNED_USERS = new ArrayList();        
        BANNED_USERS.add("pepe");
        BANNED_USERS.add("juan");
        BANNED_USERS.add("antonio");
        BANNED_USERS.add("rodolfo");
        BANNED_USERS.add("adolfo");
        BANNED_USERS.add("ecumenio");
        
        getServletContext().setAttribute("banned", BANNED_USERS);
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

        final String owner = request.getParameter("owner");
        final String balance = request.getParameter("balance");

        final boolean emptyOwner = "".equals(owner);
        final boolean bannedOwner = BANNED_USERS.contains(owner.toLowerCase());
        final boolean emptyBalance = "".equals(balance);

        final Account account = new Account(owner);
        final ArrayList<String> errors = new ArrayList();

        if (emptyOwner) {
            errors.add("titular vacio");
        } else if (bannedOwner) {
            errors.add("usuario baneado");
        }

        if (emptyBalance) {
            errors.add("balance vacio");
            account.setBalance(null);
        } else {
            try {
                final double balancenumber = Double.parseDouble(balance);
                if (balancenumber < 0) {
                    errors.add("balance negativo");
                    account.setBalance(null);
                } else {
                    account.setBalance(balancenumber);
                }
            } catch (NumberFormatException e) {
                account.setBalance(null);
                errors.add("balance no numérico");
            }
        }

        if (errors.size() > 0) {
            final RequestDispatcher dispatcher = request.getRequestDispatcher("/newaccount.jsp");
            request.setAttribute("errors", errors);
            request.setAttribute("account", account);
            dispatcher.forward(request, response);
        } else {
            final HttpSession session = request.getSession(true);
            session.setAttribute("account", account);
            response.sendRedirect(getServletContext().getContextPath() + "/movements.jsp");
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
       response.sendRedirect(getServletContext().getServletContextName());
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
