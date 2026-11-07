package vista;

import java.util.Arrays;
import javax.swing.JPasswordField;
import controlador.ControladorEmpleados;

public class FormularioTecnico extends FormularioEmpleado {
    private static final long serialVersionUID = 1L;
    private final JPasswordField clave = new JPasswordField();

    public FormularioTecnico(ControladorEmpleados controlador, Runnable alRegistrar) {
        super(controlador, alRegistrar, "Registrar técnico", "Código de empleado", "Ingrese el código");
        agregarCampo("Clave de acceso", clave, "Obligatoria; no se muestra en consultas ni listados");
        agregarBotones();
    }

    @Override
    protected void registrar() {
        char[] caracteres = clave.getPassword();
        try {
            controlador.registrarTecnico(nombre.getText(), cedula.getText(), detalle.getText(),
                    new String(caracteres));
        } finally {
            Arrays.fill(caracteres, '\0');
            clave.setText("");
        }
    }
}
