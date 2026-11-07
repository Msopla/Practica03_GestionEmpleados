package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

final class EstiloVista {
    static final Color AZUL = new Color(25, 53, 80);
    static final Color ACENTO = new Color(18, 110, 150);
    static final Color CLARO = new Color(236, 244, 248);
    static final Color BORDE = new Color(200, 214, 224);
    static final Color TEXTO = new Color(42, 55, 70);

    private EstiloVista() {
        throw new AssertionError("Clase de utilidades");
    }

    static JLabel titulo(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 23));
        etiqueta.setForeground(AZUL);
        return etiqueta;
    }

    static JButton boton(String texto, boolean principal) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setBackground(principal ? ACENTO : CLARO);
        boton.setForeground(principal ? Color.WHITE : AZUL);
        boton.setOpaque(true);
        boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(11, 20, 11, 20)));
        return boton;
    }

    static JPanel pagina() {
        JPanel panel = new JPanel(new BorderLayout(0, 22));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(28, 30, 24, 30));
        return panel;
    }

    static JPanel campo(String nombre, JTextField entrada, String ayuda) {
        JPanel panel = new JPanel(new BorderLayout(0, 7));
        panel.setOpaque(false);
        JLabel etiqueta = new JLabel(nombre);
        etiqueta.setForeground(TEXTO);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 14));
        etiqueta.setLabelFor(entrada);
        entrada.setFont(new Font("SansSerif", Font.PLAIN, 15));
        entrada.setForeground(TEXTO);
        entrada.setPreferredSize(new Dimension(250, 40));
        entrada.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        entrada.setToolTipText(ayuda);
        entrada.getAccessibleContext().setAccessibleName(nombre);
        JLabel pista = new JLabel(ayuda);
        pista.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pista.setForeground(new Color(91, 111, 127));
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(entrada, BorderLayout.CENTER);
        panel.add(pista, BorderLayout.SOUTH);
        return panel;
    }

    static void agregarFila(JPanel panel, JComponent componente, int fila) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = fila;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 16, 0);
        panel.add(componente, c);
    }
}
