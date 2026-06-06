package com.megalab.model;

public class Cliente {
    private String id;
    private String nombre;
    private String cedula;
    private String telefono;

    public Cliente(String id, String nombre, String cedula, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getTelefono() { return telefono; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}