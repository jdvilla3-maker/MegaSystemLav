package com.megalab.dao;

import com.megalab.database.ConexionMySQL;
import com.megalab.model.DetallePedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetallePedidoDAO {

    public void insertarDetalle(
            DetallePedido detalle
    ) {

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

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}