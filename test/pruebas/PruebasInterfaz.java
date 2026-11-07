package pruebas;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import controlador.ControladorEmpleados;
import modelo.Persona;
import modelo.Repositorio;
import vista.VentanaPrincipal;

public final class PruebasInterfaz {
    private static int comprobaciones;

    private PruebasInterfaz() {
        throw new AssertionError("Clase de pruebas");
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(PruebasInterfaz::ejecutar);
        System.out.println("OK: " + comprobaciones + " comprobaciones de interfaz Swing.");
    }

    private static void ejecutar() {
        ControladorEmpleados controlador = new ControladorEmpleados(new Repositorio<Persona>());
        VentanaPrincipal ventana = new VentanaPrincipal(controlador);
        Timer cerrarMensajes = new Timer(30, e -> {
            for (Window ventanaAbierta : Window.getWindows()) {
                if (ventanaAbierta instanceof JDialog && ventanaAbierta.isVisible()) {
                    ventanaAbierta.dispose();
                }
            }
        });
        try {
            ventana.setVisible(true);
            cerrarMensajes.start();
            pulsar(ventana, "Administrativos");
            llenar(ventana, "Ana Prueba", "0012345678", "Secretaria");
            pulsar(ventana, "Guardar registro");
            verificar(controlador.getTotalEmpleados() == 1, "Registro desde formulario administrativo");
            verificar(visibles(ventana, JTextField.class).stream().allMatch(c -> c.getText().isEmpty()), "Limpieza después de guardar");
            pulsar(ventana, "Obreros");
            llenar(ventana, "Luis Prueba", "0123456789", "OB-01");
            pulsar(ventana, "Guardar registro");
            verificar(controlador.getTotalEmpleados() == 2, "Registro desde formulario obrero");
            pulsar(ventana, "Técnicos");
            llenar(ventana, "Eva Prueba", "0234567890", "TEC-01", "clave-solo-prueba");
            verificar(visibles(ventana, JPasswordField.class).get(0).getEchoChar() != 0, "Clave enmascarada");
            pulsar(ventana, "Guardar registro");
            verificar(controlador.getTotalEmpleados() == 3, "Registro desde formulario técnico");
            pulsar(ventana, "Listar");
            JTable tabla = visibles(ventana, JTable.class).get(0);
            verificar(tabla.getRowCount() == 3 && tabla.getColumnCount() == 4, "Tres tipos en la misma tabla");
            verificar(tabla.getValueAt(2, 0).equals("Técnico"), "Tipo técnico en tabla");
            verificar(tabla.getValueAt(0, 2).equals("0012345678"), "Ceros iniciales en tabla");
            verificar(!tabla.isCellEditable(0, 0), "Tabla no editable");
            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
                for (int columna = 0; columna < tabla.getColumnCount(); columna++) {
                    verificar(!tabla.getValueAt(fila, columna).toString().contains("clave-solo-prueba"), "Clave ausente en tabla");
                }
            }
            verificar(visibles(ventana, JLabel.class).stream()
                    .anyMatch(e -> e.getText().equals("Total de empleados: 3")), "Contador visible");
            pulsar(ventana, "Actualizar");
            verificar(tabla.getRowCount() == 3, "Actualizar no duplica filas");
            pulsar(ventana, "Consultar");
            llenar(ventana, "0234567890");
            pulsar(ventana, "Buscar");
            JTextArea resultado = visibles(ventana, JTextArea.class).get(0);
            verificar(resultado.getText().contains("Eva Prueba") && resultado.getText().contains("TEC-01"), "Consulta desde interfaz");
            verificar(!resultado.getText().contains("clave-solo-prueba"), "Consulta sin clave");
            llenar(ventana, "9999999999");
            pulsar(ventana, "Buscar");
            verificar(!resultado.getText().contains("Eva Prueba"), "Consulta inexistente limpia resultado previo");
            llenar(ventana, "ABC");
            pulsar(ventana, "Buscar");
            verificar(!resultado.getText().contains("Eva Prueba"), "Consulta inválida sin datos previos");
            pulsar(ventana, "Administrativos");
            llenar(ventana, "Duplicado", "0123456789", "Cargo");
            pulsar(ventana, "Guardar registro");
            verificar(controlador.getTotalEmpleados() == 3, "Rechazo de duplicado desde interfaz");
            pulsar(ventana, "Limpiar");
            verificar(visibles(ventana, JTextField.class).stream().allMatch(c -> c.getText().isEmpty()), "Botón Limpiar");
            pulsar(ventana, "Guardar registro");
            verificar(controlador.getTotalEmpleados() == 3, "Rechazo de campos vacíos");
            llenar(ventana, "Nombre", "123", "Cargo");
            pulsar(ventana, "Guardar registro");
            verificar(controlador.getTotalEmpleados() == 3, "Rechazo de cédula inválida");
            for (String seccion : new String[] { "Inicio", "Obreros", "Técnicos", "Consultar", "Listar" }) {
                pulsar(ventana, seccion);
            }
            verificar(tabla.getRowCount() == 3 && controlador.getTotalEmpleados() == 3, "Registros conservados al navegar");
        } finally {
            cerrarMensajes.stop();
            ventana.dispose();
        }
    }

    private static void llenar(Container raiz, String... valores) {
        List<JTextField> campos = visibles(raiz, JTextField.class);
        verificar(campos.size() == valores.length, "Campos del formulario");
        for (int i = 0; i < valores.length; i++) {
            campos.get(i).setText(valores[i]);
        }
    }

    private static void pulsar(Container raiz, String texto) {
        JButton boton = visibles(raiz, JButton.class).stream().filter(b -> b.getText().equals(texto))
                .findFirst().orElseThrow(() -> new AssertionError("Botón no visible: " + texto));
        boton.doClick();
        raiz.validate();
    }

    private static <T extends Component> List<T> visibles(Container raiz, Class<T> tipo) {
        List<T> encontrados = new ArrayList<>();
        for (Component componente : raiz.getComponents()) {
            if (tipo.isInstance(componente) && componente.isShowing()) {
                encontrados.add(tipo.cast(componente));
            }
            if (componente instanceof Container contenedor) {
                encontrados.addAll(visibles(contenedor, tipo));
            }
        }
        return encontrados;
    }

    private static void verificar(boolean condicion, String caso) {
        if (!condicion) {
            throw new AssertionError(caso);
        }
        comprobaciones++;
    }
}
