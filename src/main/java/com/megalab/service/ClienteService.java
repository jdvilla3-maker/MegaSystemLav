package com.megalab.service;

import com.megalab.dao.ClienteDAO;
import com.megalab.model.Cliente;

import java.util.List;

public class ClienteService {

    private ClienteDAO clienteDAO;

    public ClienteService() {
        clienteDAO = new ClienteDAO();
    }

    // ==========================
    // AGREGAR
    // ==========================

    public boolean agregarCliente(
            String cedula,
            String nombres,
            String apellidos,
            String telefono
    ) {

        if (clienteDAO.existeCedula(cedula)) {
            return false;
        }

        Cliente cliente = new Cliente(
                0,
                cedula,
                nombres,
                apellidos,
                telefono
        );

        return clienteDAO.agregarCliente(cliente);
    }

    // ==========================
    // LISTAR
    // ==========================

    public List<Cliente> obtenerClientes() {
        return clienteDAO.obtenerClientes();
    }

    // ==========================
    // ELIMINAR
    // ==========================

    public boolean eliminarCliente(int idCliente) {
        return clienteDAO.eliminarCliente(idCliente);
    }

    // ==========================
    // ACTUALIZAR
    // ==========================

    public boolean actualizarCliente(
            int idCliente,
            String cedula,
            String nombres,
            String apellidos,
            String telefono
    ) {

        if (clienteDAO.existeCedulaEnOtroCliente(
                cedula,
                idCliente
        )) {
            return false;
        }

        Cliente cliente = new Cliente(
                idCliente,
                cedula,
                nombres,
                apellidos,
                telefono
        );

        return clienteDAO.actualizarCliente(cliente);
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================

    public Cliente buscarPorId(int idCliente) {
        return clienteDAO.buscarPorId(idCliente);
    }
}