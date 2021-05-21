package Johnson;

import Main.DrawingPanel;
import Main.Link;
import Main.Nodo;
import Themes.MainTheme;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Details extends JPanel {
    private Vector<HashMap<Integer, Integer>> graph;
    private Font bold;

    private Vector<Link> newLinks;

    private JLabel attr, rutaCritica;

    MainTheme theme;

    public JLabel getAttr() {
        return attr;
    }

    public void setAttr(JLabel attr) {
        this.attr = attr;
    }

    public JLabel getRutaCritica() {
        return rutaCritica;
    }

    public void setRutaCritica(JLabel rutaCritica) {
        this.rutaCritica = rutaCritica;
    }

    public Details(MainTheme theme) {
        this.theme = theme;

        setLayout(null);
        setBackground(theme.getBackground());

        JLabel title = new JLabel("Main Details");
        title.setFont(new Font("Helvetica", Font.BOLD, 15));
        title.setBounds(20, 0, 500, 50);
        title.setForeground(theme.getLabel());
        add(title);

        rutaCritica = new JLabel("Ruta Critica: ");
        rutaCritica.setFont(new Font("Helvetica", Font.PLAIN, 10));
        rutaCritica.setBounds(20, 30, 500, 50);
        rutaCritica.setForeground(theme.getLabel());
        add(rutaCritica);

        attr = new JLabel("Atributo Total: ");
        attr.setFont(new Font("Helvetica", Font.PLAIN, 10));
        attr.setBounds(20, 50, 500, 50);
        attr.setForeground(theme.getLabel());
        add(attr);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
    }

    private void debug(int[] ida, int[] vuelta, int[] check){
        System.out.println("Lista de adyacencia:");
        for (int i=0; i<graph.size();i++) {
            System.out.println(i+ " -> " + graph.get(i));
        }
        System.out.println();

        System.out.println("Aristas:");
        for (Link link: DrawingPanel.links) {
            System.out.println("Arista desde: " + link.getOrigen() + " hacia " + link.getDestino() + " con peso de: " + link.getNombre());
        }
        System.out.println();

        System.out.println("Valores de ida:");
        for (int n: ida) {
            System.out.print(n + "  " );
        }
        System.out.println();
        System.out.println();

        System.out.println("Valores de vuelta:");
        for (int n: vuelta) {
            System.out.print(n + "  " );
        }
        System.out.println();
        System.out.println();

        System.out.println("Nodos clasificados:");
        for (Integer n: check) {
            System.out.print(n + "  " );
        }
        System.out.println();
        System.out.println();
    }
}
