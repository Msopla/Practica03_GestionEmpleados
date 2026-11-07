package vista;

import controlador.ControladorEmpleados;

public class FormularioObrero extends FormularioEmpleado {
    private static final long serialVersionUID = 1L;

    public FormularioObrero(ControladorEmpleados controlador, Runnable alRegistrar) {
        super(controlador, alRegistrar, "Registrar obrero", "Código de empleado", "Ingrese el código");
        agregarBotones();
    }

    @Override
    protected void registrar() {
        controlador.registrarObrero(nombre.getText(), cedula.getText(), detalle.getText());
    }
}
