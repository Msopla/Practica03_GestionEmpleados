package modelo;

public class Administrativo extends Persona {
    private final String cargo;

    public Administrativo(String nombre, String cedula, String cargo) {
        super(nombre, cedula);
        this.cargo = ValidadorEmpleado.validarTexto(cargo, "El cargo");
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public String informacion() {
        return "Tipo: " + getTipo() + "\nNombre: " + getNombre() + "\nCédula: " + getCedula()
                + "\nCargo: " + cargo;
    }

    @Override
    public String getTipo() {
        return "Administrativo";
    }
}
