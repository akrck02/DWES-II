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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aketza
 */
public class ServletLogin extends HttpServlet {

    
    private Dao db;
    
    @Override
    public void init() throws ServletException {
    
        // Crea el pool de conexiones a la base de datos 
        // y las guarda en el contexto local.
        this.db = new Dao();
        getServletContext().setAttribute("db", this.db);
        
        super.init(); 
        
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
      
        // Crea la sesión
        final HttpSession sesion = request.getSession(true);
        
        // Valida parametros        
        final String usuario = request.getParameter("usuario");
        final String clave = request.getParameter("clave");
        
        if("".equals(usuario)) {
            request.setAttribute("error", "el usuario no puede estar vacío.");
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }
        
        if("".equals(clave)) {
            request.setAttribute("error", "Introduzca una clave");
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }
        
        // Login alumno
        if("Alumno".equals(request.getParameter("login"))) {
                 
            final Alumno alumno = db.loginAlumno(usuario, clave);
            if(alumno != null) {
                sesion.setAttribute("alumno", alumno);
                response.sendRedirect(getServletContext().getContextPath() + "/alumno.jsp");
                return;
            }    
                
            request.setAttribute("error", "Credenciales incorrectas.");
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }
        
        // Login profesor
        if("Impartidor".equals(request.getParameter("login"))) {
            
            try {
                final Integer id = Integer.parseInt(usuario);
                final Impartidor impartidor = db.loginImpartidor(id, clave);

                if(impartidor != null){
                    sesion.setAttribute("impartidor", impartidor);
                    response.sendRedirect(getServletContext().getContextPath() + "/impartidor.jsp");
                    return;
                }

                request.setAttribute("error", "Credenciales incorrectas.");
                request.getRequestDispatcher("/").forward(request, response);
                return;
            
            } catch(NumberFormatException e){
                request.setAttribute("error", "El impartidor no existe.");
                request.getRequestDispatcher("/").forward(request, response);
                return;
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
       response.sendRedirect(getServletContext().getContextPath());
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
