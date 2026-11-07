package modelo;

public class Tecnico extends Obrero {
    private final String claveAcceso;

    public Tecnico(String nombre, String cedula, String codigo, String claveAcceso) {
        super(nombre, cedula, codigo);
        ValidadorEmpleado.validarTexto(claveAcceso, "La clave de acceso");
        this.claveAcceso = claveAcceso;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    @Override
    public String informacion() {
        return super.informacion();
    }

    @Override
    public String getTipo() {
        return "Técnico";
    }
}
