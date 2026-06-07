package com.megalab.ui;

import com.megalab.model.Cliente;
import com.megalab.model.DetallePedido;
import com.megalab.model.Servicio;
import com.megalab.model.TipoDetalle;
import com.megalab.service.ClienteService;
import com.megalab.service.PedidoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

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

    private JLabel lblTotal;

    private ClienteService clienteService = new ClienteService();
    private PedidoService pedidoService = new PedidoService();

    private List<DetallePedido> detallesTemp = new ArrayList<>();

    // Referencia al frame de pedidos para refrescar al guardar
    private PedidoFrame pedidoFrame;

    public PedidoFormFrame(PedidoFrame pedidoFrame) {
        this.pedidoFrame = pedidoFrame;
        initUI();
    }

    private void initUI() {

        setTitle("MegaLav System - Nuevo Pedido");
        setSize(1100, 780);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color fondo = new Color(245, 247, 250);
        Color texto = new Color(44, 62, 80);
        Color verde = new Color(46, 204, 113);
        Color rojo = new Color(231, 76, 60);
        Color azul = new Color(52, 152, 219);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(fondo);

        // ==================================
        // HEADER
        // ==================================

        JPanel header = new JPanel();
        header.setBackground(fondo);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(20, 20, 10, 20));

        JLabel titulo = new JLabel("NUEVO PEDIDO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(texto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Registro y gestión de servicios de lavandería");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(Color.GRAY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitulo);

        // ==================================
        // PANEL DATOS GENERALES
        // ==================================

        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Datos del Pedido"),
                new EmptyBorder(10, 15, 10, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cbClientes = new JComboBox<>();

        txtFechaRegistro = new JTextField();
        txtFechaRegistro.setEditable(false);
        txtFechaRegistro.setText(LocalDate.now().toString());

        txtFechaEntrega = new JTextField();
        txtFechaEntrega.setToolTipText("Formato: YYYY-MM-DD (ej: 2025-12-31)");

        txtObservaciones = new JTextArea(3, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);

        // Fila 0: Cliente
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        panelDatos.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 3;
        panelDatos.add(cbClientes, gbc);

        // Fila 1: Fechas
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panelDatos.add(new JLabel("Fecha Registro:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(txtFechaRegistro, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Fecha Entrega:"), gbc);

        gbc.gridx = 3;
        panelDatos.add(txtFechaEntrega, gbc);

        // Fila 2: Observaciones
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        panelDatos.add(new JLabel("Observaciones:"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 3;
        panelDatos.add(scrollObs, gbc);

        // ==================================
        // PANEL AGREGAR SERVICIO
        // ==================================

        JPanel panelServicio = new JPanel(new GridBagLayout());
        panelServicio.setBackground(Color.WHITE);
        panelServicio.setBorder(BorderFactory.createTitledBorder("Agregar Servicio al Pedido"));

        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(8, 10, 8, 10);
        s.fill = GridBagConstraints.HORIZONTAL;

        cbServicios = new JComboBox<>();
        cbTipos = new JComboBox<>();
        txtCantidad = new JTextField(8);

        btnAgregarDetalle = crearBoton("✚ Agregar al Pedido", verde);

        s.gridx = 0; s.gridy = 0;
        panelServicio.add(new JLabel("Servicio:"), s);

        s.gridx = 1;
        panelServicio.add(cbServicios, s);

        s.gridx = 2;
        panelServicio.add(new JLabel("Tipo:"), s);

        s.gridx = 3;
        panelServicio.add(cbTipos, s);

        s.gridx = 4;
        panelServicio.add(new JLabel("Cantidad:"), s);

        s.gridx = 5;
        panelServicio.add(txtCantidad, s);

        s.gridx = 6;
        panelServicio.add(btnAgregarDetalle, s);

        // ==================================
        // TABLA DETALLES
        // ==================================

        modeloDetalles = new DefaultTableModel(
                new String[]{"#", "Servicio", "Tipo", "Cantidad"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaDetalles = new JTable(modeloDetalles);
        tablaDetalles.setRowHeight(28);
        tablaDetalles.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaDetalles.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaDetalles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajustar ancho columna #
        tablaDetalles.getColumnModel().getColumn(0).setMaxWidth(40);

        JScrollPane scrollTabla = new JScrollPane(tablaDetalles);
        scrollTabla.setPreferredSize(new Dimension(0, 180));

        // Label total
        lblTotal = new JLabel("Total de ítems: 0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTotal.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel panelTablaConTotal = new JPanel(new BorderLayout());
        panelTablaConTotal.setBackground(fondo);
        panelTablaConTotal.add(scrollTabla, BorderLayout.CENTER);
        panelTablaConTotal.add(lblTotal, BorderLayout.SOUTH);

        // ==================================
        // BOTONES PRINCIPALES
        // ==================================

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(fondo);

        btnEliminarDetalle = crearBoton("✖ Eliminar Ítem", rojo);
        btnGuardarPedido = crearBoton("💾 Guardar Pedido", azul);
        btnCancelar = crearBoton("Cancelar", new Color(149, 165, 166));

        panelBotones.add(btnEliminarDetalle);
        panelBotones.add(btnGuardarPedido);
        panelBotones.add(btnCancelar);

        // ==================================
        // ENSAMBLADO CENTRAL
        // ==================================

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBackground(fondo);
        centro.setBorder(new EmptyBorder(15, 20, 10, 20));

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBackground(fondo);
        panelSuperior.add(panelDatos, BorderLayout.NORTH);
        panelSuperior.add(panelServicio, BorderLayout.SOUTH);

        centro.add(panelSuperior, BorderLayout.NORTH);
        centro.add(panelTablaConTotal, BorderLayout.CENTER);
        centro.add(panelBotones, BorderLayout.SOUTH);

        principal.add(header, BorderLayout.NORTH);
        principal.add(centro, BorderLayout.CENTER);

        add(principal);

        // ==================================
        // CARGA INICIAL
        // ==================================

        cargarClientes();
        cargarServicios();
        cargarTipos();

        // ==================================
        // EVENTOS
        // ==================================

        cbServicios.addActionListener(e -> cargarTipos());

        btnAgregarDetalle.addActionListener(e -> agregarDetalle());

        btnEliminarDetalle.addActionListener(e -> eliminarDetalle());

        btnGuardarPedido.addActionListener(e -> guardarPedido());

        btnCancelar.addActionListener(e -> dispose());
    }

    // ==================================
    // LÓGICA
    // ==================================

    private void agregarDetalle() {

        Servicio servicio = (Servicio) cbServicios.getSelectedItem();
        TipoDetalle tipo = (TipoDetalle) cbTipos.getSelectedItem();

        if (servicio == null || tipo == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un servicio y un tipo.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cantidadStr = txtCantidad.getText().trim();

        if (cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese la cantidad.",
                    "Dato faltante",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double cantidad;
        try {
            cantidad = Double.parseDouble(cantidadStr);
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "La cantidad debe ser un número mayor a 0.",
                    "Valor inválido",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DetallePedido detalle = new DetallePedido(
                servicio.getIdServicio(),
                tipo.getIdTipo(),
                cantidad,
                servicio.getNombre(),
                tipo.getNombre()
        );

        detallesTemp.add(detalle);

        int fila = detallesTemp.size();
        modeloDetalles.addRow(new Object[]{
                fila,
                servicio.getNombre(),
                tipo.getNombre(),
                String.format("%.2f", cantidad)
        });

        actualizarTotal();
        txtCantidad.setText("");
        txtCantidad.requestFocus();
    }

    private void eliminarDetalle() {

        int fila = tablaDetalles.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un ítem de la tabla para eliminar.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        detallesTemp.remove(fila);
        modeloDetalles.removeRow(fila);

        // Renumerar columna #
        for (int i = 0; i < modeloDetalles.getRowCount(); i++) {
            modeloDetalles.setValueAt(i + 1, i, 0);
        }

        actualizarTotal();
    }

    private void guardarPedido() {

        if (cbClientes.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    "No hay clientes disponibles. Registre un cliente primero.",
                    "Sin clientes",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (detallesTemp.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe agregar al menos un servicio al pedido.",
                    "Pedido vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fechaEntregaStr = txtFechaEntrega.getText().trim();

        if (fechaEntregaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese la fecha de entrega (YYYY-MM-DD).",
                    "Fecha faltante",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date fechaEntrega;
        try {
            LocalDate localDate = LocalDate.parse(
                    fechaEntregaStr,
                    DateTimeFormatter.ISO_LOCAL_DATE
            );

            if (localDate.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this,
                        "La fecha de entrega no puede ser anterior a hoy.",
                        "Fecha inválida",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            fechaEntrega = Date.valueOf(localDate);

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido. Use YYYY-MM-DD (ej: 2025-12-31).",
                    "Fecha incorrecta",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = (Cliente) cbClientes.getSelectedItem();
        String observaciones = txtObservaciones.getText().trim();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Confirma guardar el pedido para " + cliente + "?",
                "Confirmar guardado",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = pedidoService.guardarPedido(
                cliente.getIdCliente(),
                fechaEntrega,
                observaciones,
                detallesTemp
        );

        if (ok) {
            JOptionPane.showMessageDialog(this,
                    "¡Pedido guardado correctamente!",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            if (pedidoFrame != null) {
                pedidoFrame.cargarDatos();
            }

            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el pedido. Intente nuevamente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTotal() {
        lblTotal.setText("Total de ítems: " + detallesTemp.size());
    }

    // ==================================
    // CARGA DE DATOS
    // ==================================

    private void cargarClientes() {
        cbClientes.removeAllItems();
        for (Cliente c : clienteService.obtenerClientes()) {
            cbClientes.addItem(c);
        }
    }

    private void cargarServicios() {
        cbServicios.removeAllItems();
        for (Servicio s : pedidoService.obtenerServicios()) {
            cbServicios.addItem(s);
        }
    }

    private void cargarTipos() {
        cbTipos.removeAllItems();
        Servicio servicio = (Servicio) cbServicios.getSelectedItem();
        if (servicio == null) return;

        for (TipoDetalle t : pedidoService.obtenerTiposPorServicio(servicio.getIdServicio())) {
            cbTipos.addItem(t);
        }
    }

    // ==================================
    // UTILIDADES UI
    // ==================================

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return boton;
    }
}
