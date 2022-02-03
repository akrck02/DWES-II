/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Autor;
import bean.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Akaitz
 */
public class GestorBD {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASS = "";
    private static BasicDataSource dataSource;

    public GestorBD() {
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
     * Get all the authors from the database
     * @return LinkedList of authors.
     */
    public LinkedList<Autor> autores() {
        String sql = "SELECT id, nombre, fechanac, nacionalidad FROM autor";
        LinkedList autores = new LinkedList();
        
        try {

            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                
                final int id = rs.getInt("id");
                final String nombre = rs.getString("nombre");
                final Date fechanac = rs.getDate("fechanac");
                final String nacionalidad = rs.getString("nacionalidad");
                
                autores.add(new Autor(id, nombre, fechanac, nacionalidad));
            
            }

            rs.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println("Error en metodo autores: " + ex);
        }
        
        return autores;
    }
    
    /**
     * Get all the books of an author
     * @param autor The ID of the author
     * @return LinkedList of Books belonging to an author.
     */
    public LinkedList<Libro> librosAutor(int autor) {
        
        String sql = "SELECT id, titulo, paginas, genero, idautor FROM libro WHERE idautor = ?";
        LinkedList<Libro> librosAutor = new LinkedList<>();
        
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, autor);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                final int id = rs.getInt("id");
                final String titulo = rs.getString("titulo");
                final int paginas = rs.getInt("paginas");
                final String genero = rs.getString("genero");
                
                librosAutor.add(new Libro(id, titulo, paginas, genero, autor));
            }
            
            rs.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error en metodo librosAutor: " + ex);
        }
        
        return librosAutor;
    }
    
    /**
     * Get the author name from ID
     * @param autor Author ID.
     * @return Name of the author.
     */
    public String nombreAutor(int autor) {
        
        String sql = "SELECT nombre FROM autor WHERE id= ?";
        String name = null;
         
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, autor);
            
            ResultSet rs = st.executeQuery();            
            if(rs.next()) {
                name = rs.getString("nombre");
            }
            
            st.close();
            con.close(); 
        } catch (Exception ex) {
              System.err.println("Error en metodo nombreAutor: " + ex);
        }
        
        return name;
    }
    
   /**
     * Inserts loan into database
     * @param libro The book to loan.
     * @return if operations succeded.
     */
    public boolean prestarLibro( int libro ) {
        String sql = "INSERT INTO prestamo(fecha, idlibro) VALUES(NOW(),?)";
        
        try {
            
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setInt(1, libro);
            st.execute();
            st.close();
            con.close();
            return true;
            
        } catch (SQLException ex) {
            System.err.println("Error en metodo prestarLibro: " + ex);
            return false;
        }
    }
    
    /**
     * Get is author with same name already exists
     * @param nombre The author name.
     * @return If author exists.
     */
    public boolean existeAutor(String nombre) {
        String sql = "SELECT id FROM autor WHERE nombre=?";
        boolean existe = false;
        try {
            
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement(sql);          
            st.setString(1, nombre);
            ResultSet rs = st.executeQuery();
            
            if(rs.next())
                existe = true;
                    
            rs.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error en metodo existeAutor: " + ex);
            existe = true;
        }
        
        return existe;
    }
    
    /**
     * Insert an author in database
     * @param autor The author itself.
     * @return If operation succeded.
     */
    public boolean nuevoAutor(Autor autor){
        
        String sql = "INSERT INTO autor(nombre, fechanac, nacionalidad) VALUES(?,?,?)";
        boolean creado = true;
        
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, autor.getNombre());
            st.setDate(2, new java.sql.Date(autor.getFechanac().getTime()));
            st.setString(3, autor.getNacionalidad());
            
            boolean rs = st.execute();
            if(!rs)
                creado = false;
            
            st.close();
            con.close();
        } catch ( SQLException ex ) {
            System.err.println("Error en metodo nuevoAutor: " + ex);
            creado = false;
        }
        
        return creado;    
    
    }
    
    
}
