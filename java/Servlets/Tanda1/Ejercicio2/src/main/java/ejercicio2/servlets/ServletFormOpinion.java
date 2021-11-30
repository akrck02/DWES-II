/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ejercicio2.servlets;

import ejercicio2.beans.UrlToolkit;
import ejercicio2.io.IoManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aketz
 */
public class ServletFormOpinion extends HttpServlet {

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
        
        final ServletContext context = getServletContext();

        response.setContentType("text/html;charset=UTF-8");        
        try (PrintWriter out = response.getWriter()) {
            if(request.getParameter("send") != null){
                
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String rating = request.getParameter("rating");
                String[] sections = request.getParameterValues("sections"); 
                String error = null;
                
                if(name == null || name.trim().length() == 0)
                    error = "Introduzca un nombre";
                else  if(rating == null) 
                    error = "Selecciona una valoración";
                else if(rating.equals("B") && sections != null){
                    String line = name + " ";
                    if(surname != null && surname.length() > 0)
                        line += surname + " ";
                    
                    line += ": " + String.join(",", sections);
                    IoManager.writeLine(context, line);
                }
                drawMainPage(request, response, out, error);
            } else drawMainPage(request, response, out, null);
        }
    }
    
    /**
     * Draws an opinion form that sends the information
     * to itself.
     * @param request   The current request
     * @param response  The response to offer
     * @param out       HTML printer
     * @param error     The current error or null
     */
    private void drawMainPage(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String error){
        
        final ServletContext context = getServletContext();
        final String baseUrl = UrlToolkit.baseUrl(request);
        
        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("<head>");
        out.print("<title>Encuestas.com - Opinión</title>");
        out.print("<link rel='stylesheet' href='" + baseUrl + "/styles/master.css' />");
        out.print("</head>");
        out.print("<body>");
        
        if(error != null)
           out.print("<p id='error'>" + error + "</p>");
            
        out.print("<form action='" + baseUrl + "/opinion' method='post'>");
        out.print("<h2>Encuesta de opinión</h2>");
        out.print("<label><b>Nombre:</b> <input type='text' name='name'></label><br><br>");
        out.print("<label><b>Apellidos:</b> <input type='text' name='surname'></label>");
        out.print("<h2>Opinion que le ha merecido este sitio web</h2>");
        out.print("<label><input type='radio' name='rating' value='B'> Buena</label><br>");
        out.print("<label><input type='radio' name='rating' value='R'> Regular</label><br>");
        out.print("<label><input type='radio' name='rating' value='M'> Mala</label>");
        out.print("<h3>Comentarios</h3>");
        out.print("<textarea name='comments'></textarea>");
        out.print("<h3>Tus secciones favoritas</h3>");
        
        final Optional<Stream<String>> lines = IoManager.getSections(context);
        if(lines.isPresent()){
            lines.get().forEach(line -> {
                out.print("<br><label><input type='checkbox' name='sections' value='" + line + "'>" + line + "</label>");
            });

            lines.get().close();
        } else {
            out.print("<p id='error'>No se encontraron secciones</p>");
        } 
        
        out.print("<br><input type='submit' name='send' value='Enviar opinion'>");
        out.print("</form>"); 
        out.print("</body>");
        out.print("</html>");
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
