package pruebas;

import java.util.List;
import controlador.ControladorEmpleados;
import modelo.Administrativo;
import modelo.Obrero;
import modelo.Persona;
import modelo.Repositorio;
import modelo.Tecnico;

public final class PruebasLogica {
    private static int comprobaciones;

    private PruebasLogica() {
        throw new AssertionError("Clase de pruebas");
    }

    public static void main(String[] args) {
        Repositorio<Persona> repositorio = new Repositorio<>();
        ControladorEmpleados controlador = new ControladorEmpleados(repositorio);
        verificar(controlador.getTotalEmpleados() == 0, "Repositorio inicialmente vacío");
        verificar(controlador.registrarAdministrativo(" Ana Pérez ", "0012345678", " Secretaria "), "Registrar administrativo");
        verificar(controlador.registrarObrero("Luis Mora", "0123456789", "OB-01"), "Registrar obrero");
        verificar(controlador.registrarTecnico("Eva Sol", "0234567890", "TEC-01", " clave-prueba "), "Registrar técnico");
        Persona administrativo = controlador.consultarPorCedula("0012345678");
        Persona obrero = controlador.consultarPorCedula("0123456789");
        Persona tecnico = controlador.consultarPorCedula("0234567890");
        verificar(administrativo instanceof Administrativo, "Consulta administrativo");
        verificar(obrero instanceof Obrero && !(obrero instanceof Tecnico), "Consulta obrero");
        verificar(tecnico instanceof Tecnico && tecnico instanceof Obrero, "Herencia técnico-obrero");
        verificar(administrativo.getNombre().equals("Ana Pérez"), "Normalización del nombre");
        verificar(((Administrativo) administrativo).getCargo().equals("Secretaria"), "Normalización del cargo");
        verificar(administrativo.getCedula().equals("0012345678"), "Conservar ceros iniciales");
        verificar(controlador.consultarPorCedula(" 0012345678 ") == administrativo, "Consulta con espacios exteriores");
        verificar(controlador.consultarPorCedula("9999999999") == null, "Consulta inexistente");
        verificar(controlador.listarEmpleados().size() == 3, "Listado heterogéneo");
        verificar(controlador.getTotalEmpleados() == 3 && repositorio.getContadorRegistros() == 3, "Contador Integer");
        verificar(((Tecnico) tecnico).getClaveAcceso().equals(" clave-prueba "), "Conservar clave exacta");
        verificar(!tecnico.informacion().contains("clave-prueba"), "Información sin clave");
        verificar(tecnico.informacion().contains("Técnico") && tecnico.informacion().contains("TEC-01"), "Información polimórfica");
        rechazar(() -> controlador.registrarObrero("Otro", "0012345678", "OB-02"), "Duplicado entre tipos");
        rechazar(() -> controlador.registrarAdministrativo("Otro", "0012345678", "Cargo"), "Duplicado mismo tipo");
        for (String invalida : new String[] { "", "  ", "123", "12345678901", "012345678a", "-123456789", "１２３４５６７８９０" }) {
            rechazar(() -> controlador.registrarObrero("Empleado", invalida, "COD"), "Cédula inválida");
            rechazar(() -> controlador.consultarPorCedula(invalida), "Consulta inválida");
        }
        rechazar(() -> controlador.registrarAdministrativo(" ", "1111111111", "Cargo"), "Nombre vacío");
        rechazar(() -> controlador.registrarAdministrativo(null, "1111111111", "Cargo"), "Nombre nulo");
        rechazar(() -> controlador.registrarAdministrativo("Nombre", null, "Cargo"), "Cédula nula");
        rechazar(() -> controlador.registrarAdministrativo("Nombre", "1111111111", "\t"), "Cargo vacío");
        rechazar(() -> controlador.registrarObrero("Nombre", "1111111111", " "), "Código vacío");
        rechazar(() -> controlador.registrarTecnico("Nombre", "1111111111", "COD", " "), "Clave vacía");
        rechazar(() -> controlador.registrarTecnico("Nombre", "1111111111", "COD", null), "Clave nula");
        rechazar(() -> controlador.registrarTecnico("Nombre", "1111111111", "", "clave"), "Código técnico vacío");
        verificar(controlador.getTotalEmpleados() == 3 && controlador.listarEmpleados().size() == 3, "Ningún registro parcial");
        verificar(controlador.consultarPorCedula("1111111111") == null, "Cédula fallida disponible");
        verificar(!repositorio.registrar(new Tecnico("Duplicado", "0012345678", "TEC-02", "clave")), "Repositorio evita duplicados");
        rechazar(() -> repositorio.registrar(null), "Repositorio rechaza nulos");
        List<Persona> copia = repositorio.listar();
        boolean protegida = false;
        try {
            copia.clear();
        } catch (UnsupportedOperationException ex) {
            protegida = true;
        }
        verificar(protegida && repositorio.listar().size() == 3, "Lista protegida de cambios externos");
        verificar(controlador.registrarTecnico("Nombre", "1111111111", "COD", "clave"), "Corregir registro rechazado");
        verificar(copia.size() == 3 && controlador.getTotalEmpleados() == 4, "Copia independiente y contador actualizado");
        Repositorio<Obrero> obreros = new Repositorio<>();
        verificar(obreros.registrar(new Tecnico("Técnico", "2222222222", "T", "clave")), "Método genérico admite subtipo");
        verificar(obreros.buscarPorCedula("2222222222") instanceof Tecnico, "Repositorio tipado");
        for (int i = 0; i < 150; i++) {
            obreros.registrar(new Obrero("Empleado " + i, String.format("8%09d", i), "O-" + i));
        }
        verificar(obreros.getContadorRegistros() == 151, "Boxing y unboxing más allá de caché Integer");
        System.out.println("OK: " + comprobaciones + " comprobaciones de modelo y controlador.");
    }

    private static void verificar(boolean condicion, String caso) {
        if (!condicion) {
            throw new AssertionError(caso);
        }
        comprobaciones++;
    }

    private static void rechazar(Runnable accion, String caso) {
        try {
            accion.run();
        } catch (IllegalArgumentException ex) {
            verificar(ex.getMessage() != null && !ex.getMessage().isBlank(), caso);
            return;
        }
        throw new AssertionError("No se rechazó: " + caso);
    }
}
