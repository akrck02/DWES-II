/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import bean.Book;
import dao.DbGestor;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aketz
 */
public class ReturnServlet extends HttpServlet {

    private DbGestor db;

    @Override
    public void init() throws ServletException {
        super.init();
        this.db = new DbGestor();
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

        final HttpSession session = request.getSession(true);
        
        if (request.getParameter("save") != null) {
            saveReturnedBooks(session);
            getNotReturnedBooks(session);
            response.sendRedirect(getServletContext().getContextPath());
            return;
        }
        
        getNotReturnedBooks(session);
        processReturn(request, session);
        response.sendRedirect(getServletContext().getContextPath());
    }
    
    /**
     * Get the not returned books by date
     * @param session Client session
     */
    private void getNotReturnedBooks(HttpSession session){
          System.out.println("[Method] Getting not returned books!");
        List<Book> books = db.getNotReturnedBooks();
        session.setAttribute("books", books);
    }
    
    /**
     * Save the returned book into database
     */
    private void saveReturnedBooks(HttpSession session) {
        HashSet<Integer> selected = (HashSet<Integer>) session.getAttribute("return");
        System.out.println("[Method] Saving returned books!");

        if (selected == null) {
            return;
        }
        
        db.returnBooks(selected);
        session.setAttribute("return", new HashSet<Integer>());
    }

    /**
     * Process the return / revert request
     * @param request The HTTP request
     * @param session The client session
     */
    private void processReturn(HttpServletRequest request, HttpSession session) throws IOException {
        
        if (request.getParameter("id") == null) {
            return;
        }
        
         System.out.println("[Method] Processing returned books!");
        try {
            final Integer id = Integer.parseInt(request.getParameter("id"));

            HashSet<Integer> selected = (HashSet<Integer>) session.getAttribute("return");

            if (selected == null) {
                selected = new HashSet<>();
            }

            if (request.getParameter("revert") == null) {
                selected.add(id);
            } else {
                selected.remove(id);
            }
            
             session.setAttribute("return", selected);
        } catch (NumberFormatException e) {
            System.out.println("[Return Servlet] Id is incorrect, redirecting...");
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
