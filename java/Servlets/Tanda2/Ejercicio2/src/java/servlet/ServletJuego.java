package servlet;

import bean.AlmacenPalabras;
import bean.Game;
import bean.UrlToolkit;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import styles.Style;

/**
 *
 * @author aketz
 */
public class ServletJuego extends HttpServlet {

    private String baseUrl;
    private final static String STYLES = Style.DEFAULT;

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
            this.baseUrl = UrlToolkit.baseUrl(request);
        }

        HttpSession session = request.getSession(true);
        String error = null;
        Game game = (session.getAttribute("game") != null) ? (Game) session.getAttribute("game") : new Game(AlmacenPalabras.randomWord());

        if (game.getLives() <= 0) {
            error = "Has fallado! La palabra era " + game.getWord();
            session.invalidate();
        }

        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");

            if (request.getParameter("win") != null) {
                out.print(getWinPage());
                session.invalidate();
            } else {
                out.print(getPage(game, error));
            }
        }
        session.setAttribute("game", game);
    }

    private String getPage(Game game, String error) {

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Ahorcado.com - juego</title>");
        html.append("</head>");
        html.append("<body data-word='" + game.getWord() + "'>");
        html.append(ServletJuego.STYLES);

        html.append("<table>");
        html.append("<tr>");
        game.getWord().chars().forEach(charcode -> {
            if (error != null || game.canShow(((char) charcode) + "")) {
                html.append("<td class='letter'>").append((char) charcode).append("</td>");
            } else {
                html.append("<td class='letter'>");
                html.append("<a href='").append(baseUrl).append("comprobar?letter=").append((char) charcode).append("'>Ver</a>");
                html.append("</td>");
            }
        });
        html.append("</tr>");
        html.append("</table>");

        if (error != null) {
            html.append("<p class='text-error'>").append(error).append("</p>");
            html.append("<a href='").append(baseUrl).append("'>Reintentar</p>");
        } else {
            html.append("<form action='").append(baseUrl).append("/comprobar' method='post'>");
            html.append("<table style='margin-top: 2rem;'>");
            html.append("<tr><td>").append(game.getLives()).append(" vidas </td></tr>");
            html.append("<tr>");
            html.append("<td>Tu respuesta: </td>");
            html.append("<td><input name='word' type='text'></td>");
            html.append("<td><input name='check' value='Comprobar' type='submit'></td>");
            html.append("</tr>");
            html.append("</table>");
            html.append("</form>");
        }

        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    private String getWinPage() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Ahorcado.com - juego</title>");
        html.append("</head>");
        html.append("<body>");
        html.append(ServletJuego.STYLES);
        html.append("<h1>ENHORABUENA, HAS GANADO!</h1>");
        html.append("<p>Esperamos que hayas disfrutado del juego</p>");
        html.append("<a href='").append(baseUrl).append("'>Jugar de nuevo</a>");
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
