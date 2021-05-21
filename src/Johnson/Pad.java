package Johnson;

import Main.DrawingPanel;
import Main.Link;
import Themes.MainTheme;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class Pad extends JPanel {
    private Vector<HashMap<Integer, Integer>> graph;
    private Font bold;

    private Vector<Link> newLinks;
    private JLabel appi, atpi;
    private JLabel appj, atpj;

    MainTheme theme;

    public JLabel getAppi() {
        return appi;
    }

    public void setAppi(JLabel appi) {
        this.appi = appi;
    }

    public JLabel getAtpi() {
        return atpi;
    }

    public void setAtpi(JLabel atpi) {
        this.atpi = atpi;
    }

    public Pad(MainTheme theme) {
        this.theme = theme;

        setLayout(null);
        setBackground(theme.getBackground());

        JLabel title = new JLabel("Interactive Pad");
        title.setFont(new Font("Helvetica", Font.BOLD, 15));
        title.setBounds(20, 0, 500, 50);
        title.setForeground(theme.getLabel());
        add(title);

        appi = new JLabel("[Selecciona un nodo...]");
        appi.setFont(new Font("Helvetica", Font.PLAIN, 10));
        appi.setBounds(20, 30, 500, 50);
        appi.setForeground(theme.getLabel());
        add(appi);

        atpi = new JLabel();
        atpi.setFont(new Font("Helvetica", Font.PLAIN, 10));
        atpi.setBounds(20, 50, 500, 50);
        atpi.setForeground(theme.getLabel());
        add(atpi);

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
