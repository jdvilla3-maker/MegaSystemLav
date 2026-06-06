package com.megalab.service;

import com.megalab.dao.DetallePedidoDAO;
import com.megalab.dao.PedidoDAO;
import com.megalab.dao.ServicioDAO;
import com.megalab.model.DetallePedido;
import com.megalab.model.Pedido;
import com.megalab.model.Servicio;
import com.megalab.model.TipoDetalle;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class PedidoService {

    private PedidoDAO pedidoDAO;
    private DetallePedidoDAO detalleDAO;
    private ServicioDAO servicioDAO;

    public PedidoService() {

        pedidoDAO = new PedidoDAO();
        detalleDAO = new DetallePedidoDAO();
        servicioDAO = new ServicioDAO();

    }

    // =========================
    // SERVICIOS
    // =========================

    public List<Servicio> obtenerServicios() {
        return servicioDAO.obtenerServicios();
    }

    public List<TipoDetalle> obtenerTiposPorServicio(
            int idServicio
    ) {
        return servicioDAO.obtenerTiposPorServicio(idServicio);
    }

    // =========================
    // PEDIDOS
    // =========================

    public boolean guardarPedido(
            int idCliente,
            Date fechaEntrega,
            String observaciones,
            List<DetallePedido> detalles
    ) {

        if (detalles == null || detalles.isEmpty()) {
            return false;
        }

        Pedido pedido = new Pedido(
                0,
                idCliente,
                Date.valueOf(LocalDate.now()),
                fechaEntrega,
                "Pendiente",
                observaciones
        );

        int idPedido =
                pedidoDAO.insertarPedido(pedido);

        if (idPedido == -1) {
            return false;
        }

        for (DetallePedido detalle : detalles) {

            DetallePedido detalleGuardar =
                    new DetallePedido(
                            0,
                            idPedido,
                            detalle.getIdServicio(),
                            detalle.getIdTipo(),
                            detalle.getCantidad()
                    );

            detalleDAO.insertarDetalle(
                    detalleGuardar
            );

        }

        return true;
    }

    // =========================
    // CONSULTAS
    // =========================

    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.obtenerPedidos();
    }

    // =========================
    // ESTADOS
    // =========================

    public void actualizarEstado(
            int idPedido,
            String estado
    ) {

        pedidoDAO.actualizarEstado(
                idPedido,
                estado
        );

    }

}