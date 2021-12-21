/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.AlmacenPalabras;
import bean.Game;
import bean.UrlToolkit;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aketz
 */
public class ServletComprobar extends HttpServlet {

    private String baseUrl;
    
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
        
        if(this.baseUrl == null)
            this.baseUrl = UrlToolkit.baseUrl(request);

        HttpSession session = request.getSession(true);
        Game game = (session.getAttribute("game") != null) ? (Game) session.getAttribute("game") : new Game(AlmacenPalabras.randomWord());
        
        if(request.getParameter("check") != null && request.getParameter("word") != null){
            String word = request.getParameter("word");
            if(word.equalsIgnoreCase(game.getWord()))
            {
                response.sendRedirect(baseUrl + "?win");
                return;
            } else {
                game.fail();
            }
        }
        
        if (request.getParameter("letter") != null) {
            game.show(request.getParameter("letter"));
            game.fail();
        }

        response.sendRedirect(baseUrl);

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
