/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aketz
 */
public class ServletInscripcion extends HttpServlet {

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
        
        HttpSession sesion = request.getSession(true);  
        HashSet<Integer> actividadesPendientes = (HashSet<Integer>) sesion.getAttribute("inscripcionesPendientes"); 
        
        if(actividadesPendientes ==  null){
            actividadesPendientes = new HashSet();
            sesion.setAttribute("inscripcionesPendientes", actividadesPendientes);
        }
        
        // Apuntar en sesion la inscripcion
        if(request.getParameter("apuntar") != null){
            try {
                actividadesPendientes.add(Integer.parseInt(request.getParameter("apuntar")));
            } catch (NumberFormatException e) {}
            
           
            
            sesion.setAttribute("inscripcionesPendientes", actividadesPendientes);
            response.sendRedirect(getServletContext().getContextPath() + "/alumno.jsp");    
            return;
        }

        // Borrar de sesion la inscripción
        if(request.getParameter("anular") != null) {
            try {
                actividadesPendientes.remove(Integer.parseInt(request.getParameter("anular")));
            } catch (NumberFormatException e) {}
            
            sesion.setAttribute("inscripcionesPendientes", actividadesPendientes);
            response.sendRedirect(getServletContext().getContextPath() + "/alumno.jsp");    
            return;
        }
        
        // Grabar en base de datos las inscripciones.
        if(request.getParameter("grabar") != null) {
            
            
            
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
