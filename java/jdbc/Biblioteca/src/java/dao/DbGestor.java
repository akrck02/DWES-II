/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import bean.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author aketz
 */
public class DbGestor {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASS = "";
    private static BasicDataSource dataSource;

    public DbGestor() {
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
     * Get not returned books ordered by date
     *
     * @return a list of Books
     */
    public List<Book> getNotReturnedBooks() {
        String sql = "SELECT * FROM libro WHERE id IN (SELECT idlibro FROM prestamo ORDER BY fecha)";
        ArrayList<Book> books = new ArrayList<>();

        try {

            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Book.BookBuilder builder = new Book.BookBuilder();
                builder.setId(rs.getInt("id"));
                builder.setTitle(rs.getString("titulo"));
                builder.setAuthor(rs.getInt("idautor"));
                builder.setGenre(rs.getString("genero"));
                builder.setPages(rs.getInt("paginas"));
                books.add(builder.build());
            }

            rs.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println("Error en metodo libros: " + ex);
        }

        return books;
    }

    /**
     * Return books with the given IDs into the database
     *
     * @param bookIds The book ids
     */
    public void returnBooks(Collection<Integer> bookIds) {

        try {
            Connection con = dataSource.getConnection();
            bookIds.forEach(id -> {
                try {
                    String sql = "DELETE FROM prestamo WHERE idlibro = ?";
                    PreparedStatement st = con.prepareStatement(sql);
                    st.setInt(1, id);
                    
                    boolean ok = st.execute();
                    System.out.println(id + " - " + ok);
                    st.close();
                } catch (SQLException ex) {
                    System.err.println("Error en metodo devolver libros: " + ex);
                }
            });

            con.close();
        } catch (SQLException ex) {
            System.err.println("Error en metodo devolver libros: " + ex);
        }
    }

}
