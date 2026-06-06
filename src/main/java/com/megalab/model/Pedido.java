package com.megalab.model;

import java.sql.Date;

public class Pedido {

    private int idPedido;
    private int idCliente;

    private Date fechaRegistro;
    private Date fechaEntrega;

    private String estado;
    private String observaciones;

    public Pedido(
            int idPedido,
            int idCliente,
            Date fechaRegistro,
            Date fechaEntrega,
            String estado,
            String observaciones
    ) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fechaRegistro = fechaRegistro;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}