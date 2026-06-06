package com.megalab.dao;

import com.megalab.database.ConexionMySQL;
import com.megalab.model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public int insertarPedido(Pedido pedido) {

        String sql = """
                INSERT INTO Pedido
                (
                    idCliente,
                    fechaRegistro,
                    fechaEntrega,
                    estado,
                    observaciones
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = ConexionMySQL.conectar();
                PreparedStatement ps =
                        conn.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            ps.setInt(1, pedido.getIdCliente());
            ps.setDate(2, pedido.getFechaRegistro());
            ps.setDate(3, pedido.getFechaEntrega());
            ps.setString(4, pedido.getEstado());
            ps.setString(5, pedido.getObservaciones());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public List<Pedido> obtenerPedidos() {

        List<Pedido> lista = new ArrayList<>();

        String sql = """
                SELECT *
                FROM Pedido
                ORDER BY idPedido DESC
                """;

        try (
                Connection conn = ConexionMySQL.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(
                        new Pedido(
                                rs.getInt("idPedido"),
                                rs.getInt("idCliente"),
                                rs.getDate("fechaRegistro"),
                                rs.getDate("fechaEntrega"),
                                rs.getString("estado"),
                                rs.getString("observaciones")
                        )
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void actualizarEstado(
            int idPedido,
            String estado
    ) {

        String sql = """
                UPDATE Pedido
                SET estado = ?
                WHERE idPedido = ?
                """;

        try (
                Connection conn = ConexionMySQL.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, estado);
            ps.setInt(2, idPedido);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}