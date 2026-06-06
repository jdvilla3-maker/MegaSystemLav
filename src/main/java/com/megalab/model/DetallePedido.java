package com.megalab.model;

public class DetallePedido {

    private int idDetalle;
    private int idPedido;

    private int idServicio;
    private int idTipo;

    private double cantidad;

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

    public DetallePedido(
            int idServicio,
            int idTipo,
            double cantidad
    ) {
        this.idServicio = idServicio;
        this.idTipo = idTipo;
        this.cantidad = cantidad;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public double getCantidad() {
        return cantidad;
    }
}