package com.megalab.model;

public class Cliente {

    private int idCliente;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String telefono;

    public Cliente(
            int idCliente,
            String cedula,
            String nombres,
            String apellidos,
            String telefono) {

        this.idCliente = idCliente;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
}