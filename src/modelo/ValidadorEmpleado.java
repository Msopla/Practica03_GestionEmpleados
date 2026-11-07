package modelo;

public final class ValidadorEmpleado {
    private ValidadorEmpleado() {
        throw new AssertionError("Clase de utilidades");
    }

    public static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " es obligatorio.");
        }
        return valor.strip();
    }

    public static String validarCedula(String cedula) {
        String valor = validarTexto(cedula, "La cédula");
        if (!valor.matches("[0-9]{10}")) {
            throw new IllegalArgumentException("La cédula debe contener exactamente 10 dígitos numéricos.");
        }
        return valor;
    }

    public static void validarDatos(String nombre, String cedula) {
        validarTexto(nombre, "El nombre");
        validarCedula(cedula);
    }
}
