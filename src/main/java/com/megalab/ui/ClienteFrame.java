package com.megalab.ui;

import com.megalab.service.ClienteService;
import com.megalab.model.Cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClienteFrame extends JFrame {

    // Manejo con controlador
    private ClienteService service;

    private int idClienteSeleccionado = -1;

    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtCedula;
    private JTextField txtTelefono;
    private JTextField txtBuscar;

    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnVolver;

    private JTable tablaClientes;
    private DefaultTableModel modelo;

    public ClienteFrame() {

        service = new ClienteService();

        setTitle("MegaLav System - Gestión de Clientes");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color fondo = new Color(245, 247, 250);
        Color azul = new Color(33, 150, 243);
        Color texto = new Color(44, 62, 80);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(fondo);

        // ==========================
        // HEADER
        // ==========================

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(fondo);
        header.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("GESTIÓN DE CLIENTES");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(texto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "Registrar, consultar y administrar clientes"
        );

        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(Color.GRAY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(10));
        header.add(subtitulo);

        // ==========================
        // PANEL IZQUIERDO
        // ==========================

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(Color.WHITE);
        panelIzquierdo.setBorder(new EmptyBorder(25,25,25,25));

        JLabel lblFormulario = new JLabel("DATOS DEL CLIENTE");
        lblFormulario.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblFormulario.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelIzquierdo.add(lblFormulario);
        panelIzquierdo.add(Box.createVerticalStrut(20));

        txtNombres = new JTextField();
        txtApellidos = new JTextField();
        txtCedula = new JTextField();
        txtTelefono = new JTextField();

        txtNombres.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
        txtApellidos.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
        txtCedula.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
        txtTelefono.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));

        panelIzquierdo.add(new JLabel("Nombres"));
        panelIzquierdo.add(Box.createVerticalStrut(5));
        panelIzquierdo.add(txtNombres);

        panelIzquierdo.add(Box.createVerticalStrut(15));

        panelIzquierdo.add(new JLabel("Apellidos"));
        panelIzquierdo.add(Box.createVerticalStrut(5));
        panelIzquierdo.add(txtApellidos);

        panelIzquierdo.add(Box.createVerticalStrut(15));

        panelIzquierdo.add(new JLabel("Cédula"));
        panelIzquierdo.add(Box.createVerticalStrut(5));
        panelIzquierdo.add(txtCedula);

        panelIzquierdo.add(Box.createVerticalStrut(15));

        panelIzquierdo.add(new JLabel("Teléfono"));
        panelIzquierdo.add(Box.createVerticalStrut(5));
        panelIzquierdo.add(txtTelefono);

        panelIzquierdo.add(Box.createVerticalStrut(25));

        // BOTONES

        // boton agregar
        btnAgregar = crearBoton(
                "Agregar",
                new Color(46,204,113)
        );


        btnAgregar.addActionListener(e -> {

            if (!validarCampos()) return;

            boolean ok = service.agregarCliente(
                    txtCedula.getText().trim(),
                    txtNombres.getText().trim(),
                    txtApellidos.getText().trim(),
                    txtTelefono.getText().trim()
            );

            if (!ok) {

                JOptionPane.showMessageDialog(
                        this,
                        "La cédula ya está registrada"
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente agregado correctamente"
            );

            limpiarCampos();
            cargarDatos();

        });

        // boton editar
        btnEditar = crearBoton(
                "Editar",
                new Color(52,152,219)
        );

        btnEditar.setEnabled(false);

        btnEditar.addActionListener(e -> {

            if (idClienteSeleccionado == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un cliente"
                );

                return;
            }

            if (!validarCampos()) return;

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea actualizar este cliente?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION)
                return;

            boolean ok = service.actualizarCliente(
                    idClienteSeleccionado,
                    txtCedula.getText().trim(),
                    txtNombres.getText().trim(),
                    txtApellidos.getText().trim(),
                    txtTelefono.getText().trim()
            );

            if (!ok) {

                JOptionPane.showMessageDialog(
                        this,
                        "La cédula ya pertenece a otro cliente"
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente actualizado correctamente"
            );

            limpiarCampos();
            cargarDatos();

        });

        // boton eliminar
        btnEliminar = crearBoton(
                "Eliminar",
                new Color(231,76,60)
        );

        btnEliminar.setEnabled(false);

        btnEliminar.addActionListener(e -> {

            if (idClienteSeleccionado == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un cliente"
                );

                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea eliminar este cliente?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION)
                return;

            service.eliminarCliente(idClienteSeleccionado);

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente eliminado correctamente"
            );

            limpiarCampos();
            cargarDatos();

        });

        // boton limpiar
        btnLimpiar = crearBoton(
                "Limpiar",
                new Color(149,165,166)
        );

        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Botón volver
        btnVolver = crearBoton(
                "Volver",
                new Color(52,73,94)
        );
        btnVolver.addActionListener(e -> {

            dispose();

            new MainFrame().setVisible(true);

        });

        panelIzquierdo.add(btnAgregar);
        panelIzquierdo.add(Box.createVerticalStrut(10));

        panelIzquierdo.add(btnEditar);
        panelIzquierdo.add(Box.createVerticalStrut(10));

        panelIzquierdo.add(btnEliminar);
        panelIzquierdo.add(Box.createVerticalStrut(10));

        panelIzquierdo.add(btnLimpiar);
        panelIzquierdo.add(Box.createVerticalStrut(10));

        panelIzquierdo.add(btnVolver);

        // ==========================
        // PANEL DERECHO
        // ==========================

        JPanel panelDerecho = new JPanel(new BorderLayout(10,10));
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setBorder(new EmptyBorder(20,20,20,20));

        JLabel lblTabla = new JLabel("CLIENTES REGISTRADOS");
        lblTabla.setFont(new Font("Segoe UI", Font.BOLD, 18));

        panelDerecho.add(lblTabla, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Cédula",
                        "Nombres",
                        "Apellidos",
                        "Teléfono"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaClientes = new JTable(modelo);

        tablaClientes.setRowHeight(30);
        tablaClientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Deshabilitar botones editar y eliminar cuando no se seleccione ningun registro
        tablaClientes.getSelectionModel()
                .addListSelectionListener(e -> {

                    boolean seleccionado =
                            tablaClientes.getSelectedRow() != -1;

                    btnEditar.setEnabled(seleccionado);
                    btnEliminar.setEnabled(seleccionado);
                });

        JScrollPane scroll = new JScrollPane(tablaClientes);

        panelDerecho.add(scroll, BorderLayout.CENTER);

        // Seleccionar fila
        tablaClientes.getSelectionModel()
                .addListSelectionListener(e -> {

                    int fila = tablaClientes.getSelectedRow();

                    if (fila != -1) {

                        idClienteSeleccionado =
                                Integer.parseInt(
                                        modelo.getValueAt(fila,0).toString()
                                );

                        txtCedula.setText(
                                modelo.getValueAt(fila,1).toString()
                        );

                        txtNombres.setText(
                                modelo.getValueAt(fila,2).toString()
                        );

                        txtApellidos.setText(
                                modelo.getValueAt(fila,3).toString()
                        );

                        txtTelefono.setText(
                                modelo.getValueAt(fila,4).toString()
                        );

                        btnAgregar.setEnabled(false);

                    }

                });

        // ==========================
        // SPLIT PRINCIPAL
        // ==========================

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                panelIzquierdo,
                panelDerecho
        );

        split.setDividerLocation(320);
        split.setBorder(null);

        principal.add(header, BorderLayout.NORTH);
        principal.add(split, BorderLayout.CENTER);

        add(principal);

        cargarDatos();
    }

    private JButton crearBoton(String texto, Color color) {

        JButton boton = new JButton(texto);

        boton.setBackground(color);
        boton.setForeground(Color.WHITE);

        boton.setFocusPainted(false);

        boton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        boton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        return boton;
    }

    private void cargarDatos() {

        modelo.setRowCount(0);

        for (Cliente cliente : service.obtenerClientes()) {

            modelo.addRow(new Object[]{
                    cliente.getIdCliente(),
                    cliente.getCedula(),
                    cliente.getNombres(),
                    cliente.getApellidos(),
                    cliente.getTelefono()
            });

        }

    }

    private void limpiarCampos() {

        txtNombres.setText("");
        txtApellidos.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");

        idClienteSeleccionado = -1;

        tablaClientes.clearSelection();

        btnAgregar.setEnabled(true);

    }

    private boolean validarCampos() {

        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String cedula = txtCedula.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (
                nombres.isEmpty() ||
                        apellidos.isEmpty() ||
                        cedula.isEmpty() ||
                        telefono.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Todos los campos son obligatorios"
            );

            return false;
        }

        if (!cedula.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "La cédula debe tener 10 dígitos"
            );

            return false;
        }

        if (!telefono.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "El teléfono debe tener 10 dígitos"
            );

            return false;
        }

        return true;
    }


}