/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Actividad;
import bean.Alumno;
import dao.Dao;
import java.io.IOException;
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
        final Alumno alumno = (Alumno) sesion.getAttribute("Alumno");
        
        // Si no se ha hecho login como alumno, redirige al servlet
        if(alumno == null){
            response.sendRedirect(getServletContext().getContextPath());
            return;
        }
        
        // Coge actividades pendiente de sesión, si no hay, crea el hashmap
        HashSet<Integer> actividadesPendientes = (HashSet<Integer>) sesion.getAttribute("inscripcionesPendientes");
        if(actividadesPendientes ==  null){
            actividadesPendientes = new HashSet();
            sesion.setAttribute("inscripcionesPendientes", actividadesPendientes);
        }
        
        // Cargar actividades asignadas y disponibles para la JSP
        final Dao db = (Dao) getServletContext().getAttribute("db");
        final ArrayList<Actividad> actividadesParticipa = db.actividadesParticipa(alumno);
        final ArrayList<Actividad> actividadesNoParticipa = db.actividadesLibresNoParticipa(alumno);
        
        request.setAttribute("actividadesParticipa" , actividadesParticipa);
        request.setAttribute("actividadesNoParticipa" , actividadesNoParticipa);
        
        // Apuntar en sesion la inscripcion
        if(request.getParameter("apuntar") != null){
            try {
                actividadesPendientes.add(Integer.parseInt(request.getParameter("apuntar")));
            } catch (NumberFormatException e) {}
            
            sesion.setAttribute("inscripcionesPendientes", actividadesPendientes);
            request.getRequestDispatcher("/alumno.jsp").forward(request, response);
            return; 
        }

        // Borrar de sesion la inscripción
        if(request.getParameter("anular") != null) {
            try {
                actividadesPendientes.remove(Integer.parseInt(request.getParameter("anular")));
            } catch (NumberFormatException e) {}
            
            sesion.setAttribute("inscripcionesPendientes", actividadesPendientes);
            request.getRequestDispatcher("/alumno.jsp").forward(request, response);
            return;
        }
        
        // Grabar en base de datos las inscripciones.
        if(request.getParameter("grabar") != null) {
            
            for (Integer idActividad : actividadesPendientes) {
                db.inscribir(idActividad, alumno.getDni());
            }
            
            // Recarga la información de la base de datos
            request.setAttribute("actividadesParticipa" , db.actividadesParticipa(alumno));
            request.setAttribute("actividadesNoParticipa" , db.actividadesLibresNoParticipa(alumno));
            
            // Vacia el hashset
            sesion.setAttribute("inscripcionesPendientes", new HashSet());
            
        }
        
        
        request.getRequestDispatcher("/alumno.jsp").forward(request, response);
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
        response.sendRedirect(getServletContext().getContextPath());
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
