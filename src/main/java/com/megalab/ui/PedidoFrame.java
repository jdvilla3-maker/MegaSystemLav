package com.megalab.ui;

import com.megalab.model.DetallePedido;
import com.megalab.model.Pedido;
import com.megalab.service.PedidoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PedidoFrame extends JFrame {

    private JTextField txtBuscar;
    private JButton btnNuevoPedido;
    private JButton btnVerDetalle;
    private JButton btnActualizarEstado;
    private JButton btnVolver;

    private JTable tablaPedidos;
    private DefaultTableModel modelo;

    private PedidoService pedidoService = new PedidoService();

    // Estados válidos según el ENUM de la BD
    private static final String[] ESTADOS = {
            "Pendiente", "En proceso", "Listo", "Entregado", "Cancelado"
    };

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

        JLabel subtitulo = new JLabel("Administración y seguimiento de pedidos");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(Color.GRAY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(10));
        header.add(subtitulo);

        // =====================================
        // PANEL SUPERIOR (búsqueda + botón nuevo)
        // =====================================

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(fondo);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(fondo);
        panelBusqueda.add(new JLabel("Buscar por cliente o estado:"));

        txtBuscar = new JTextField(25);
        panelBusqueda.add(txtBuscar);

        JButton btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.addActionListener(e -> filtrarTabla(txtBuscar.getText().trim()));
        panelBusqueda.add(btnBuscar);

        JButton btnRefrescar = new JButton("↻ Refrescar");
        btnRefrescar.addActionListener(e -> {
            txtBuscar.setText("");
            cargarDatos();
        });
        panelBusqueda.add(btnRefrescar);

        JPanel panelNuevo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelNuevo.setBackground(fondo);

        btnNuevoPedido = new JButton("➕ Nuevo Pedido");
        btnNuevoPedido.setBackground(new Color(46, 204, 113));
        btnNuevoPedido.setForeground(Color.WHITE);
        btnNuevoPedido.setFocusPainted(false);
        btnNuevoPedido.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelNuevo.add(btnNuevoPedido);

        panelSuperior.add(panelBusqueda, BorderLayout.WEST);
        panelSuperior.add(panelNuevo, BorderLayout.EAST);

        // =====================================
        // TABLA
        // =====================================

        modelo = new DefaultTableModel(
                new String[]{"ID", "Cliente", "Fecha Registro", "Fecha Entrega", "Estado", "Observaciones"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPedidos = new JTable(modelo);
        tablaPedidos.setRowHeight(30);
        tablaPedidos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaPedidos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaPedidos.getTableHeader().setReorderingAllowed(false);
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Centrar columnas
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        for (int col : new int[]{0, 2, 3, 4}) {
            tablaPedidos.getColumnModel().getColumn(col).setCellRenderer(centro);
        }

        // Ancho columna ID
        tablaPedidos.getColumnModel().getColumn(0).setMaxWidth(60);

        // Renderer coloreado para estado
        tablaPedidos.getColumnModel().getColumn(4).setCellRenderer(
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column
                    ) {
                        super.getTableCellRendererComponent(
                                table, value, isSelected, hasFocus, row, column
                        );
                        setHorizontalAlignment(SwingConstants.CENTER);
                        if (!isSelected) {
                            String estado = value != null ? value.toString() : "";
                            switch (estado) {
                                case "Pendiente"   -> setBackground(new Color(255, 243, 205));
                                case "En proceso"  -> setBackground(new Color(207, 226, 255));
                                case "Listo"       -> setBackground(new Color(212, 237, 218));
                                case "Entregado"   -> setBackground(new Color(220, 220, 220));
                                case "Cancelado"   -> setBackground(new Color(248, 215, 218));
                                default            -> setBackground(Color.WHITE);
                            }
                        }
                        return this;
                    }
                }
        );

        JScrollPane scroll = new JScrollPane(tablaPedidos);

        // =====================================
        // BOTONES INFERIORES
        // =====================================

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(fondo);

        btnVerDetalle = new JButton("📋 Ver Detalle");
        btnActualizarEstado = new JButton("🔄 Actualizar Estado");
        btnVolver = new JButton("← Volver");

        btnVerDetalle.setEnabled(false);
        btnActualizarEstado.setEnabled(false);

        panelBotones.add(btnVerDetalle);
        panelBotones.add(btnActualizarEstado);
        panelBotones.add(btnVolver);

        // =====================================
        // ENSAMBLADO CENTRAL
        // =====================================

        JPanel centroPanel = new JPanel(new BorderLayout(10, 10));
        centroPanel.setBackground(fondo);
        centroPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        centroPanel.add(panelSuperior, BorderLayout.NORTH);
        centroPanel.add(scroll, BorderLayout.CENTER);
        centroPanel.add(panelBotones, BorderLayout.SOUTH);

        principal.add(header, BorderLayout.NORTH);
        principal.add(centroPanel, BorderLayout.CENTER);

        add(principal);

        // =====================================
        // EVENTOS
        // =====================================

        btnNuevoPedido.addActionListener(e ->
                new PedidoFormFrame(this).setVisible(true)
        );

        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            boolean seleccionado = tablaPedidos.getSelectedRow() != -1;
            btnVerDetalle.setEnabled(seleccionado);
            btnActualizarEstado.setEnabled(seleccionado);
        });

        btnVerDetalle.addActionListener(e -> verDetalle());

        btnActualizarEstado.addActionListener(e -> actualizarEstado());

        btnVolver.addActionListener(e -> dispose());

        txtBuscar.addActionListener(e -> filtrarTabla(txtBuscar.getText().trim()));

        // Carga inicial
        cargarDatos();
    }

    // =====================================
    // CARGAR DATOS
    // =====================================

    public void cargarDatos() {

        modelo.setRowCount(0);

        List<Pedido> pedidos = pedidoService.obtenerPedidos();

        for (Pedido p : pedidos) {

            String nombreCliente = p.getNombreCliente() != null
                    ? p.getNombreCliente()
                    : "ID: " + p.getIdCliente();

            modelo.addRow(new Object[]{
                    p.getIdPedido(),
                    nombreCliente,
                    p.getFechaRegistro(),
                    p.getFechaEntrega(),
                    p.getEstado(),
                    p.getObservaciones() != null ? p.getObservaciones() : ""
            });
        }
    }

    // =====================================
    // FILTRAR
    // =====================================

    private void filtrarTabla(String filtro) {

        modelo.setRowCount(0);

        List<Pedido> pedidos = pedidoService.obtenerPedidos();

        for (Pedido p : pedidos) {

            String nombreCliente = p.getNombreCliente() != null
                    ? p.getNombreCliente()
                    : "ID: " + p.getIdCliente();

            boolean coincide =
                    filtro.isEmpty() ||
                            nombreCliente.toLowerCase().contains(filtro.toLowerCase()) ||
                            p.getEstado().toLowerCase().contains(filtro.toLowerCase());

            if (coincide) {
                modelo.addRow(new Object[]{
                        p.getIdPedido(),
                        nombreCliente,
                        p.getFechaRegistro(),
                        p.getFechaEntrega(),
                        p.getEstado(),
                        p.getObservaciones() != null ? p.getObservaciones() : ""
                });
            }
        }
    }

    // =====================================
    // VER DETALLE
    // =====================================

    private void verDetalle() {

        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) return;

        int idPedido = (int) modelo.getValueAt(fila, 0);
        String cliente = modelo.getValueAt(fila, 1).toString();
        String estado = modelo.getValueAt(fila, 4).toString();

        List<DetallePedido> detalles = pedidoService.obtenerDetallesPorPedido(idPedido);

        if (detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Este pedido no tiene detalles registrados.",
                    "Sin detalles",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear tabla de detalles
        DefaultTableModel modeloDetalle = new DefaultTableModel(
                new String[]{"Servicio", "Tipo / Prenda", "Cantidad"},
                0
        );

        for (DetallePedido d : detalles) {
            modeloDetalle.addRow(new Object[]{
                    d.getNombreServicio(),
                    d.getNombreTipo(),
                    String.format("%.2f", d.getCantidad())
            });
        }

        JTable tablaDetalle = new JTable(modeloDetalle);
        tablaDetalle.setRowHeight(26);
        tablaDetalle.setEnabled(false);
        tablaDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaDetalle.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollDetalle = new JScrollPane(tablaDetalle);
        scrollDetalle.setPreferredSize(new Dimension(500, 200));

        JPanel panelInfo = new JPanel(new GridLayout(3, 2, 10, 5));
        panelInfo.setBorder(new EmptyBorder(0, 0, 10, 0));
        panelInfo.add(new JLabel("Pedido #:"));
        panelInfo.add(new JLabel(String.valueOf(idPedido)));
        panelInfo.add(new JLabel("Cliente:"));
        panelInfo.add(new JLabel(cliente));
        panelInfo.add(new JLabel("Estado:"));
        panelInfo.add(new JLabel(estado));

        JPanel contenido = new JPanel(new BorderLayout(0, 10));
        contenido.add(panelInfo, BorderLayout.NORTH);
        contenido.add(scrollDetalle, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(
                this,
                contenido,
                "Detalle del Pedido #" + idPedido,
                JOptionPane.PLAIN_MESSAGE
        );
    }

    // =====================================
    // ACTUALIZAR ESTADO
    // =====================================

    private void actualizarEstado() {

        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) return;

        int idPedido = (int) modelo.getValueAt(fila, 0);
        String estadoActual = modelo.getValueAt(fila, 4).toString();

        String nuevoEstado = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione el nuevo estado para el Pedido #" + idPedido + ":",
                "Actualizar Estado",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ESTADOS,
                estadoActual
        );

        if (nuevoEstado == null || nuevoEstado.equals(estadoActual)) return;

        boolean ok = pedidoService.actualizarEstado(idPedido, nuevoEstado);

        if (ok) {
            JOptionPane.showMessageDialog(this,
                    "Estado actualizado a: " + nuevoEstado,
                    "Actualización exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar el estado.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
