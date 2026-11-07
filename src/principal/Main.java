package principal;

import javax.swing.SwingUtilities;
import controlador.ControladorEmpleados;
import modelo.Persona;
import modelo.Repositorio;
import vista.VentanaPrincipal;

public final class Main {
    private Main() {
        throw new AssertionError("Punto de entrada");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Repositorio<Persona> repositorio = new Repositorio<>();
            ControladorEmpleados controlador = new ControladorEmpleados(repositorio);
            new VentanaPrincipal(controlador).setVisible(true);
        });
    }
}
