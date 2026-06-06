package com.megalab.dao;

import com.megalab.database.ConexionMySQL;
import com.megalab.model.Servicio;
import com.megalab.model.TipoDetalle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {

    public List<Servicio> obtenerServicios() {

        List<Servicio> lista = new ArrayList<>();

        String sql = """
                SELECT *
                FROM Servicio
                ORDER BY nombre
                """;

        try (
                Connection conn = ConexionMySQL.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(
                        new Servicio(
                                rs.getInt("idServicio"),
                                rs.getString("nombre")
                        )
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<TipoDetalle> obtenerTiposPorServicio(int idServicio) {

        List<TipoDetalle> lista = new ArrayList<>();

        String sql = """
                SELECT td.*
                FROM TipoDetalle td
                INNER JOIN ServicioTipoDetalle std
                    ON td.idTipo = std.idTipo
                WHERE std.idServicio = ?
                ORDER BY td.nombre
                """;

        try (
                Connection conn = ConexionMySQL.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)
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