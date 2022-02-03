/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Autor;
import bean.Libro;
import dao.GestorBD;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aketz
 */
public class Controlador extends HttpServlet {
    
    private GestorBD db;

    /**
     * Gets the servlet up and load the database controller
     * @throws ServletException 
     */
    @Override
    public void init() throws ServletException {
        super.init(); 
        db = new GestorBD();
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
        
        // Getting the authors from database.
        LinkedList<Autor> autores = db.autores();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/autores.jsp");
        request.setAttribute("autores", autores);
        
        
        // if author is selected get books. 
        if(request.getParameter("autor") != null){
            try {
                
                int id = Integer.parseInt(request.getParameter("autor"));
                LinkedList<Libro> librosAutor = db.librosAutor(id);
                
                request.setAttribute("autor", db.nombreAutor(id));
                request.setAttribute("librosAutor", librosAutor);
            
            } catch(NumberFormatException e){
            
                request.setAttribute("error", "Autor inválido.");
                dispatcher.forward(request, response);
                return;
            
            }
            
        }
        
        // if new author is pressed try adding it to database.
        if(request.getParameter("nuevoAutor") != null) {
            
            String nombre = request.getParameter("nombre");
            String fechaNacString = request.getParameter("fechanac");
            String nacionalidad = request.getParameter("nacionalidad");
            
            if(nombre == null || "".equals(nombre)){
                request.setAttribute("error", "El nombre no puede estar vacío.");       
                dispatcher.forward(request, response);  
                return;
            }
            
            if(fechaNacString == null || "".equals(fechaNacString)){
                request.setAttribute("error", "La fecha de nacimiento no puede estar vacía.");
                dispatcher.forward(request, response);
                return;
            }
            
            Date fecha = null;
            
            try {
                SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
                fecha = formateador.parse(fechaNacString);
            } catch (ParseException e) {
                request.setAttribute("error", "La fecha de nacimiento con formato incorrecto (use yyyy/MM/dd).");
                dispatcher.forward(request, response);
                return;
            }
            
            if(nacionalidad == null || "".equals(nacionalidad)) {
                request.setAttribute("error", "La nacionalidad no puede estar vacía.");
                dispatcher.forward(request, response);
                return;
            }
            
            Autor autor = new Autor(-1, nombre, fecha, nacionalidad);    
            
            if(db.existeAutor(nombre)){
                request.setAttribute("error", "El autor ya existe.");
                dispatcher.forward(request, response);
                return;
            }
            
            
            db.nuevoAutor(autor);
            request.setAttribute("message", "Autor creado correctamente.");
        }
        
        // if loan is request process it.
        if(request.getParameter("prestar") != null) {
            try {
                
                final int id = Integer.parseInt(request.getParameter("prestar"));                
                if(db.prestarLibro(id)) {
                    request.setAttribute("message", "El libro ha sido prestado.");
                } else {
                    request.setAttribute("error", "El libro no se pudo prestar.");
                }
                
                
            } catch (NumberFormatException ex) {
                request.setAttribute("error", "Libro inválido, el préstamo es imposible.");
            }
        }
        
        // Redirect to home
        dispatcher.forward(request, response);
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
