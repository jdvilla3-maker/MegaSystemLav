package com.megalab.dao;

import com.megalab.database.ConexionMySQL;
import com.megalab.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Connection conexion;

    public ClienteDAO() {
        conexion = ConexionMySQL.conectar();
    }

    // ==========================
    // INSERTAR
    // ==========================

    public boolean agregarCliente(Cliente cliente) {

        String sql = """
                INSERT INTO Cliente
                (cedula, nombres, apellidos, telefono)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, cliente.getCedula());
            ps.setString(2, cliente.getNombres());
            ps.setString(3, cliente.getApellidos());
            ps.setString(4, cliente.getTelefono());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    // ==========================
    // LISTAR
    // ==========================

    public List<Cliente> obtenerClientes() {

        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM Cliente";

        try (
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Cliente cliente = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("cedula"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("telefono")
                );

                lista.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // ELIMINAR
    // ==========================

    public boolean eliminarCliente(int idCliente) {

        String sql = """
                DELETE FROM Cliente
                WHERE idCliente = ?
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    // ==========================
    // ACTUALIZAR
    // ==========================

    public boolean actualizarCliente(Cliente cliente) {

        String sql = """
                UPDATE Cliente
                SET
                    cedula = ?,
                    nombres = ?,
                    apellidos = ?,
                    telefono = ?
                WHERE idCliente = ?
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, cliente.getCedula());
            ps.setString(2, cliente.getNombres());
            ps.setString(3, cliente.getApellidos());
            ps.setString(4, cliente.getTelefono());
            ps.setInt(5, cliente.getIdCliente());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================

    public Cliente buscarPorId(int idCliente) {

        String sql = """
                SELECT *
                FROM Cliente
                WHERE idCliente = ?
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("cedula"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("telefono")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ==========================
    // VALIDAR CÉDULA
    // ==========================

    public boolean existeCedula(String cedula) {

        String sql = """
                SELECT COUNT(*)
                FROM Cliente
                WHERE cedula = ?
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, cedula);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // VALIDAR CÉDULA EN EDICIÓN
    // ==========================

    public boolean existeCedulaEnOtroCliente(
            String cedula,
            int idCliente
    ) {

        String sql = """
                SELECT COUNT(*)
                FROM Cliente
                WHERE cedula = ?
                AND idCliente <> ?
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ps.setInt(2, idCliente);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}