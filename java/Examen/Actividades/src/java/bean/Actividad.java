/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author aketz
 */
public class Actividad {
    
    private int id;
    private String nombre;
    private double coste;
    private Impartidor impartidor;
    
    public Actividad (int id, String nombre, double coste, Impartidor impartidor) {
        this.id = id;
        this.nombre = nombre;
        this.coste = coste;
        this.impartidor = impartidor;
    }
    

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCoste() {
        return coste;
    }
    
    public int getCosteEntero() {
        return (int) coste;
    }

    public Impartidor getImpartidor() {
        return impartidor;
    }    

    @Override
    public String toString() {
        return "Actividad{" + "id=" + id + ", nombre=" + nombre + ", coste=" + coste + ", impartidor=" + impartidor + '}';
    }
    
    
    
}
