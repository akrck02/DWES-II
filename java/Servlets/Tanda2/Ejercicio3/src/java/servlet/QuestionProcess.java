/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Question;
import bean.Test;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Objects;
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
public class QuestionProcess extends HttpServlet {

    String baseUrl;

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

        if (this.baseUrl == null) {
            this.baseUrl = Url.baseUrl(request);
        }

        HttpSession session = request.getSession(true);
        Test test = null;
        Integer index = null;
        Boolean hints = null;
        Integer total = null;

        if (request.getParameter("start") != null) {
            final String name = request.getParameter("name");
            
            if("".equals(name)){
                response.sendRedirect(baseUrl + "?invalidName");
                return;
            }
          
            session.setAttribute("start", LocalDateTime.now());

            try {
                total = Integer.parseInt(request.getParameter("question-number"));
                hints = Boolean.parseBoolean(request.getParameter("hints"));
            } catch (NumberFormatException e) {
                response.sendRedirect(baseUrl + "?invalidNumber");
                return;
            }

            total = Test.getTotal(total);
            test = new Test(total);
            index = 0;

            session.setAttribute("test", test);
            session.setAttribute("hints", hints);
            session.setAttribute("index", index);
            session.setAttribute("name", name);
            session.setAttribute("total", total);
            
        } else if (request.getParameter("next") != null) {

            if (test == null) {
                test = (Test) session.getAttribute("test");
            }

            if (index == null) {
                index = (int) session.getAttribute("index");
            }

            if (hints == null) {
                hints = (boolean) session.getAttribute("hints");
            }
            
            
            if (total == null) {
                total = (int) session.getAttribute("total");
            }

            index++;
            session.setAttribute("index", index);
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if (test != null) {
                Question question = test.getQuestion(index);
                if (question == null) {
                    response.sendRedirect(baseUrl + "Resultado");
                    return;
                }
                
                boolean end = Objects.equals(index, total);
                out.print(getPage(question, end, hints));
                return;
            }

            response.sendRedirect(baseUrl);
        }
    }

    private String getPage(Question question, boolean end, boolean hints) {

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Test.com - ").append(question.getStatement()).append("</title>");
        html.append("<link rel='stylesheet' href='").append(baseUrl).append("styles/main.css").append("'>");
        html.append("</head>");
        html.append("<body>");
        html.append("<h1 class='title'>").append(question.getStatement()).append("</h1>");
        html.append("<form action='' method='post'>");

        String[] answers = question.getAnswers();

        for (String answer : answers) {
            html.append("<label class='answer'>");
            html.append("<input type='radio' name='response'>");
            html.append("<span>").append(answer).append("<span>");
            html.append("</label>");
        }

        html.append("<input type='submit' name='next' value='").append(end ? "FIN" : "SIGUIENTE").append("'>");
        html.append("</form>");

        if (hints) {
            html.append("<p class='text-info'>* PISTA: ").append(question.getHint()).append("</p>");
        }

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
