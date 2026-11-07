package modelo;

import java.util.ArrayList;
import java.util.List;

public class Repositorio<T extends Persona> {
    private final ArrayList<T> empleados = new ArrayList<>();
    private Integer contadorRegistros = 0;

    public <E extends T> boolean registrar(E empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado es obligatorio.");
        }
        if (existeCedula(empleado.getCedula())) {
            return false;
        }
        empleados.add(empleado);
        int totalActual = contadorRegistros;
        contadorRegistros = totalActual + 1;
        return true;
    }

    public T buscarPorCedula(String cedula) {
        for (T empleado : empleados) {
            if (empleado.getCedula().equals(cedula)) {
                return empleado;
            }
        }
        return null;
    }

    public List<T> listar() {
        return List.copyOf(empleados);
    }

    public boolean existeCedula(String cedula) {
        return buscarPorCedula(cedula) != null;
    }

    public Integer getContadorRegistros() {
        return contadorRegistros;
    }
}
