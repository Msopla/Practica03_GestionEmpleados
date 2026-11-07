package modelo;

public abstract class Persona {
    private final String nombre;
    private final String cedula;

    protected Persona(String nombre, String cedula) {
        this.nombre = ValidadorEmpleado.validarTexto(nombre, "El nombre");
        this.cedula = ValidadorEmpleado.validarCedula(cedula);
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public abstract String informacion();

    public abstract String getTipo();
}
