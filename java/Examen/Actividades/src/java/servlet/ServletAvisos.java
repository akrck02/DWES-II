/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Alumno;
import dao.Dao;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
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

    private Dao db;

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
        
        if (sesion.getAttribute("Impartidor") == null) {
            response.sendRedirect(getServletContext().getContextPath());
            return;
        }
        
        this.db = (Dao) getServletContext().getAttribute("db");
        RequestDispatcher impartidorDispatcher = request.getRequestDispatcher("/impartidor.jsp");
        
        
                // si pulsa avisar
        if(request.getParameter("avisar") != null) {
            System.out.println("EScribiendo");
            String direccion = "";
            
            String dni = request.getParameter("dni");
            
            if("Telefono".equals(request.getParameter("medio"))){
                direccion = request.getParameter("Telefono");
            }
            
            if("Email".equals(request.getParameter("medio"))){
                direccion = request.getParameter("Email");
            }
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(getServletContext().getRealPath("files/avisos")));
            bw.write(dni + " " + direccion);
            bw.close();
            
        }
        
        
        // si se selecciona una actividad
        if(request.getParameter("actividad") != null) {
            System.out.println("Lol");
            try {
                
                int idActividad = Integer.parseInt(request.getParameter("actividad"));
                HashMap<Alumno, Date> mapaAsistencia = db.mapaAsistenciaActividad(idActividad);
                
                request.setAttribute("mapaAsistencia", mapaAsistencia);
                request.setAttribute("actividad", idActividad);
                
            } catch (NumberFormatException e) {
                System.out.println("Error en ServletAvisos: actividad no númerica");
            }
            
            impartidorDispatcher.forward(request, response);
            return;
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
