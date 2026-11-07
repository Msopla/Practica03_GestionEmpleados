package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import controlador.ControladorEmpleados;
import modelo.Persona;

public class VentanaConsulta extends JPanel {
    private static final long serialVersionUID = 1L;
    private final ControladorEmpleados controlador;
    private final JTextField cedula = new JTextField();
    private final JTextArea resultado = new JTextArea(7, 25);

    public VentanaConsulta(ControladorEmpleados controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());
        JPanel pagina = EstiloVista.pagina();
        pagina.add(EstiloVista.titulo("Consulta por cédula"), BorderLayout.NORTH);
        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setOpaque(false);
        EstiloVista.agregarFila(contenido, EstiloVista.campo("Cédula del empleado", cedula,
                "Ingrese 10 dígitos"), 0);
        JButton buscar = EstiloVista.boton("Buscar", true);
        buscar.addActionListener(e -> consultar());
        cedula.addActionListener(e -> consultar());
        JPanel acciones = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        acciones.setOpaque(false);
        acciones.add(buscar);
        EstiloVista.agregarFila(contenido, acciones, 1);
        JPanel tarjeta = new JPanel(new BorderLayout(0, 18));
        tarjeta.setBackground(EstiloVista.CLARO);
        tarjeta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titulo = new JLabel("Resultado de la consulta");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(EstiloVista.AZUL);
        tarjeta.add(titulo, BorderLayout.NORTH);
        resultado.setEditable(false);
        resultado.setLineWrap(true);
        resultado.setWrapStyleWord(true);
        resultado.setFont(new Font("SansSerif", Font.PLAIN, 16));
        resultado.setForeground(EstiloVista.TEXTO);
        resultado.setBackground(EstiloVista.CLARO);
        resultado.getAccessibleContext().setAccessibleName("Resultado de la consulta");
        JScrollPane scroll = new JScrollPane(resultado);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        tarjeta.add(scroll, BorderLayout.CENTER);
        EstiloVista.agregarFila(contenido, tarjeta, 2);
        EstiloVista.agregarFila(contenido, new JLabel("La clave del técnico no se muestra."), 3);
        JPanel superior = new JPanel(new BorderLayout());
        superior.setOpaque(false);
        superior.add(contenido, BorderLayout.NORTH);
        pagina.add(superior, BorderLayout.CENTER);
        add(pagina);
        limpiarResultado();
    }

    private void limpiarResultado() {
        resultado.setText("Tipo: —\n\nNombre: —\n\nCédula: —\n\nCargo / Código: —");
        resultado.setCaretPosition(0);
    }

    private void consultar() {
        limpiarResultado();
        try {
            Persona empleado = controlador.consultarPorCedula(cedula.getText());
            if (empleado == null) {
                MensajesValidacion.mostrarInformacion(this, "No se encontró un empleado con esa cédula.");
                return;
            }
            resultado.setText(empleado.informacion().replace("\n", "\n\n"));
            resultado.setCaretPosition(0);
        } catch (IllegalArgumentException ex) {
            MensajesValidacion.mostrarError(this, ex.getMessage());
        }
    }
}
