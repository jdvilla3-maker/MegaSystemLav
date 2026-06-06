package com.megalab.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Mega Lab System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnClientes = new JButton("Gestión de Clientes");
        JButton btnPedidos = new JButton("Gestión de Pedidos (No disponible)");
        JButton btnServicios = new JButton("Gestión de Servicios (No disponible)");

        panel.add(btnClientes);
        panel.add(btnPedidos);
        panel.add(btnServicios);

        add(panel);

        btnClientes.addActionListener(e -> {
            new ClienteFrame().setVisible(true);
        });
    }
}