package com.megalab.model;

public class DetallePedido {

    private int idDetalle;
    private int idPedido;
    private int idServicio;
    private int idTipo;
    private double cantidad;

    // Campos de presentación (joins)
    private String nombreServicio;
    private String nombreTipo;

    // Constructor completo (con ID)
    public DetallePedido(
            int idDetalle,
            int idPedido,
            int idServicio,
            int idTipo,
            double cantidad
    ) {
        this.idDetalle = idDetalle;
        this.idPedido = idPedido;
        this.idServicio = idServicio;
        this.idTipo = idTipo;
        this.cantidad = cantidad;
    }

    // Constructor para detalles temporales (sin ID ni pedido)
    public DetallePedido(
            int idServicio,
            int idTipo,
            double cantidad,
            String nombreServicio,
            String nombreTipo
    ) {
        this.idServicio = idServicio;
        this.idTipo = idTipo;
        this.cantidad = cantidad;
        this.nombreServicio = nombreServicio;
        this.nombreTipo = nombreTipo;
    }

    public int getIdDetalle() { return idDetalle; }
    public int getIdPedido() { return idPedido; }
    public int getIdServicio() { return idServicio; }
    public int getIdTipo() { return idTipo; }
    public double getCantidad() { return cantidad; }

    public String getNombreServicio() { return nombreServicio; }
    public String getNombreTipo() { return nombreTipo; }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }
}
