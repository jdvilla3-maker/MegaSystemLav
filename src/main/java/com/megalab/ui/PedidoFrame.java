package com.megalab.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PedidoFrame extends JFrame {

    private JTextField txtBuscar;

    private JButton btnNuevoPedido;
    private JButton btnVerDetalle;
    private JButton btnActualizarEstado;
    private JButton btnVolver;

    private JTable tablaPedidos;
    private DefaultTableModel modelo;

    public PedidoFrame() {

        setTitle("MegaLav System - Gestión de Pedidos");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color fondo = new Color(245, 247, 250);
        Color texto = new Color(44, 62, 80);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(fondo);

        // =====================================
        // HEADER
        // =====================================

        JPanel header = new JPanel();
        header.setBackground(fondo);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("GESTIÓN DE PEDIDOS");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(texto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "Administración y seguimiento de pedidos"
        );

        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(Color.GRAY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(10));
        header.add(subtitulo);

        // =====================================
        // PANEL SUPERIOR
        // =====================================

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(fondo);

        JPanel panelBusqueda = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );
        panelBusqueda.setBackground(fondo);

        panelBusqueda.add(new JLabel("Buscar pedido:"));

        txtBuscar = new JTextField(25);

        panelBusqueda.add(txtBuscar);

        JPanel panelNuevo = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        panelNuevo.setBackground(fondo);

        btnNuevoPedido = new JButton("➕ Nuevo Pedido");

        panelNuevo.add(btnNuevoPedido);

        panelSuperior.add(panelBusqueda, BorderLayout.WEST);
        panelSuperior.add(panelNuevo, BorderLayout.EAST);

        // =====================================
        // TABLA
        // =====================================

        modelo = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Cliente",
                        "Fecha Registro",
                        "Fecha Entrega",
                        "Estado"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        tablaPedidos = new JTable(modelo);

        tablaPedidos.setRowHeight(30);

        tablaPedidos.getTableHeader()
                .setReorderingAllowed(false);

        tablaPedidos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaPedidos.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        DefaultTableCellRenderer centro =
                new DefaultTableCellRenderer();

        centro.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        tablaPedidos.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centro);

        tablaPedidos.getColumnModel()
                .getColumn(2)
                .setCellRenderer(centro);

        tablaPedidos.getColumnModel()
                .getColumn(3)
                .setCellRenderer(centro);

        tablaPedidos.getColumnModel()
                .getColumn(4)
                .setCellRenderer(centro);

        JScrollPane scroll =
                new JScrollPane(tablaPedidos);

        // =====================================
        // BOTONES INFERIORES
        // =====================================

        JPanel panelBotones = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        10
                )
        );

        panelBotones.setBackground(fondo);

        btnVerDetalle =
                new JButton("Ver Detalle");

        btnActualizarEstado =
                new JButton("Actualizar Estado");

        btnVolver =
                new JButton("Volver");

        btnVerDetalle.setEnabled(false);
        btnActualizarEstado.setEnabled(false);

        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnActualizarEstado);
        panelBotones.add(btnVolver);

        // =====================================
        // CENTRO
        // =====================================

        JPanel centroPanel =
                new JPanel(new BorderLayout(10, 10));

        centroPanel.setBackground(fondo);

        centroPanel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        centroPanel.add(
                panelSuperior,
                BorderLayout.NORTH
        );

        centroPanel.add(
                scroll,
                BorderLayout.CENTER
        );

        centroPanel.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        // =====================================
        // ENSAMBLAR
        // =====================================

        principal.add(header, BorderLayout.NORTH);
        principal.add(centroPanel, BorderLayout.CENTER);

        add(principal);

        // =====================================
        // EVENTOS
        // =====================================

        btnNuevoPedido.addActionListener(e -> {
            new PedidoFormFrame().setVisible(true);
        });

        tablaPedidos.getSelectionModel()
                .addListSelectionListener(e -> {

                    boolean seleccionado =
                            tablaPedidos.getSelectedRow() != -1;

                    btnVerDetalle.setEnabled(seleccionado);
                    btnActualizarEstado.setEnabled(seleccionado);
                });

        btnVerDetalle.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Funcionalidad en desarrollo"
            );
        });

        btnActualizarEstado.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Funcionalidad en desarrollo"
            );
        });

        btnVolver.addActionListener(e -> {
            dispose();
        });
    }
}