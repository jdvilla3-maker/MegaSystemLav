package com.megalab.ui;

import com.megalab.model.Cliente;
import com.megalab.service.ClienteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClienteFrame extends JFrame {

    private ClienteService service = new ClienteService();
    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtNombre, txtCedula, txtTelefono;

    private JButton btnAgregar;

    public ClienteFrame() {
        setTitle("Gestión de Clientes");
        setSize(600, 400);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // FORMULARIO
        JPanel form = new JPanel(new GridLayout(4,2));

        txtNombre = new JTextField();
        txtCedula = new JTextField();
        txtTelefono = new JTextField();

        form.add(new JLabel("Nombre"));
        form.add(txtNombre);
        form.add(new JLabel("Cédula"));
        form.add(txtCedula);
        form.add(new JLabel("Teléfono"));
        form.add(txtTelefono);

        btnAgregar = new JButton("Agregar");
        form.add(btnAgregar);

        add(form, BorderLayout.NORTH);

        // TABLA
        modelo = new DefaultTableModel(
                new String[]{"_id", "Nombre", "Cédula", "Teléfono"}, 0
        );
        tabla = new JTable(modelo) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // bloquea edición
            }
        };
        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.getColumnModel().getColumn(0).setWidth(0);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // BOTONES
        JPanel panelBotones = new JPanel();

        JButton btnEliminar = new JButton("Eliminar");
        JButton btnEditar = new JButton("Editar");

        JButton btnLimpiar = new JButton("Limpiar");


        panelBotones.add(btnEliminar);
        panelBotones.add(btnEditar);

        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.SOUTH);

        cargarDatos();

        // SELECCIONAR FILA → CARGAR DATOS
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila != -1) {
                txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                txtCedula.setText(modelo.getValueAt(fila, 2).toString());
                txtTelefono.setText(modelo.getValueAt(fila, 3).toString());

                btnAgregar.setEnabled(false); // desactiva agregar
            }
        });

        // EVENTOS

        btnAgregar.addActionListener(e -> {
            if (!validarCampos()) return;

            boolean ok = service.agregarCliente(
                    txtNombre.getText(),
                    txtCedula.getText(),
                    txtTelefono.getText()
            );

            if (!ok) {
                JOptionPane.showMessageDialog(null, "La cédula ya está registrada");
                return;
            }

            JOptionPane.showMessageDialog(null, "Cliente agregado correctamente");
            limpiarCampos();
            cargarDatos();
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona un cliente");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "¿Estás seguro de eliminar este cliente?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                String id = modelo.getValueAt(fila, 0).toString();
                service.eliminarCliente(id);

                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");

                limpiarCampos();
                cargarDatos();
            }
        });


        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona un cliente");
                return;
            }

            if (!validarCampos()) return;

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "¿Estás seguro de editar este cliente?",
                    "Confirmar edición",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {

                // AQUÍ defines el id
                String id = modelo.getValueAt(fila, 0).toString();

                boolean ok = service.actualizarCliente(
                        id,
                        txtNombre.getText(),
                        txtCedula.getText(),
                        txtTelefono.getText()
                );

                if (!ok) {
                    JOptionPane.showMessageDialog(null, "La cédula ya pertenece a otro cliente");
                    return;
                }

                JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente");

                limpiarCampos();
                cargarDatos();
            }
        });

        btnLimpiar.addActionListener(e -> {
            tabla.clearSelection();  // quita selección
            limpiarCampos();         // limpia inputs
        });

    }



    private void cargarDatos() {
        modelo.setRowCount(0);

        for (Cliente c : service.obtenerClientes()) {
            modelo.addRow(new Object[]{
                    c.getId(),
                    c.getNombre(),
                    c.getCedula(),
                    c.getTelefono()
            });
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
        tabla.clearSelection();

        btnAgregar.setEnabled(true);
    }

    private boolean validarCampos() {
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        String telefono = txtTelefono.getText().trim();

        // Campos vacíos
        if (nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return false;
        }

        // Cédula: solo números y 10 dígitos
        if (!cedula.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "La cédula debe tener 10 dígitos numéricos");
            return false;
        }

        // Teléfono: 10 dígitos (Ecuador)
        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "El teléfono debe tener 10 dígitos");
            return false;
        }

        return true;
    }
}