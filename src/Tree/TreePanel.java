package Tree;

import Main.Link;
import Main.MainFrame;
import Main.Nodo;
import Themes.MainTheme;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class TreePanel extends JPanel {
    // Graphic Part
    public static Vector<Nodo> nodos;
    public static Vector<Link> links;

    // Logic Part
    public static Tree tree;

    // Theme
    MainTheme theme;

    public TreePanel(){
        nodos = new Vector<>();
        links = new Vector<>();
        tree = new Tree();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        theme = MainFrame.theme;
        setBackground(theme.getBackground());

        System.out.println("Nodos contenidos:");
        for (Nodo nodo: nodos){
            nodo.paint(g);
            System.out.println("Nombre: " + nodo.getNombre());
            System.out.println("Nro: " + nodo.getNro());
        }
        System.out.println();

        System.out.println("Enlaces contenidos:");
        for(int i = 0; i<links.size(); i++){
            System.out.println("Peso: " + links.get(i).getNombre());
            System.out.println("Nombre de Origen: " + links.get(i).getnOrigen());
            System.out.println("Nombre de Destino: " + links.get(i).getnDestino());
            System.out.println("Origen: " + links.get(i).getOrigen());
            System.out.println("Destino: " + links.get(i).getDestino());
            System.out.println("Id: " + links.get(i).getId());
            System.out.println();

            boolean returning = false;
            int a = links.get(i).getOrigen();
            int b = links.get(i).getDestino();
            if(a == b)
                links.get(i).paint(g, true);
            else {
                for (int j = 0; j < i; j++) {
                    if ((links.get(j).getOrigen() == a && links.get(j).getDestino() == b) || (links.get(j).getOrigen() == b && links.get(j).getDestino() == a)) {
                        returning = true;
                        break;
                    }
                }
                if (returning)
                    links.get(i).paint(g, 2);
                else
                    links.get(i).paint(g, 1);
            }
        }
        System.out.println();

        System.out.println("Arbol (in order): ");
        tree.inOrder(tree.getRoot());
        System.out.println();
    }
}
