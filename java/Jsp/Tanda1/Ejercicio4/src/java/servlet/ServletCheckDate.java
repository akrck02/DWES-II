/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Date;
import bean.Lottery;
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
public class ServletCheckDate extends HttpServlet {

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
        
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/");
        final HttpSession session = request.getSession(true);
       
        if(request.getParameter("new-lottery") == null){
            request.setAttribute("error", "Fecha incorrecta");
            dispatcher.forward(request, response);
            return;
        }
        
        final String dayParam   = request.getParameter("day");
        final String monthParam = request.getParameter("month");
        final String yearParam  = request.getParameter("year");
        
        if("".equals(dayParam) || "".equals(monthParam) || "".equals(yearParam)){
            request.setAttribute("error", "Rellene todos los campos antes de continuar.");
            dispatcher.forward(request, response);
            return;
        }   
        
        try {
            final Integer day   = Integer.parseInt(dayParam);
            final Integer month = Integer.parseInt(monthParam);
            final Integer year  = Integer.parseInt(yearParam);    
            
            final Date date = new Date(day, month, year);
            
            if(!date.correct()) {
                request.setAttribute("error", "Fecha incorrecta");
                dispatcher.forward(request, response);
                return;
            }
            
            Lottery lottery = new Lottery(date);
            session.setAttribute("lottery", lottery);
            dispatcher.forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Fecha incorrecta");
            dispatcher.forward(request, response);
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
