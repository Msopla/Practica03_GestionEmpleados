package modelo;

public class Obrero extends Persona {
    protected final String codigo;

    public Obrero(String nombre, String cedula, String codigo) {
        super(nombre, cedula);
        this.codigo = ValidadorEmpleado.validarTexto(codigo, "El código de empleado");
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String informacion() {
        return "Tipo: " + getTipo() + "\nNombre: " + getNombre() + "\nCédula: " + getCedula()
                + "\nCódigo: " + codigo;
    }

    @Override
    public String getTipo() {
        return "Obrero";
    }
}
