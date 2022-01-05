/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Test;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import toolkit.Url;

/**
 *
 * @author aketz
 */
@WebServlet(name = "Result", urlPatterns = {"/Resultado"})
public class Result extends HttpServlet {

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
        if (session == null) {
            response.sendRedirect(baseUrl);
            return;
        }

        final Test test = (Test) session.getAttribute("test");
        final String name = (String) session.getAttribute("name");
        final LocalDateTime start = (LocalDateTime) session.getAttribute("start");
        final LocalDateTime end = LocalDateTime.now();

        if (start == null || name == null || test == null) {
            response.sendRedirect(baseUrl);
            return;
        }

        final int success = test.check();
        final int total = test.getTotalQuestions();

        final int startmilis = start.getMinute() * 3600 + start.getSecond() * 60;
        final int endmilis = end.getMinute() * 3600 + end.getSecond() * 60;

        final int minutes = (endmilis - startmilis) / 3600;
        final int seconds = (endmilis - startmilis) % 3600 / 60;

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(getPage(name, minutes, seconds, success, total));
        }
    }

    private String getPage(String name, int minutes, int seconds, int success, int total) {

        final StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Test.com - Results</title>");
        html.append("<link rel='stylesheet' href='").append(baseUrl).append("/styles/main.css").append("'>");
        html.append("</head>");
        html.append("<body class='box-center box-column'>");
        html.append("<p>");
        html.append("<b class='bold'>").append(name).append("</b>");
        html.append(", has acertado ").append(success).append(" Preguntas de un total de ").append(total);
        html.append("</p>");
        
        html.append("<br><p>");
        if(total - success < total * 0.7){
            html.append("Tienes conocimientos amplios sobre la vida.");
        } else if( total - success < total * 0.5){
            html.append("Tienes conocimientos medios sobre el mundo");
        } else {
            html.append("No sabes nada jon nieve.");
        }
        html.append("</p><br>");        
        
        html.append("<p>");
        html.append("Tiempo de respuesta: ");
        html.append(minutes).append(" ").append(minutes == 1 ? "Minuto" : " Minutos").append(" ");
        html.append(seconds).append(" ").append(seconds == 1 ? "Segundo" : "Segundos. ");
        html.append("</p>");
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
