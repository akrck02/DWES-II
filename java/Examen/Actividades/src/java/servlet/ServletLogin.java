/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Alumno;
import bean.Impartidor;
import dao.Dao;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ServletLogin extends HttpServlet {

    private Dao db;
    
    @Override
    public void init() throws ServletException {
    
        this.db = new Dao();
        getServletContext().setAttribute("db", db);
        
        super.init(); //To change body of generated methods, choose Tools | Templates.
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
        
        RequestDispatcher indexDispatcher = request.getRequestDispatcher("/");
        
        if(request.getParameter("login") == null){
            indexDispatcher.forward(request, response);
            return;
        }
        
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        
        if(usuario == null || "".equals(usuario)){
            request.setAttribute("error", "El usuario no puede estár vacio");
            indexDispatcher.forward(request, response);
            return;
        }
        
        if(clave == null || "".equals(clave)){
            request.setAttribute("error", "La contaseña no puede estár vacia");
            indexDispatcher.forward(request, response);
            return;
        }
        
        HttpSession sesion = request.getSession(true);
        
        //Login alumno
        if("Alumno".equals(request.getParameter("login"))) {
                
            Alumno alumno = db.loginAlumno(usuario, clave);
            if(alumno == null) {
                request.setAttribute("error", "Credenciales incorrectas.");
                indexDispatcher.forward(request, response);
                return;                
            }
            
            //redirigir a alumno.jsp
            sesion.setAttribute("Alumno", alumno);
            response.sendRedirect(getServletContext().getContextPath() + "/alumno.jsp");    
            return;     
        }
        
        // Login profesor
        if("Impartidor".equals(request.getParameter("login"))) {
            
            try {
                int id = Integer.parseInt(usuario);
                Impartidor impartidor = db.loginImpartidor(id, clave);
                
                if(impartidor == null) {
                    request.setAttribute("error", "Credenciales incorrectas. (No existe impartidor)");
                    indexDispatcher.forward(request, response);
                    return;                
                }
                
                //redirigir a impartidor.jsp
                 sesion.setAttribute("Impartidor", impartidor);
                 response.sendRedirect(getServletContext().getContextPath() + "/impartidor.jsp");
                
            } catch(NumberFormatException e) {
                request.setAttribute("error", "Credenciales incorrectas.");
                indexDispatcher.forward(request, response);    
            }
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
