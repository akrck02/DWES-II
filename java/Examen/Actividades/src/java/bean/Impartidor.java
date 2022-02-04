/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author aketz
 */
public class Impartidor {
    
    private int id;
    private String apellido;
    private String nombre;

    public Impartidor(int id,  String apellido, String nombre) {
        this.id = id;
        this.apellido = apellido;
        this.nombre = nombre;
    }
    
    public int getId() {
        return id;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Impartidor{" + "id=" + id + ", apellido=" + apellido + ", nombre=" + nombre + '}';
    }

    
    
    
    
    
    
}
