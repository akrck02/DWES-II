/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import bean.Actividad;
import bean.Alumno;
import bean.Impartidor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author aketz
 */
public class Dao {
     private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/bdactividad?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASS = "";
    private static BasicDataSource dataSource;

    public Dao() {
        //Creamos el pool de conexiones
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);

        //Indicamos el tamaño del pool de conexiones
        dataSource.setInitialSize(50);
    }
    
    
    
    /**
     * Recibe un dni y una clave y devuelve el alumno los alumnos se loguean
     * usando su dni como usuario y como contraseña las primeras letras de sus
     * apellidos concatenados en minúscula.
     */
    public Alumno loginAlumno(final String usuario, final String clave){
        
        Alumno alumno = null;
        final String sql = "SELECT nombre, apellidos, telefono, email FROM alumno WHERE dni = ?";
        
        try {
            final Connection con = dataSource.getConnection();
            final PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, usuario);
            
            // Si existe devuelve un alumno con sus datos
            final ResultSet rs = st.executeQuery();
            if(rs.next()){
                
                final String nombre = rs.getString("nombre");
                final String apellidos = rs.getString("apellidos");
                final String telefono = rs.getString("telefono");
                final String email = rs.getString("email");
                
                
                // la clave será las tres primeras letras de sus apellidos
                final String[] recortes = apellidos.split(" ");
                final String claveReal = recortes[0].substring(0,3) + recortes[1].substring(0,3);
                
                if(claveReal.toLowerCase().equals(clave)){
                    alumno = new Alumno(usuario, nombre, apellidos, telefono, email);
                }
                
            }        
            
            rs.close();
            st.close();
            con.close();
        } catch(SQLException e) {
            System.out.println("Error en loginAlumnos: " + e.getMessage());
        }
        
        
        return alumno;
    }
    
    /**
     * Los impartidores se loguean tomando como usuario la clave de la tabla
     * impartidores y como contraseña "damocles".
     */
    public Impartidor loginImpartidor(final int usuario, final String clave) {
        
        Impartidor impartidor = null;         
        final String sql = "SELECT id, nombre, apellido FROM impartidor WHERE id = ?";
        
        try {
            final Connection con = dataSource.getConnection();
            final PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, usuario);
            
            // Si existe devuelve un impartidor con sus datos
            final ResultSet rs = st.executeQuery();
            if(rs.next() && "damocles".equals(clave)){
                final Integer id = rs.getInt("id");
                final String nombre = rs.getString("nombre");
                final String apellidos = rs.getString("apellido");
                
                impartidor = new Impartidor(id, nombre, apellidos);
            }        
            
            rs.close();
            st.close();
            con.close();
        } catch(SQLException e) {
            System.out.println("Error en loginImpartidor: " + e.getMessage());
        }
        
        return impartidor;
    }
    
    
    /**
     * Devuelve una lista con todas las actividades de un impartidor.
     */
    public ArrayList<Actividad> actividadesImpartidor(final Impartidor impartidor) {
        
        final ArrayList<Actividad> actividadesImpartidor = new ArrayList<>();
        final String sql = "SELECT id, nombre, coste_mensual, capacidad FROM actividad WHERE impartidor_id = ?";
        
        try {
            final Connection con = dataSource.getConnection();
            final PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, impartidor.getId());
            
            final ResultSet rs = st.executeQuery();
        
            while(rs.next()){
                
                final Integer id = rs.getInt("id");
                final String nombre = rs.getString("nombre");
                final Double costeMensual = rs.getDouble("coste_mensual");
                final Integer capacidad = rs.getInt("capacidad");
                
                actividadesImpartidor.add(new Actividad(id, impartidor, nombre, costeMensual, capacidad));
                
            }
            
            rs.close();
            st.close();
            con.close();
            
        } catch(SQLException e) {
            System.out.println("Error en actividadesImpartidor: " + e.getMessage());
        }        
        
        return actividadesImpartidor;
    }
    
    
    
    
    /**
     * Recibe un alumno y devuelve una lista con las actividades en las que se
     * ha inscrito.
     */
    public ArrayList<Actividad> actividadesParticipa(final Alumno alumno) {
        
        final ArrayList<Actividad> actividadesParticipa = new ArrayList<>();
        final String sql = "SELECT actividad.id, actividad.nombre, actividad.coste_mensual, actividad.capacidad,"
                + " impartidor.id, impartidor.nombre, impartidor.apellido"
                + " FROM participa "
                + " JOIN actividad ON participa.actividad_id = actividad.id"
                + " JOIN impartidor ON actividad.impartidor_id = impartidor.id"
                + " WHERE participa.alumno_dni = ?";
        
        try {
            final Connection con = dataSource.getConnection();
            final PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, alumno.getDni());
            
            final ResultSet rs = st.executeQuery();
            while(rs.next()){
                
                final Integer idActividad = rs.getInt("actividad.id");
                final String nombreActividad = rs.getString("actividad.nombre");
                final Double costeMensual = rs.getDouble("actividad.coste_mensual");
                final Integer capacidad = rs.getInt("actividad.capacidad");
                
                final Integer idImpartidor = rs.getInt("impartidor.id");
                final String nombreImpartidor = rs.getString("impartidor.nombre");
                final String apellidoImpartidor = rs.getString("impartidor.apellido");
                
                final Impartidor impartidor = new Impartidor(idImpartidor, nombreImpartidor, apellidoImpartidor);
                actividadesParticipa.add(new Actividad(idActividad, impartidor, nombreActividad, costeMensual, capacidad));
                
            }
            
            rs.close();
            st.close();
            con.close();
            
        } catch(SQLException e) {
            System.out.println("Error en actividadesParticipa: " + e.getMessage());
        }
        
        return actividadesParticipa; 
    }
    
    
    /**
     * Recibe un alumno y devuelve una lista de actividades donde puede
     * matricularse.
     *
     * REQUISITOS:
     *
     * - No estar inscrito. (Nombre no coincide con las que sí).
     * - Plazas libres.
     *
     */
    public ArrayList<Actividad> actividadesNoParticipa(final Alumno alumno) {
        
        final ArrayList<Actividad> actividadesNoParticipa = new ArrayList<>();
        final String sql = "SELECT DISTINCT actividad.id, actividad.nombre, actividad.coste_mensual, actividad.capacidad,"
                + " impartidor.id, impartidor.nombre, impartidor.apellido"
                + " FROM participa "
                + " JOIN actividad ON participa.actividad_id = actividad.id"
                + " JOIN impartidor ON actividad.impartidor_id = impartidor.id"
                + " WHERE participa.alumno_dni != ?"
                + " AND actividad.nombre NOT IN ("
                + " SELECT actividad.nombre FROM participa JOIN actividad ON actividad.id = participa.actividad_id"
                + " WHERE participa.alumno_dni = ?)"
                + " AND actividad.capacidad > (SELECT count(alumno_dni) FROM participa WHERE actividad_id = actividad.id )"
                ;
        
        try {
            final Connection con = dataSource.getConnection();
            final PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, alumno.getDni());
            st.setString(2, alumno.getDni());
            
            final ResultSet rs = st.executeQuery();
            while(rs.next()){
                final Integer idActividad = rs.getInt("actividad.id");
                final String nombreActividad = rs.getString("actividad.nombre");
                final Double costeMensual = rs.getDouble("actividad.coste_mensual");
                final Integer capacidad = rs.getInt("actividad.capacidad");
                
                final Integer idImpartidor = rs.getInt("impartidor.id");
                final String nombreImpartidor = rs.getString("impartidor.nombre");
                final String apellidoImpartidor = rs.getString("impartidor.apellido");
                
                final Impartidor impartidor = new Impartidor(idImpartidor, nombreImpartidor, apellidoImpartidor);
                actividadesNoParticipa.add(new Actividad(idActividad, impartidor, nombreActividad, costeMensual, capacidad));
            }
            
            rs.close();
            st.close();
            con.close();
        } catch(SQLException e) {
            System.out.println("Error en actividadesNoParticipa: " + e.getMessage());
        }
        
        return actividadesNoParticipa;
    }
    
    
    /**
     * Deducir como incribir.
     */
    public boolean inscribir( final String dniAlumno, final Integer idActividad ){
        
        boolean inscrito = false;
        final String sql = "INSERT INTO participa(actividad_id, alumno_dni, ultima_asistencia) VALUES (?, ?, NOW())";
        
        try {
            final Connection con = dataSource.getConnection();
            final PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, idActividad);
            st.setString(2, dniAlumno);

            inscrito = st.executeUpdate() > 0;

            st.close();
            con.close();
        } catch(SQLException e) {
            System.out.println("Error en inscribir: " + e.getMessage());
        }
        
        return inscrito;
    }
    
    /**
     * Recibe un ID de actividad y devuelve un mapa ordenado. 
     * Clave -> alumno participando. 
     * Valor -> ultima fecha de asistencia.
     */
    public LinkedHashMap<Alumno, java.util.Date> mapaAsistenciaActividad(final int actividad) {
        
        final LinkedHashMap<Alumno, java.util.Date> mapaAsistenciaActividad = new LinkedHashMap<>();
        final String sql = "SELECT participa.ultima_asistencia, alumno.dni, alumno.nombre, alumno.apellidos, alumno.telefono, alumno.email "
                + " FROM participa"
                + " JOIN alumno ON participa.alumno_dni = alumno.dni"
                + " WHERE actividad_id = ?";
        
          try {
            final Connection con = dataSource.getConnection();
            final PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, actividad);
            
            final ResultSet rs = st.executeQuery();
            while(rs.next()){
                
                final Date ultimaAsistencia = rs.getDate("ultima_asistencia");
                final String dni = rs.getString("dni");
                final String nombre = rs.getString("nombre");
                final String apellidos = rs.getString("apellidos");
                final String telefono = rs.getString("telefono");
                final String email = rs.getString("email");
                
                final Alumno alumno = new Alumno(dni, nombre, apellidos, telefono, email);
                mapaAsistenciaActividad.put(alumno, ultimaAsistencia);
                
            }
            
            rs.close();
            st.close();
            con.close();
        } catch(SQLException e) {
            System.out.println("Error en mapaAsistenciaActividad: " + e.getMessage());
        }
        
        
        return mapaAsistenciaActividad;
    }    
    
    
}
