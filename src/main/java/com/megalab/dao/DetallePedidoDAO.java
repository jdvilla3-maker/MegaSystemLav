package com.megalab.dao;

import com.megalab.database.ConexionMySQL;
import com.megalab.model.DetallePedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAO {

    // ==========================
    // INSERTAR
    // ==========================

    public boolean insertarDetalle(DetallePedido detalle) {

        String sql = """
                INSERT INTO DetallePedido
                (
                    idPedido,
                    idServicio,
                    idTipo,
                    cantidad
                )
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conn = ConexionMySQL.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, detalle.getIdPedido());
            ps.setInt(2, detalle.getIdServicio());
            ps.setInt(3, detalle.getIdTipo());
            ps.setDouble(4, detalle.getCantidad());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // OBTENER POR PEDIDO
    // ==========================

    public List<DetallePedido> obtenerPorPedido(int idPedido) {

        List<DetallePedido> lista = new ArrayList<>();

        String sql = """
                SELECT
                    dp.idDetalle,
                    dp.idPedido,
                    dp.idServicio,
                    dp.idTipo,
                    dp.cantidad,
                    s.nombre AS nombreServicio,
                    td.nombre AS nombreTipo
                FROM DetallePedido dp
                INNER JOIN Servicio s
                    ON dp.idServicio = s.idServicio
                INNER JOIN TipoDetalle td
                    ON dp.idTipo = td.idTipo
                WHERE dp.idPedido = ?
                ORDER BY dp.idDetalle
                """;

        try (
                Connection conn = ConexionMySQL.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, idPedido);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                DetallePedido d = new DetallePedido(
                        rs.getInt("idDetalle"),
                        rs.getInt("idPedido"),
                        rs.getInt("idServicio"),
                        rs.getInt("idTipo"),
                        rs.getDouble("cantidad")
                );

                d.setNombreServicio(rs.getString("nombreServicio"));
                d.setNombreTipo(rs.getString("nombreTipo"));

                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // ELIMINAR POR PEDIDO
    // ==========================

    public boolean eliminarPorPedido(int idPedido) {

        String sql = """
                DELETE FROM DetallePedido
                WHERE idPedido = ?
                """;

        try (
                Connection conn = ConexionMySQL.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, idPedido);
            return ps.executeUpdate() >= 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
