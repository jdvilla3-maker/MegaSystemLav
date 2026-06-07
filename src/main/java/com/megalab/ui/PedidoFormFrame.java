package com.megalab.ui;

import com.megalab.model.Cliente;
import com.megalab.model.Servicio;
import com.megalab.model.TipoDetalle;

import com.megalab.dao.ServicioDAO;
import com.megalab.model.DetallePedido;
import com.megalab.service.ClienteService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PedidoFormFrame extends JFrame {

    private JComboBox<Cliente> cbClientes;
    private JComboBox<Servicio> cbServicios;
    private JComboBox<TipoDetalle> cbTipos;

    private JTextField txtFechaRegistro;
    private JTextField txtFechaEntrega;
    private JTextField txtCantidad;

    private JTextArea txtObservaciones;

    private JTable tablaDetalles;
    private DefaultTableModel modeloDetalles;

    private JButton btnAgregarDetalle;
    private JButton btnEliminarDetalle;
    private JButton btnGuardarPedido;
    private JButton btnCancelar;

    private ClienteService clienteService = new ClienteService();

    private ServicioDAO servicioDAO = new ServicioDAO();

    private List<DetallePedido> detallesTemp;

    public PedidoFormFrame() {

        setTitle("MegaLav System - Nuevo Pedido");
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color fondo = new Color(245, 247, 250);
        Color texto = new Color(44, 62, 80);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(fondo);

        // ==================================
        // HEADER
        // ==================================

        JPanel header = new JPanel();
        header.setBackground(fondo);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(20,20,20,20));

        JLabel titulo = new JLabel("NUEVO PEDIDO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(texto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "Registro y gestión de servicios de lavandería"
        );

        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(Color.GRAY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(10));
        header.add(subtitulo);

        // ==================================
        // PANEL DATOS GENERALES
        // ==================================

        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220,220,220)
                        ),
                        new EmptyBorder(20,20,20,20)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cbClientes = new JComboBox<>();

        txtFechaRegistro = new JTextField();
        txtFechaRegistro.setEditable(false);
        txtFechaRegistro.setText(LocalDate.now().toString());

        txtFechaEntrega = new JTextField();

        txtObservaciones = new JTextArea(4,20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);

        JScrollPane scrollObs =
                new JScrollPane(txtObservaciones);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        panelDatos.add(cbClientes, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(new JLabel("Fecha Registro:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(txtFechaRegistro, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Fecha Entrega:"), gbc);

        gbc.gridx = 3;
        panelDatos.add(txtFechaEntrega, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDatos.add(new JLabel("Observaciones:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        panelDatos.add(scrollObs, gbc);

        // ==================================
        // PANEL SERVICIOS
        // ==================================

        JPanel panelServicio = new JPanel(new GridBagLayout());
        panelServicio.setBackground(Color.WHITE);
        panelServicio.setBorder(
                BorderFactory.createTitledBorder(
                        "Agregar Servicio"
                )
        );

        GridBagConstraints s = new GridBagConstraints();

        s.insets = new Insets(10,10,10,10);
        s.fill = GridBagConstraints.HORIZONTAL;

        cbServicios = new JComboBox<>();
        cbTipos = new JComboBox<>();

        txtCantidad = new JTextField();

        btnAgregarDetalle =
                new JButton("Agregar al Pedido");

        s.gridx = 0;
        s.gridy = 0;
        panelServicio.add(new JLabel("Servicio:"), s);

        s.gridx = 1;
        panelServicio.add(cbServicios, s);

        s.gridx = 2;
        panelServicio.add(new JLabel("Tipo:"), s);

        s.gridx = 3;
        panelServicio.add(cbTipos, s);

        s.gridx = 0;
        s.gridy = 1;
        panelServicio.add(new JLabel("Cantidad:"), s);

        s.gridx = 1;
        panelServicio.add(txtCantidad, s);

        s.gridx = 3;
        panelServicio.add(btnAgregarDetalle, s);

        // ==================================
        // TABLA DETALLES
        // ==================================

        modeloDetalles = new DefaultTableModel(
                new String[]{
                        "Servicio",
                        "Tipo",
                        "Cantidad"
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

        tablaDetalles = new JTable(modeloDetalles);

        tablaDetalles.setRowHeight(28);

        JScrollPane scrollTabla =
                new JScrollPane(tablaDetalles);

        // ==================================
        // BOTONES
        // ==================================

        JPanel panelBotones = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        10
                )
        );

        panelBotones.setBackground(fondo);

        btnEliminarDetalle =
                new JButton("Eliminar Detalle");

        btnGuardarPedido =
                new JButton("Guardar Pedido");

        btnCancelar =
                new JButton("Cancelar");

        panelBotones.add(btnEliminarDetalle);
        panelBotones.add(btnGuardarPedido);
        panelBotones.add(btnCancelar);

        // ==================================
        // CENTRO
        // ==================================

        JPanel centro = new JPanel(
                new BorderLayout(10,10)
        );

        centro.setBackground(fondo);
        centro.setBorder(
                new EmptyBorder(20,20,20,20)
        );

        centro.add(panelDatos, BorderLayout.NORTH);

        JPanel panelIntermedio =
                new JPanel(new BorderLayout(10,10));

        panelIntermedio.setBackground(fondo);

        panelIntermedio.add(
                panelServicio,
                BorderLayout.NORTH
        );

        panelIntermedio.add(
                scrollTabla,
                BorderLayout.CENTER
        );

        centro.add(panelIntermedio, BorderLayout.CENTER);

        centro.add(panelBotones, BorderLayout.SOUTH);

        // ==================================
        // ENSAMBLAR
        // ==================================

        principal.add(header, BorderLayout.NORTH);
        principal.add(centro, BorderLayout.CENTER);

        add(principal);

        // ==================================
        // EVENTOS BÁSICOS
        // ==================================

        detallesTemp = new ArrayList<>();

        cargarClientes();
        cargarServicios();
        cargarTipos();

        cbServicios.addActionListener(e -> cargarTipos());

        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarClientes() {

        cbClientes.removeAllItems();

        for (Cliente cliente : clienteService.obtenerClientes()) {
            cbClientes.addItem(cliente);
        }
    }

    private void cargarServicios() {

        cbServicios.removeAllItems();

        for (Servicio servicio : servicioDAO.obtenerServicios()) {
            cbServicios.addItem(servicio);
        }

    }

    private void cargarTipos() {

        cbTipos.removeAllItems();

        Servicio servicio =
                (Servicio) cbServicios.getSelectedItem();

        if (servicio == null) {
            return;
        }

        List<TipoDetalle> lista =
                servicioDAO.obtenerTiposPorServicio(
                        servicio.getIdServicio()
                );

        System.out.println(
                "Tipos encontrados: " + lista.size()
        );

        for (TipoDetalle tipo : lista) {
            cbTipos.addItem(tipo);
        }
    }
}