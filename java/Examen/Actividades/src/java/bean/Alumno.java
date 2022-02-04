/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author aketz
 */
public class Alumno {
    
    
    private String dni;
    private String apellidos;
    private String nombre;
    private String telefono;
    private String email;
    
    public Alumno(String dni,  String apellidos, String nombre, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }
    

    public String getDni() {
        return dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Alumno{" + "dni=" + dni + ", apellidos=" + apellidos + ", nombre=" + nombre + ", telefono=" + telefono + ", email=" + email + '}';
    }

    
    
    
}
