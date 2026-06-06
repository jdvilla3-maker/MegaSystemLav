package com.megalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {

    private static final String URL =
            "jdbc:mysql://localhost:3306/MegaLavSystem";

    private static final String USER = "root";

    private static final String PASSWORD = "";

    private static Connection conexion;

    public static Connection conectar() {

        try {

            if (conexion == null || conexion.isClosed()) {

                conexion = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );

                System.out.println("Conexión exitosa a MySQL");
            }

        } catch (SQLException e) {

            System.out.println("Error al conectar con MySQL");
            e.printStackTrace();

        }

        return conexion;
    }
}