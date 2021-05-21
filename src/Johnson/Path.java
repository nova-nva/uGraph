package Johnson;

import Main.DrawingPanel;
import Main.Link;
import Main.Nodo;
import Themes.MainTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Path extends JPanel implements MouseListener, MouseMotionListener {
    private Vector<HashMap<Integer, Integer>> graph;
    private Details details;
    private Pad pad;

    private Vector<Link> newLinks;
    private Vector<Nodo> newNodes;

    private int ida[];
    private int vuelta[];

    private JLabel appi, apti;
    private JLabel appj, aptj;

    MainTheme theme;

    public Path(MainTheme theme, Vector<HashMap<Integer, Integer>> graph, Details details, Pad pad) {
        this.theme = theme;
        this.graph = graph;
        this.details = details;
        this.pad = pad;
        this.newLinks = new Vector<Link>();
        this.newNodes = DrawingPanel.nodos;

        this.addMouseListener(this);

        setLayout(null);
        setBackground(theme.getBackground());

        ida = new int[graph.size()];
        vuelta = new int[graph.size()];

        ida[0] = 0;
        for(int i=1; i<ida.length; i++){
            int c=0;
            Vector<Integer> v = new Vector<Integer>();
            for(int j = 0; j<graph.size();j++){
                if(graph.get(j).containsKey(i)){
                    v.add(ida[j] + graph.get(j).get(i));
                }
            }
            Collections.sort(v);
            ida[i] = v.get(v.size()-1);
        }

        vuelta[vuelta.length-1] = ida[ida.length-1];
        for(int i=vuelta.length-2; i>-1; i--){
            Vector<Integer> v = new Vector<Integer>();
            for(Map.Entry<Integer, Integer> map: graph.get(i).entrySet()){
                v.add(vuelta[map.getKey()] - map.getValue());
            }
            Collections.sort(v);
            vuelta[i] = v.get(0);
        }

        String nodoCritico = "";
        Vector<Integer> check = new Vector<Integer>();
        for (int i = 0; i<ida.length; i++) {
            if(ida[i] == vuelta[i]){
                if(i > 0)
                    nodoCritico += " + ";
                nodoCritico += i;
                check.add(i);
            }
        }

        System.out.println("Tramo de ida");
        for(int n: ida){
            System.out.print(n + " ");
        }
        System.out.println();
        System.out.println("Tramo de vuelta");
        for(int n: vuelta){
            System.out.print(n + " ");
        }
        System.out.println();

        int count = 0;
        for (Link link: DrawingPanel.links) {
            boolean flag = false;
            for(int i = 0; i<check.size(); i++){
                if(link.getDestino() == check.get(i) && link.getOrigen() == check.get(i-1)){
                    flag = true;
                    newLinks.add(new Link(link.getLinkX1(), link.getLinkY1(), link.getLinkX2(), link.getLinkY2(), link.getOrigen(), link.getDestino(), link.getNombre(), link.getId(), true));
                }
            }
            if(!flag)
                newLinks.add(link);
            count++;
        }

        details.getAttr().setText(details.getAttr().getText() +  ida[ida.length-1]);
        details.getRutaCritica().setText(details.getRutaCritica().getText() +  nodoCritico);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        int c = 0;
        for (Nodo nodo: newNodes){
            if(c == 0){
                appi = new JLabel(ida[0] + "");
                appi.setFont(new Font("Helvetica", Font.BOLD, 10));
                appi.setBounds(nodo.getX() - 18, nodo.getY() + 5, 50, 50);
                appi.setForeground(theme.getJohnsonAttributes());
                add(appi);

                apti = new JLabel(vuelta[0] + "");
                apti.setFont(new Font("Helvetica", Font.BOLD, 10));
                apti.setBounds(nodo.getX() + 5, nodo.getY() + 5, 50, 50);
                apti.setForeground(theme.getJohnsonAttributes());
                add(apti);
            }
            if(c == newNodes.size()-1){
                appj = new JLabel(ida[ida.length-1] + "");
                appj.setFont(new Font("Helvetica", Font.BOLD, 10));
                appj.setBounds(nodo.getX() - 18, nodo.getY() + 5, 50, 50);
                appj.setForeground(theme.getJohnsonAttributes());
                add(appj);

                aptj = new JLabel(vuelta[ida.length-1] + "");
                aptj.setFont(new Font("Helvetica", Font.BOLD, 10));
                aptj.setBounds(nodo.getX() + 5, nodo.getY() + 5, 50, 50);
                aptj.setForeground(theme.getJohnsonAttributes());
                add(aptj);
            }
            nodo.paint(g);
            c++;
        }

        for(int i = 0; i<newLinks.size(); i++){
            boolean returning = false;
            int a = newLinks.get(i).getOrigen();
            int b = newLinks.get(i).getDestino();
            for(int j = i; j<newLinks.size(); j++){
                if((newLinks.get(j).getOrigen() == a && newLinks.get(j).getOrigen() == b) || (newLinks.get(j).getOrigen() == b && newLinks.get(j).getOrigen() == a)) {
                    returning = true;
                    break;
                }
            }
            if (returning)
                newLinks.get(i).paint(g, 2);
            else
                newLinks.get(i).paint(g, 1);
        }
        /*
        for (Link link: this.newLinks) {
            link.paint(g);
        }

         */
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

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Punto presionado en el Path: " + e.getPoint());
        System.out.println("No me toques pinshi shango :v");
        for (Nodo nodo: newNodes) {
            if(new Rectangle(nodo.getX() - Nodo.diametro/2, nodo.getY() - Nodo.diametro/2, Nodo.diametro, Nodo.diametro).contains(e.getPoint())){
                System.out.println("Objeto detectado en el Path -> Nodo: " + nodo.getNombre());
                pad.getAppi().setText("Atributo mas pronto posible de inicio (appi): " + ida[Integer.parseInt(nodo.getNombre())]);
                pad.getAtpi().setText("Atributo mas tardio permisible de inicio (atpi): " + vuelta[Integer.parseInt(nodo.getNombre())]);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
