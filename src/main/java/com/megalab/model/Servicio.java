package com.megalab.model;

public class Servicio {

    private int idServicio;
    private String nombre;

    public Servicio(int idServicio, String nombre) {
        this.idServicio = idServicio;
        this.nombre = nombre;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}