package controlador;

import java.util.List;
import java.util.Objects;
import modelo.Administrativo;
import modelo.Obrero;
import modelo.Persona;
import modelo.Repositorio;
import modelo.Tecnico;
import modelo.ValidadorEmpleado;

public class ControladorEmpleados {
    private final Repositorio<Persona> repositorio;

    public ControladorEmpleados(Repositorio<Persona> repositorio) {
        this.repositorio = Objects.requireNonNull(repositorio);
    }

    public boolean registrarAdministrativo(String nombre, String cedula, String cargo) {
        ValidadorEmpleado.validarDatos(nombre, cedula);
        return registrar(new Administrativo(nombre, cedula, cargo));
    }

    public boolean registrarObrero(String nombre, String cedula, String codigo) {
        ValidadorEmpleado.validarDatos(nombre, cedula);
        return registrar(new Obrero(nombre, cedula, codigo));
    }

    public boolean registrarTecnico(String nombre, String cedula, String codigo, String clave) {
        ValidadorEmpleado.validarDatos(nombre, cedula);
        return registrar(new Tecnico(nombre, cedula, codigo, clave));
    }

    private <E extends Persona> boolean registrar(E empleado) {
        if (!repositorio.registrar(empleado)) {
            throw new IllegalArgumentException("Ya existe un empleado con esa cédula.");
        }
        return true;
    }

    public Persona consultarPorCedula(String cedula) {
        return repositorio.buscarPorCedula(ValidadorEmpleado.validarCedula(cedula));
    }

    public List<Persona> listarEmpleados() {
        return repositorio.listar();
    }

    public int getTotalEmpleados() {
        return repositorio.getContadorRegistros();
    }
}
