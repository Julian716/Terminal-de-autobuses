/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.autobuses.entidades;

/**
 *
 * @author alex
 */

public class Pasajero {
    
    private String nombre;
    private String origen;
    private String destino;
    private double precio;
    private int asiento;

    public Pasajero(String nombre, String origen, String destino, double precio, int asiento) {
        this.nombre = nombre;
        this.origen = origen;
        this.destino = destino;
        this.precio = precio;
        this.asiento = asiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public double getPrecio() {
        return precio;
    }

    public int getAsiento() {
        return asiento;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Origen: " + origen + 
               ", Destino: " + destino + ", Precio: $" + precio + 
               ", Asiento: " + asiento;
    }
}


