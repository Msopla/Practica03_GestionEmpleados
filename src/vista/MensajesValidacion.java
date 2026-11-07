package vista;

import java.awt.Component;
import javax.swing.JOptionPane;

public final class MensajesValidacion {
    private MensajesValidacion() {
        throw new AssertionError("Clase de utilidades");
    }

    public static void mostrarExito(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Registro guardado", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void mostrarError(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Revise los datos", JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarInformacion(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Consulta de empleados", JOptionPane.INFORMATION_MESSAGE);
    }
}
