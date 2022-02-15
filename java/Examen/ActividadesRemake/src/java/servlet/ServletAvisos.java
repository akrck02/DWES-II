/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Actividad;
import bean.Impartidor;
import dao.Dao;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        
        // Si no ha hecho login como impartidor redirige al login
        if(sesion.getAttribute("impartidor") == null){
            response.sendRedirect(getServletContext().getContextPath());
            return;
        }
        
        // Recoger informacion necesaria para el jsp
        final Impartidor impartidor = (Impartidor) sesion.getAttribute("impartidor");
        final Dao db = (Dao) getServletContext().getAttribute("db");
        final ArrayList<Actividad> actividadesImpartidor = db.actividadesImpartidor(impartidor);
        
        request.setAttribute("actividadesImpartidor", actividadesImpartidor);
        
        // Mostrar actividad       
        if(request.getParameter("mostrar") != null) {
            
            try {
               final Integer id = Integer.parseInt(request.getParameter("mostrar"));
               request.setAttribute("mapaAsistenciaActividad", db.mapaAsistenciaActividad(id));
               
            } catch (NumberFormatException e) {
                System.out.println("ID de actividad no numérico, no se pudo mostrar.");
            }
            
        }
        
        // Grabar avisos en archivo
        if(request.getParameter("avisar") != null){
            
            System.out.println("Avisando!");
            
            final String dni = request.getParameter("avisar");
            String direccion = "";
            
            if("Telefono".equals(request.getParameter("medio-" + dni))){
                direccion = request.getParameter("Telefono-" + dni);
            }       
            
            if("Email".equals(request.getParameter("medio-" + dni))){
                direccion = request.getParameter("Email-" + dni);
            }       
            
            System.out.println("DIR " + direccion);
            System.out.println("aLUMN " + dni);
            
            BufferedWriter wr = new BufferedWriter(new FileWriter(getServletContext().getRealPath("files/avisos.tx"
                    + "t"), true));
            wr.write(dni + " : " + direccion + "\n");
            wr.close();
            
            request.setAttribute("avisoRealizado", true);
            
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
