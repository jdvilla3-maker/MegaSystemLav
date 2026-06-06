package com.megalab.model;

public class TipoDetalle {

    private int idTipo;
    private String nombre;

    public TipoDetalle(int idTipo, String nombre) {
        this.idTipo = idTipo;
        this.nombre = nombre;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}