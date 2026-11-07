package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controlador.ControladorEmpleados;
import modelo.Administrativo;
import modelo.Obrero;
import modelo.Persona;

public class VentanaListado extends JPanel {
    private static final long serialVersionUID = 1L;
    private final ControladorEmpleados controlador;
    private final DefaultTableModel modeloTabla = new DefaultTableModel(
            new String[] { "Tipo", "Nombre", "Cédula", "Cargo/Código" }, 0) {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JLabel total = new JLabel();

    public VentanaListado(ControladorEmpleados controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());
        JPanel pagina = EstiloVista.pagina();
        JPanel cabecera = new JPanel(new BorderLayout(12, 0));
        cabecera.setOpaque(false);
        cabecera.add(EstiloVista.titulo("Empleados registrados"), BorderLayout.CENTER);
        JButton actualizar = EstiloVista.boton("Actualizar", true);
        actualizar.addActionListener(e -> actualizar());
        cabecera.add(actualizar, BorderLayout.EAST);
        pagina.add(cabecera, BorderLayout.NORTH);
        JTable tabla = new JTable(modeloTabla);
        tabla.setName("tablaEmpleados");
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.setRowHeight(38);
        tabla.setAutoCreateRowSorter(true);
        tabla.setFillsViewportHeight(true);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(EstiloVista.BORDE);
        tabla.setSelectionBackground(EstiloVista.CLARO);
        tabla.setSelectionForeground(EstiloVista.AZUL);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(EstiloVista.CLARO);
        tabla.getTableHeader().setForeground(EstiloVista.AZUL);
        tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(220);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);
        pagina.add(new JScrollPane(tabla), BorderLayout.CENTER);
        total.setFont(new Font("SansSerif", Font.BOLD, 16));
        total.setForeground(EstiloVista.AZUL);
        pagina.add(total, BorderLayout.SOUTH);
        add(pagina);
        actualizar();
    }

    public void actualizar() {
        modeloTabla.setRowCount(0);
        for (Persona empleado : controlador.listarEmpleados()) {
            String detalle = empleado instanceof Administrativo administrativo ? administrativo.getCargo()
                    : ((Obrero) empleado).getCodigo();
            modeloTabla.addRow(new Object[] { empleado.getTipo(), empleado.getNombre(), empleado.getCedula(), detalle });
        }
        total.setText("Total de empleados: " + controlador.getTotalEmpleados());
    }
}
