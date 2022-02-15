/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author aketza
 */
public class Actividad {
    
    private int id;
    private Impartidor impartidor;
    private String nombre;
    private Double costeMensual;
    private int capacidad;

    public Actividad(int id, Impartidor impartidor, String nombre, Double costeMensual, int capacidad) {
        this.id = id;
        this.impartidor = impartidor;
        this.nombre = nombre;
        this.costeMensual = costeMensual;
        this.capacidad = capacidad;
    }

    public int getId() {
        return id;
    }

    public Impartidor getImpartidor() {
        return impartidor;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public Double getCosteMensual() {
        return costeMensual;
    }
    
    
    public Integer getCosteMensualEntero() {
        return costeMensual.intValue();
    }

    public String getNombre() {
        return nombre;
    }
    
    
    
}
