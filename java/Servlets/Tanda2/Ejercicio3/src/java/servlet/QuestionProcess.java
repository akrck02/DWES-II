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

        // If test has not been created, check request params
        // for the first time.
        Test test = (Test) session.getAttribute("test");
        if (test == null) {
            String name;
            boolean hints;
            int quantity = 0;
            
            //check initial parameters 
            name = request.getParameter("name");
            if("".equals(name)){
                response.sendRedirect(baseUrl + "?InvalidName");
                return;
            }
             
            if(request.getParameter("question-number") != null){
                try {
                    quantity = Integer.parseInt(request.getParameter("question-number"));
                    
                    if(quantity < 1) {
                        response.sendRedirect(baseUrl + "?InvalidNumber");
                        return;
                    }
                    
                } catch (NumberFormatException e) {
                    response.sendRedirect(baseUrl + "?InvalidNumber");
                    return;
                }
            }
            
            System.out.println(request.getParameter("hints"));
            hints = "on".equalsIgnoreCase(request.getParameter("hints"));
            
            //Create new test
            test = new Test(quantity, hints);

            session.setAttribute("start", LocalDateTime.now());
            session.setAttribute("name", name);
            session.setAttribute("test", test);
        }

        String error = null;
        
        //If the next button is pressed change index
        if (request.getParameter("next") != null) {
            
            if(request.getParameter("answer") != null){
                int answer = Integer.parseInt(request.getParameter("answer"));
                test.setCurrentAnswer(answer);
                test.next();                        
            } else {
                error = "Seleccione una respuesta";
            }
            
        }

        //Draw current question or send user to result page
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            Question question = test.getCurrentQuestion();
            if (question == null) {
                response.sendRedirect(baseUrl + "Resultado");
                return;
            }

            out.print(getPage(question, test.isFinal(), test.hasHints(), error));
        }
    }

    private String getPage(Question question, boolean end, boolean hints, String error) {

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Test.com - ").append(question.getStatement()).append("</title>");
        html.append("<link rel='stylesheet' href='").append(baseUrl).append("styles/main.css").append("'>");
        html.append("</head>");
        html.append("<body>");
        html.append("<h1 class='title'>").append(question.getStatement()).append("</h1>");
        html.append("<form action='").append(baseUrl).append("ProcesoPregunta").append("' method='post'>");

        String[] answers = question.getAnswers();

        for (int i = 0; i < answers.length ; i++) {
            html.append("<label class='answer'>");
            html.append("<input type='radio' name='answer' value='").append(i).append("'>");
            html.append("<span>").append(answers[i]).append("<span>");
            html.append("</label>");
        }

        html.append("<input type='submit' name='next' value='").append(end ? "FIN" : "SIGUIENTE").append("'>");
        html.append("</form>");

        if (hints) {
            html.append("<p class='text-info'>* PISTA: ").append(question.getHint()).append("</p>");
        }
        
        if(error != null){
             html.append("<br><p class='text-error'>").append(error).append("</p>");
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
