package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controlador.ControladorEmpleados;

abstract class FormularioEmpleado extends JPanel {
    private static final long serialVersionUID = 1L;
    protected final ControladorEmpleados controlador;
    protected final JTextField nombre = new JTextField();
    protected final JTextField cedula = new JTextField();
    protected final JTextField detalle = new JTextField();
    private final List<JTextField> campos = new ArrayList<>();
    private final JPanel contenido = new JPanel(new GridBagLayout());
    private final Runnable alRegistrar;
    private int fila;

    protected FormularioEmpleado(ControladorEmpleados controlador, Runnable alRegistrar, String titulo,
            String etiquetaDetalle, String ayudaDetalle) {
        this.controlador = controlador;
        this.alRegistrar = alRegistrar;
        setLayout(new BorderLayout());
        JPanel pagina = EstiloVista.pagina();
        pagina.add(EstiloVista.titulo(titulo), BorderLayout.NORTH);
        contenido.setOpaque(false);
        JPanel superior = new JPanel(new BorderLayout());
        superior.setOpaque(false);
        superior.add(contenido, BorderLayout.NORTH);
        pagina.add(superior, BorderLayout.CENTER);
        add(pagina);
        agregarCampo("Nombre completo", nombre, "Ingrese nombre y apellidos");
        agregarCampo("Cédula", cedula, "10 dígitos numéricos");
        agregarCampo(etiquetaDetalle, detalle, ayudaDetalle);
    }

    protected final void agregarCampo(String etiqueta, JTextField campo, String ayuda) {
        campos.add(campo);
        EstiloVista.agregarFila(contenido, EstiloVista.campo(etiqueta, campo, ayuda), fila++);
    }

    protected final void agregarBotones() {
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        acciones.setOpaque(false);
        JButton guardar = EstiloVista.boton("Guardar registro", true);
        JButton limpiar = EstiloVista.boton("Limpiar", false);
        guardar.addActionListener(e -> guardar());
        limpiar.addActionListener(e -> limpiar());
        acciones.add(guardar);
        acciones.add(javax.swing.Box.createHorizontalStrut(12));
        acciones.add(limpiar);
        EstiloVista.agregarFila(contenido, acciones, fila++);
        EstiloVista.agregarFila(contenido, new JLabel("* Campos obligatorios  |  Valida duplicados"), fila++);
    }

    private void guardar() {
        try {
            registrar();
            alRegistrar.run();
            limpiar();
            MensajesValidacion.mostrarExito(this, "Empleado registrado correctamente.");
        } catch (IllegalArgumentException ex) {
            MensajesValidacion.mostrarError(this, ex.getMessage());
        }
    }

    private void limpiar() {
        for (JTextField campo : campos) {
            campo.setText("");
        }
        nombre.requestFocusInWindow();
    }

    protected abstract void registrar();
}
