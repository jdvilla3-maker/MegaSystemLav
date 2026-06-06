package com.megalab.dao;

import com.megalab.database.ConexionMySQL;
import com.megalab.model.TipoDetalle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDetalleDAO {

    private Connection conexion;

    public TipoDetalleDAO() {
        conexion = ConexionMySQL.conectar();
    }

    public List<TipoDetalle> obtenerPorServicio(
            int idServicio
    ) {

        List<TipoDetalle> lista = new ArrayList<>();

        String sql = """
            SELECT td.*
            FROM TipoDetalle td
            INNER JOIN ServicioTipoDetalle std
                ON td.idTipo = std.idTipo
            WHERE std.idServicio = ?
        """;

        try (
                PreparedStatement ps =
                        conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, idServicio);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                lista.add(
                        new TipoDetalle(
                                rs.getInt("idTipo"),
                                rs.getString("nombre")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}