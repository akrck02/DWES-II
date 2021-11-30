package ejercicio3.beans;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author aketz
 */
public class UrlToolkit {
    
    /**
     * Get the bas url of the application
     * @param req The request done
     * @return The base URL for the webapp
     */
    public static String baseUrl(HttpServletRequest req){
        StringBuffer url = req.getRequestURL();
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();
        return url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
    }
    
}
