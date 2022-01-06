/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.stream.Stream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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
public class WriteBet extends HttpServlet {
    private LinkedList teams;
    private String baseUrl;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        String file = ctx.getInitParameter("teamFile");
        this.teams = getTeams(file);
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
        
        if(baseUrl == null)
            baseUrl = Url.baseUrl(request);

        // Checking illegal straight access
        if(request.getParameter("write") == null && request.getAttribute("write") == null){
            response.sendRedirect(baseUrl);
            return;
        }
        
        final HttpSession session = request.getSession(true);
        final boolean fillError = request.getAttribute("FillError") != null;
        String user = request.getParameter("name");
        
        // Check user from request and session
        if(!"".equals(user) && user != null){
            user = request.getParameter("name");
            session.setAttribute("name", user);
        } else user = (String) session.getAttribute("name");
        
        // If not name was provided return to main page
        if(user == null){
            response.sendRedirect(baseUrl + "?InvalidName");
            return;
        }
        
        //Checking if previous selection exists
        String[] selected = (String[]) session.getAttribute("selected");
        if(selected == null)
            selected = new String[teams.size()];
        
        // If teams are not saved on session, we save them
        if(session.getAttribute("teams") == null) {
            session.setAttribute("teams", teams);
        }
        
        // Render the page
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(this.getView(user, teams, selected, fillError));
        }
    }

    /**
     * Get the current view
     *
     * @return The view HTML to show
     */
    private String getView(final String user, final LinkedList<String[]> teams, final String[] selected, final boolean fillError) {

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Quiniela.com - Nueva</title>");
        html.append("<link rel='stylesheet' href='").append(baseUrl).append("styles/main.css").append("'>");
        html.append("</head>");
        html.append("<body>");
        html.append("<h1>").append(user).append(", escribe tu apuesta:</h1><br>");

        html.append("<form action='/Ejercicio4/ProcessBet' method='post'>");
        html.append("<table>");
        
        for (int i = 0; i < teams.size(); i++) {
            String[] couple = teams.get(i);
            html.append("<tr>");
            html.append("<td>").append(couple[0]).append("</td>");
            html.append("<td>").append(couple[1]).append("</td>");
            html.append("<td>");
            html.append("<select name='option-").append(i).append("'>");
                
            html.append("<option></option>");
            html.append("<option ").append("1".equals(selected[i]) ? "selected" : "").append(">1</option>");
            html.append("<option ").append("X".equals(selected[i]) ? "selected" : "").append(">X</option>");
            html.append("<option ").append("2".equals(selected[i]) ? "selected" : "").append(">2</option>");
            
            html.append("</select>");
            html.append("</td>");
            if(fillError && "".equals(selected[i]))
                html.append("<td><p class='text-error'> Rellene esta casilla</p></td>");
            html.append("</tr>");
        }
        
        html.append("</table>");
        html.append("<input type='hidden' name='total' value='").append(teams.size()).append("'>");
        html.append("<input type='submit' name='process' value='Guardar apuesta'>");
        html.append("</form>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();

    }

    /**
     * Get teams for file
     * @return List with couples of teams
     */
    private LinkedList<String[]> getTeams(final String TEAMS_FILE) {

        final String realPath = getServletContext().getRealPath("/files/" + TEAMS_FILE);
        LinkedList<String[]> teams = new LinkedList<>();
        Stream<String> lines = null;

        //Setting UTF-8 for buffered reader
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(realPath), "UTF8"));
            lines = reader.lines();
        } catch (IOException | NullPointerException e) {
            System.err.println(e);
        }

        // Getting each valid team couple and adding it to the list
        if (lines != null) {
            lines.forEach((String line) -> {
                line = line.trim();

                String[] couple = line.split(":");
                if (couple.length >= 2) {
                    teams.add(couple);
                }
            });
        }

        return teams;
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
