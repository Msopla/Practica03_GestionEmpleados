package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import controlador.ControladorEmpleados;

public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    private final ControladorEmpleados controlador;
    private final CardLayout tarjetas = new CardLayout();
    private final JPanel contenido = new JPanel(tarjetas);
    private final JLabel cabecera = new JLabel();
    private final Map<String, JButton> botones = new LinkedHashMap<>();
    private final JLabel administrativos = new JLabel("0");
    private final JLabel obreros = new JLabel("0");
    private final JLabel tecnicos = new JLabel("0");
    private final VentanaListado listado;

    public VentanaPrincipal(ControladorEmpleados controlador) {
        super("Sistema de Gestión de Empleados");
        this.controlador = controlador;
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 14));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1060, 760);
        setMinimumSize(new Dimension(960, 720));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        cabecera.setOpaque(true);
        cabecera.setBackground(EstiloVista.AZUL);
        cabecera.setForeground(Color.WHITE);
        cabecera.setFont(new Font("SansSerif", Font.BOLD, 21));
        cabecera.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));
        add(cabecera, BorderLayout.NORTH);
        JPanel navegacion = new JPanel();
        navegacion.setLayout(new BoxLayout(navegacion, BoxLayout.Y_AXIS));
        navegacion.setBackground(EstiloVista.CLARO);
        navegacion.setBorder(BorderFactory.createEmptyBorder(28, 14, 20, 14));
        navegacion.setPreferredSize(new Dimension(190, 0));
        for (String seccion : new String[] { "Inicio", "Administrativos", "Obreros", "Técnicos", "Consultar", "Listar" }) {
            JButton boton = EstiloVista.boton(seccion, false);
            boton.setHorizontalAlignment(JButton.LEFT);
            boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            boton.setAlignmentX(LEFT_ALIGNMENT);
            boton.setBorder(BorderFactory.createEmptyBorder(14, 12, 14, 12));
            boton.addActionListener(e -> mostrarSeccion(seccion));
            navegacion.add(boton);
            navegacion.add(Box.createVerticalStrut(12));
            botones.put(seccion, boton);
        }
        add(navegacion, BorderLayout.WEST);
        listado = new VentanaListado(controlador);
        agregarSeccion("Inicio", crearInicio());
        agregarSeccion("Administrativos", new FormularioAdministrativo(controlador, this::actualizarDatos));
        agregarSeccion("Obreros", new FormularioObrero(controlador, this::actualizarDatos));
        agregarSeccion("Técnicos", new FormularioTecnico(controlador, this::actualizarDatos));
        agregarSeccion("Consultar", new VentanaConsulta(controlador));
        contenido.add(listado, "Listar");
        add(contenido, BorderLayout.CENTER);
        actualizarDatos();
        mostrarSeccion("Inicio");
    }

    private void agregarSeccion(String nombre, JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        contenido.add(scroll, nombre);
    }

    private JPanel crearInicio() {
        JPanel pagina = EstiloVista.pagina();
        JPanel bienvenida = new JPanel();
        bienvenida.setLayout(new BoxLayout(bienvenida, BoxLayout.Y_AXIS));
        bienvenida.setOpaque(false);
        bienvenida.add(EstiloVista.titulo("Bienvenido al sistema"));
        bienvenida.add(Box.createVerticalStrut(16));
        bienvenida.add(new JLabel("Seleccione una opción para gestionar empleados."));
        bienvenida.add(Box.createVerticalStrut(30));
        JPanel resumen = new JPanel(new GridLayout(1, 3, 14, 0));
        resumen.setOpaque(false);
        resumen.add(crearContador("Administrativos", administrativos));
        resumen.add(crearContador("Obreros", obreros));
        resumen.add(crearContador("Técnicos", tecnicos));
        resumen.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        resumen.setAlignmentX(LEFT_ALIGNMENT);
        bienvenida.add(resumen);
        bienvenida.add(Box.createVerticalStrut(75));
        JLabel opciones = new JLabel("Opciones disponibles");
        opciones.setFont(new Font("SansSerif", Font.BOLD, 17));
        opciones.setForeground(EstiloVista.AZUL);
        bienvenida.add(opciones);
        bienvenida.add(Box.createVerticalStrut(12));
        bienvenida.add(new JLabel("Registrar, consultar por cédula y listar empleados."));
        pagina.add(bienvenida, BorderLayout.NORTH);
        return pagina;
    }

    private JPanel crearContador(String titulo, JLabel numero) {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(EstiloVista.CLARO);
        panel.setBorder(BorderFactory.createEmptyBorder(24, 18, 24, 18));
        JLabel etiqueta = new JLabel(titulo);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 15));
        etiqueta.setForeground(EstiloVista.AZUL);
        numero.setFont(new Font("SansSerif", Font.BOLD, 28));
        numero.setForeground(EstiloVista.ACENTO);
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(numero, BorderLayout.CENTER);
        return panel;
    }

    private void actualizarDatos() {
        listado.actualizar();
        administrativos.setText(String.valueOf(controlador.listarEmpleados().stream()
                .filter(e -> e.getTipo().equals("Administrativo")).count()));
        obreros.setText(String.valueOf(controlador.listarEmpleados().stream()
                .filter(e -> e.getTipo().equals("Obrero")).count()));
        tecnicos.setText(String.valueOf(controlador.listarEmpleados().stream()
                .filter(e -> e.getTipo().equals("Técnico")).count()));
    }

    private void mostrarSeccion(String seccion) {
        actualizarDatos();
        tarjetas.show(contenido, seccion);
        String titulo = switch (seccion) {
            case "Administrativos" -> "Registrar administrativo";
            case "Obreros" -> "Registrar obrero";
            case "Técnicos" -> "Registrar técnico";
            case "Consultar" -> "Consulta por cédula";
            case "Listar" -> "Listado de empleados";
            default -> "Menú principal";
        };
        cabecera.setText("Gestión de personal   /   " + titulo);
        botones.forEach((nombre, boton) -> {
            boton.setBackground(nombre.equals(seccion) ? EstiloVista.AZUL : EstiloVista.CLARO);
            boton.setForeground(nombre.equals(seccion) ? Color.WHITE : EstiloVista.AZUL);
        });
    }
}
