/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ejercicio1.servlets;

import ejercicio1.beans.ConversionCF;
import ejercicio1.beans.UrlToolkit;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aketz
 */
public class Conversor extends HttpServlet {
    
    private HashSet<String> locales;

    @Override
    public void init() throws ServletException {
        super.init(); 
        locales = new HashSet<>();
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
        
        final String locale = request.getLocale().toString(); 
        locales.add(locale);
            
        if (request.getParameter("cel-fah") != null) {
            final String celsius = request.getParameter("celsius");

            if (celsius == null || celsius.equals("")) {
                errorPage(request, response, "No ha indicado los grados celsius");
                return;
            }

            try {
                
                final double value = Double.parseDouble(celsius);
                final ConversionCF conversion = new ConversionCF(value, "c");
                resultPage(request, response, conversion.getCelsius(), conversion.getFahrenheit());
            
            } catch (NumberFormatException e) {
                errorPage(request, response, "La temperatura debe ser númerica");
            }

        } else if (request.getParameter("fah-cel") != null) {
            final String fahrenheit = request.getParameter("fahrenheit");

            if (fahrenheit == null || fahrenheit.equals("")) {
                errorPage(request, response, "No ha indicado los grados fahrenheit");
                return;
            }

            try {
                
                final double value = Double.parseDouble(fahrenheit);
                final ConversionCF conversion = new ConversionCF(value, "f");
                resultPage(request, response, conversion.getCelsius(), conversion.getFahrenheit());
            
            } catch (NumberFormatException e) {
                errorPage(request, response, "La temperatura debe ser númerica");
            }
        } else {
            response.sendRedirect("http://localhost:8080/Ejercicio1");
        }

    }

    /**
     * Sends an error view in HTML to the client
     * @param request   The current request
     * @param response  The response to offer
     * @param message   Error message to send
     */
    private void errorPage(HttpServletRequest request, HttpServletResponse response, String message) {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println(
                    "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<title>Error</title>"
                    + "<link rel='stylesheet' href='http://localhost:8080/Ejercicio1/styles/error.css'>"
                    + "</head>"
                    + "<body>"
                    + "<h2>" + message + "</h1>"
                    + "<p>Selecciona una temperatura válida</p>"
                    + "<a id='error' href='http://localhost:8080/Ejercicio1'>Volver</a>"
                    + "</body>"
                    + "</html>"
            );
        } catch (Exception e) {
        }
    }

    /**
     * Result HTML view that shows the temperature 
     * and the conversion result
     * @param request       The current request
     * @param response      The response to offer 
     * @param celsius       Celsius temperature
     * @param fahrenheit    Fahrenheit temperature 
     */
    private void resultPage(HttpServletRequest request, HttpServletResponse response, double celsius, double fahrenheit) {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            DecimalFormat formatter = new DecimalFormat("#0.00");
            String baseUrl = UrlToolkit.baseUrl(request);
            
            out.println(
                    "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>" 
                    + "<title>Conversion exitosa</title>"
                    + "<link rel='stylesheet' href='" + baseUrl + "/styles/error.css'>"
                    + "</head>"
                    + "<body>"
                    + "<h2>Resultado de la conversion</h1>"
                    + "<p><b>Valor en Celsius: </b>" + formatter.format(celsius) + "</p>"
                    + "<p><b>Valor en Fahrenheit: </b>" + formatter.format(fahrenheit) + "</p>"
                    + "<a href='" + baseUrl + "'>Volver</a>"
                    + "<p id='connect'>Se han establecido conexiones desde " + locales.size() + " distintos locale's</p>"
                    // + "<p>" + Arrays.toString(locales.toArray()) + "</p>"
                    + "</body>"
                    + "</html>"
            );
        } catch (Exception e) {
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
