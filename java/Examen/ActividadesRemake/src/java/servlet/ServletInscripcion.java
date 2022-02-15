/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Alumno;
import dao.Dao;
import java.io.IOException;
import java.io.PrintWriter;
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

        final HttpSession sesion = request.getSession(true);

        // Si no ha hecho login como alumno, redigige al login
        if(sesion.getAttribute("alumno") == null){
            response.sendRedirect(getServletContext().getContextPath());
            return;
        }
        
        // Recoger los datos necesarios para cargar el jsp
        final Alumno alumno = (Alumno) sesion.getAttribute("alumno");
        final Dao db = (Dao) getServletContext().getAttribute("db");
        HashSet<Integer> actividadesPendientes = (HashSet<Integer>) sesion.getAttribute("actividadesPendientes");
        
        request.setAttribute("actividadesParticipa", db.actividadesParticipa(alumno));
        request.setAttribute("actividadesNoParticipa", db.actividadesNoParticipa(alumno));
        
        // Si las actividades pendientes no existen crea el hashset
        if(actividadesPendientes == null) {
            actividadesPendientes = new HashSet<>();
            sesion.setAttribute("actividadesPendientes", actividadesPendientes);
        }
        
        
        // Apuntar actividad
        if(request.getParameter("apuntar") != null) {
            try {
                final Integer id = Integer.parseInt(request.getParameter("apuntar"));
                actividadesPendientes.add(id);
                sesion.setAttribute("actividadesPendientes", actividadesPendientes);
            } catch(NumberFormatException e) {
                System.out.println("ID de actividad no numérico");
           }
            
           request.getRequestDispatcher("/alumno.jsp").forward(request, response);
           return;
        }
        
        // Anular actividad
        if(request.getParameter("anular") != null) {
            
            try{
                final Integer id = Integer.parseInt(request.getParameter("anular"));
                actividadesPendientes.remove(id);
                sesion.setAttribute("actividadesPendientes", actividadesPendientes);
            } catch(NumberFormatException e) {
                System.out.println("ID de actividad no numérico");
            }
            
            request.getRequestDispatcher("/alumno.jsp").forward(request, response);
            return;
        }
        
        // Guardar los cambios
        if(request.getParameter("grabar") != null) {
            
            for (Integer idActividad : actividadesPendientes) {
                db.inscribir(alumno.getDni(), idActividad);
            }
            
            // Recarga los datos de la jsp
            sesion.setAttribute("actividadesPendientes", new HashSet<>());
            request.setAttribute("actividadesParticipa", db.actividadesParticipa(alumno));
            request.setAttribute("actividadesNoParticipa", db.actividadesNoParticipa(alumno));
        }
        
        // Redirigir a alumnos.jsp
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
        response.sendRedirect(getServletContext().getContextPath() + "/alumno.jsp");
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
