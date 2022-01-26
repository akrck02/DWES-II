/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Bet;
import bean.Lottery;
import exception.BadBetException;
import java.io.IOException;
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
public class ServletCheckBet extends HttpServlet {

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
        
        
        
        if(request.getParameter("check-bets") == null){
            sendError(request, response, "");
            return;
        }
        
        final String name = request.getParameter("name");
        if(name == null || "".equals(name)) {
            sendError(request, response, "Rellene nombre.");
            return;
        }
        
        final String adminParam = request.getParameter("administration");
        if(adminParam == null || "".equals(adminParam)){
            sendError(request, response, "Rellene administración.");
            return;
        }
        
        final String[] numbersParam = request.getParameterValues("numbers");
        if(numbersParam == null){
            sendError(request, response, "Rellene todos los números.");
            return;
        }        
        
        final String drawbackParam = request.getParameter("drawback");
        if(drawbackParam == null || "".equals(drawbackParam)) {
            sendError(request, response, "Rellene reintegro.");
            return;
        }

        int administration = 0;        
        try {
            administration = Integer.parseInt(adminParam);
        } catch (NumberFormatException e) {
            sendError(request, response, "Administración inválida.");
            return;
        }
        
        ArrayList<Integer> numberParsed = new ArrayList<>();
        try {
            for (String number : numbersParam) {
                numberParsed.add(Integer.parseInt(number));
            }
        } catch (NumberFormatException e) {
            sendError(request, response, "Números inválidos.");
            return;
        }
        
        int drawback = 0;
        try {
            drawback = Integer.parseInt(drawbackParam);
        } catch (NumberFormatException e) {
            sendError(request, response, "Reintegro inválido.");
            return;
        }

        try {        
            final HttpSession session = request.getSession(true);
            final Lottery lottery = (Lottery) session.getAttribute("lottery");
            final Bet bet = new Bet(name, administration, numberParsed.toArray(new Integer[6]) , drawback);
              
            final RequestDispatcher dispatcher = request.getRequestDispatcher("/check_apuestas.jsp");
            request.setAttribute("result", lottery.result(bet));
            dispatcher.forward(request, response);        
        } catch(BadBetException e) { 
            sendError(request, response, "Apuesta inválida. " + e.getMessage()); 
        }
      
    }
    
    /**
     * Send error to the main page
     * @param request
     * @param response
     * @param message
     * @throws ServletException
     * @throws IOException 
     */
    private void sendError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException{
        final RequestDispatcher dispatcher = request.getRequestDispatcher("/check_apuestas.jsp");
        request.setAttribute("error", message);
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
