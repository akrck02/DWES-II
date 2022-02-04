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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    public Alumno loginAlumno(String dni, String clave) {

        final String sql = "SELECT apellidos, nombre, telefono, email FROM alumno WHERE dni = ?";
        Alumno alumno = null;

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, dni);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {

                final String apellidos = rs.getString("apellidos");
                final String nombre = rs.getString("nombre");
                final String telefono = rs.getString("telefono");
                final String email = rs.getString("email");

                String[] apellidosSueltos = apellidos.split(" ");
                String passwordCorrecta = apellidosSueltos[0].substring(0, 3) + apellidosSueltos[1].substring(0, 3);
                passwordCorrecta = passwordCorrecta.toLowerCase();

                if (passwordCorrecta.equals(clave)) {
                    alumno = new Alumno(dni, apellidos, nombre, telefono, email);
                }
            }

            rs.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println("Error en metodo loginAlumno: " + ex);
        }

        return alumno;
    }

    /**
     * Los impartidores se loguean tomando como usuario la clave de la tabla
     * impartidores y como contraseña "damocles".
     */
    public Impartidor loginImpartidor(int id, String clave) {

        final String sql = "SELECT apellido, nombre FROM impartidor WHERE id=?";
        Impartidor impartidor = null;

        if (!"damocles".equals(clave)) {
            return impartidor;
        }

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {

                final String nombre = rs.getString("nombre");
                final String apellido = rs.getString("apellido");

                impartidor = new Impartidor(id, apellido, nombre);
            }

            rs.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println("Error en metodo loginImpartidor: " + ex);
        }

        return impartidor;
    }

    /**
     * Devuelve una lista con todas las actividades de un impartidor.
     */
    public ArrayList<Actividad> actividadesImpartidor(Impartidor impartidor) {

        final String sql = "SELECT id, nombre, coste_mensual FROM actividad WHERE impartidor_id=?";
        ArrayList<Actividad> actividades = new ArrayList<>();

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, impartidor.getId());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                final int id = rs.getInt("id");
                final String nombre = rs.getString("nombre");
                final double costeMensual = rs.getDouble("coste_mensual");
                actividades.add(new Actividad(id, nombre, costeMensual, impartidor));

            }

            rs.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println("Error en metodo actividadesImpartidor: " + ex);
        }

        return actividades;
    }

    /**
     * Recibe un alumno y devuelve una lista con las actividades en las que se
     * ha inscrito.
     */
    public ArrayList<Actividad> actividadesParticipa(Alumno alumno) {

        final String sql = "SELECT id, impartidor_id, nombre, coste_mensual FROM actividad WHERE id IN (SELECT actividad_id FROM participa WHERE alumno_dni = ?)";
        ArrayList<Actividad> actividades = new ArrayList<>();

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, alumno.getDni());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                final int idActividad = rs.getInt("id");
                final int idImpartidor = rs.getInt("impartidor_id");
                final String nombreActividad = rs.getString("nombre");
                final double costeMensualActividad = rs.getDouble("coste_mensual");

                String nombreImpartidor = null;
                String apellidoImpartidor = null;

                Impartidor impartidor = loginImpartidor(idActividad, "damocles");
                actividades.add(new Actividad(idActividad, nombreActividad, costeMensualActividad, impartidor));
            }

            rs.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println("Error en metodo actividadesParticipa: " + ex);
        }

        return actividades;
    }

    /**
     * Recibe un alumno y devuelve una lista de actividades donde puede
     * matricularse.
     *
     * REQUISITOS:
     *
     * - No estar inscrito. (Nombre no coincide con las que sí). - Plazas
     * libres.
     *
     */
    public ArrayList<Actividad> actividadesLibresNoParticipa(Alumno alumno) {

        final String sql
                = "SELECT id, impartidor_id, nombre, coste_mensual FROM actividad "
                + "WHERE id IN (SELECT actividad_id FROM participa WHERE alumno_dni != ?) "
                + "AND capacidad > (SELECT count(actividad_id) FROM participa WHERE actividad_id = actividad.id)";

        ArrayList<Actividad> actividades = new ArrayList<>();

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, alumno.getDni());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final int idActividad = rs.getInt("id");
                final int idImpartidor = rs.getInt("impartidor_id");
                final String nombreActividad = rs.getString("nombre");
                final double costeMensualActividad = rs.getDouble("coste_mensual");

                String nombreImpartidor = null;
                String apellidoImpartidor = null;

                Impartidor impartidor = loginImpartidor(idImpartidor, "damocles");
                actividades.add(new Actividad(idActividad, nombreActividad, costeMensualActividad, impartidor));
            }

            rs.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println("Error en metodo actividadesLibresNoParticipa: " + ex);
        }

        return actividades;

    }

    /**
     * Deducir.
     */
    public void inscribir() {

    }

    /**
     * Recibe un ID de actividad y devuelve un mapa ordenado. Clave -> alumnos
     * participando. Valor -> ultima fecha de asistencia.
     */
    public LinkedHashMap<Alumno, java.util.Date> mapaAsistenciaActividad(int idActividad) {

        final String sql = "SELECT ultima_asistencia, alumno_dni FROM participa WHERE actividad_id = ?";
        LinkedHashMap<Alumno, Date> mapa = new LinkedHashMap<>();
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, idActividad);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                final java.util.Date ultimaAsistencia = rs.getDate("ultima_asistencia");
                final String dniAlumno = rs.getString("alumno_dni");

                final String sql2 = "SELECT apellidos, nombre, telefono, email FROM alumno WHERE dni = ?";
                Alumno alumno = null;

                try {
                    Connection con2 = dataSource.getConnection();
                    PreparedStatement st2 = con2.prepareStatement(sql2);
                    st2.setString(1, dniAlumno);

                    ResultSet rs2 = st2.executeQuery();

                    if (rs2.next()) {

                        final String apellidos = rs2.getString("apellidos");
                        final String nombre = rs2.getString("nombre");
                        final String telefono = rs2.getString("telefono");
                        final String email = rs2.getString("email");

                        alumno = new Alumno(dniAlumno, apellidos, nombre, telefono, email);
                    }

                    rs2.close();
                    st2.close();
                    con2.close();

                } catch (SQLException ex) {
                    System.err.println("Error en metodo mapaAsistenciaActividad encontrando alumno: " + ex);
                }

                mapa.put(alumno, ultimaAsistencia);

            }

            rs.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println("Error en metodo mapaAsistenciaActividad: " + ex);
        }

        return mapa;
    }

}
