/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Aketza 
 */
public class Libro {
    private int id;
    private String titulo;
    private int paginas;
    private String genero;
    private int idAutor;

    public Libro(int id, String titulo, int paginas, String genero, int idAutor) {
        this.id = id;
        this.titulo = titulo;
        this.paginas = paginas;
        this.genero = genero;
        this.idAutor = idAutor;
    }

    public Libro(String titulo, int paginas, String genero, int idAutor) {
        this.titulo = titulo;
        this.paginas = paginas;
        this.genero = genero;
        this.idAutor = idAutor;
    }

    public Libro() {
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getPaginas() {
        return paginas;
    }

    public String getGenero() {
        return genero;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
    
}
