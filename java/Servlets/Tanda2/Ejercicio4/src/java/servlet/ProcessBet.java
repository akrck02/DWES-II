/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import toolkit.Url;

/**
 *
 * @author aketz
 */
public class ProcessBet extends HttpServlet {

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

        if (baseUrl == null) {
            baseUrl = Url.baseUrl(request);
        }

        HttpSession session = request.getSession();
        if (request.getParameter("process") == null || session == null) {
            response.sendRedirect(baseUrl);
            return;
        }

        int total = Integer.parseInt(request.getParameter("total"));
        String[] selected = new String[total];
        
        selected = new String[total];
        boolean emptyFields = false;

        for (int i = 0; i < selected.length; i++) {
            String option = request.getParameter("option-" + i);

            if ("".equals(option)) {
                emptyFields = true;
            }

            selected[i] = option;
        }

        session.setAttribute("selected", selected);
        if (emptyFields) {
            request.setAttribute("write", true);
            request.setAttribute("FillError", true);
            
            System.out.println("Empty fields!");
            request.getRequestDispatcher("/WriteBet").forward(request, response);
            return;
        } 
        
        LinkedList<String[]> teams = (LinkedList<String[]>) session.getAttribute("teams");
        if(teams == null) {
            response.sendRedirect(baseUrl);
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(this.getView(teams, selected));
        }

    }

    /**
     * Get the view HTML
     *
     * @return The HTML code
     */
    private String getView(final LinkedList<String[]> teams, final String[] selected) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Quiniela.com - Apuesta guardada</title>");
        html.append("<link rel='stylesheet' href='").append(baseUrl).append("styles/main.css").append("'>");
        html.append("</head>");
        html.append("<body class='box-column box-center'>");
        html.append("<div>");
        html.append("<h1 class='text-center'>Apuesta guardada:</h1><br>");
        html.append("<table>");
        for (int i = 0; i < teams.size(); i++) {
            String[] couple = teams.get(i);
            html.append("<tr>");
            html.append("<td class='bold'>");
            html.append(couple[0]).append(" : ").append(couple[1]);
            html.append("</td>");
            html.append("<td>").append(selected[i]).append("</td>");
            
            html.append("</tr>");
        }
        html.append("<table>");
        html.append("</div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
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
