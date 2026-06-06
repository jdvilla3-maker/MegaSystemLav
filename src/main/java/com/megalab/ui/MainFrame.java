package com.megalab.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("MegaLav System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color fondo = new Color(245, 247, 250);
        Color azul = new Color(33, 150, 243);
        Color texto = new Color(44, 62, 80);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(fondo);

        // ================= HEADER =================

        JPanel header = new JPanel();
        header.setBackground(fondo);
        header.setBorder(new EmptyBorder(30, 20, 20, 20));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("MEGALAV SYSTEM");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titulo.setForeground(texto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Sistema de Gestión de Lavandería");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(Color.GRAY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(10));
        header.add(subtitulo);

        // ================= CENTRO =================

        JPanel centro = new JPanel(new GridLayout(1, 2, 30, 30));
        centro.setBorder(new EmptyBorder(40, 60, 40, 60));
        centro.setBackground(fondo);

        JButton btnClientes = crearTarjeta(
                "👤 Gestión de Clientes",
                "Registrar, editar y administrar clientes."
        );

        JButton btnPedidos = crearTarjeta(
                "📦 Gestión de Pedidos",
                "Gestionar pedidos y servicios."
        );

        centro.add(btnClientes);
        centro.add(btnPedidos);

        // ================= FOOTER =================

        JLabel footer = new JLabel("MegaLav System v1.0", SwingConstants.CENTER);
        footer.setBorder(new EmptyBorder(10, 10, 20, 10));
        footer.setForeground(Color.GRAY);

        principal.add(header, BorderLayout.NORTH);
        principal.add(centro, BorderLayout.CENTER);
        principal.add(footer, BorderLayout.SOUTH);

        add(principal);

        // EVENTOS

        btnClientes.addActionListener(e ->
                new ClienteFrame().setVisible(true)
        );

        btnPedidos.addActionListener(e ->
                new PedidoFrame().setVisible(true)
        );
    }

    private JButton crearTarjeta(String titulo, String descripcion) {

        JButton boton = new JButton(
                "<html><center>" +
                        "<h2>" + titulo + "</h2>" +
                        "<p>" + descripcion + "</p>" +
                        "</center></html>"
        );

        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        boton.setBackground(Color.WHITE);
        boton.setPreferredSize(new Dimension(300, 200));

        return boton;
    }
}