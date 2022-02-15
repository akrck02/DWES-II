/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Actividad;
import bean.Alumno;
import bean.Impartidor;
import dao.Dao;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aketz
 */
public class ServletAvisos extends HttpServlet {

    @Override
    public void init() throws ServletException {
       
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
        
        HttpSession sesion = request.getSession(true);
        
        //Si no se ha hecho login con el impartidor, se devuelve al login
        if (sesion.getAttribute("Impartidor") == null) {
            response.sendRedirect(getServletContext().getContextPath());
            return;
        }
        
        // Recoge las actividades del impartidor de la base de datos
        final Dao db = (Dao) getServletContext().getAttribute("db");
        final Impartidor impartidor = (Impartidor) sesion.getAttribute("Impartidor");
        final ArrayList<Actividad> actividadesImpartidor = db.actividadesImpartidor(impartidor);
        
        // Guarda los datos necesarios para la página jsp
        request.setAttribute("actividadesImpartidor", actividadesImpartidor);
        request.setAttribute("mapaAsistencia", new LinkedHashMap<Alumno, java.util.Date>());
        
        // si pulsa avisar
        if(request.getParameter("avisar") != null) {
            
            String direccion = "";
            String dni = request.getParameter("avisar");
           
            // Si es un telefono recoge el telefono
            if("Telefono".equals(request.getParameter("medio-" + dni))){
                direccion = request.getParameter("Telefono-" + dni);
            }
            
            // Si es un email recoge el email
            if("Email".equals(request.getParameter("medio-" + dni))){
                direccion = request.getParameter("Email-" + dni);
            }
            
            // Escribe una linea en el fichero y lo cierra
            BufferedWriter bw = new BufferedWriter(new FileWriter(getServletContext().getRealPath("files/avisos"),true));
            bw.write(dni + " " + direccion + "\n");
            bw.close();
            
            // Devuelve a impartidor.jsp y mostrar notificación 
            request.setAttribute("avisoRealizado", true);
            request.getRequestDispatcher("/impartidor.jsp").forward(request, response);
            return;
        }
        
        
        // si se selecciona una actividad
        if(request.getParameter("actividad") != null) {
            try {
                int idActividad = Integer.parseInt(request.getParameter("actividad"));
                HashMap<Alumno, Date> mapaAsistencia = db.mapaAsistenciaActividad(idActividad);
                
                request.setAttribute("mapaAsistencia", mapaAsistencia);
                request.setAttribute("actividad", idActividad);
                
            } catch (NumberFormatException e) {
                System.out.println("Error en ServletAvisos: actividad no númerica");
            }
            
            request.getRequestDispatcher("/impartidor.jsp").forward(request, response);
            return;
        }
        

        
        request.getRequestDispatcher("/impartidor.jsp").forward(request, response);
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
