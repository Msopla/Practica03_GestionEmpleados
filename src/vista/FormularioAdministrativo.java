package vista;

import controlador.ControladorEmpleados;

public class FormularioAdministrativo extends FormularioEmpleado {
    private static final long serialVersionUID = 1L;

    public FormularioAdministrativo(ControladorEmpleados controlador, Runnable alRegistrar) {
        super(controlador, alRegistrar, "Registrar administrativo", "Cargo", "Ingrese el cargo");
        agregarBotones();
    }

    @Override
    protected void registrar() {
        controlador.registrarAdministrativo(nombre.getText(), cedula.getText(), detalle.getText());
    }
}
