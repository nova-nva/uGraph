package Main;

import Themes.MainTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Vector;

public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {
    // Graphic Part
    public static Vector<Nodo> nodos;
    public static Vector<Link> links;
    private Point p1, p2;
    private Nodo move;
    private int index;

    // Logic Part
    public static Vector<HashMap<Integer, Integer>> graph;
    public static int nodoCount = 0;
    public static int linkCount = 0;

    // Edit Part
    private Vector<Nodo> editNode;
    private Vector<Link> editLink;

    // Theme
    MainTheme theme;

    public DrawingPanel(){
        this.nodos = new Vector<>();
        this.links = new Vector<>();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        graph = new Vector<HashMap<Integer, Integer>>();
        theme = MainFrame.theme;
        setBackground(theme.getBackground());

        this.editNode = new Vector<>();
        this.editLink = new Vector<>();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        theme = MainFrame.theme;
        setBackground(theme.getBackground());
        System.out.println();
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

        /*
        for (Link link: links) {
            link.paint(g);
        }
        */

        System.out.println("Grafo:");
        for(int i = 0; i<graph.size(); i++){
            System.out.println(i + " -> " + graph.get(i));
        }
        System.out.println();
    }

    private int a = 0, b = 0;
    private String nOrigen = "", nDestino = "";
    @Override
    public void mouseClicked(MouseEvent e) {
        if(MainFrame.chosenWorkspace.equals("Graph")) {
            if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() < 2 && (MainFrame.mode.equals("edit") || MainFrame.mode.equals("delete"))) { //clic izquierdo
                editNode.clear();
                editLink.clear();
                int cnt = 0;
                for (Nodo nodo : nodos) {
                    if (new Rectangle(nodo.getX() - Nodo.diametro / 2, nodo.getY() - Nodo.diametro / 2, Nodo.diametro, Nodo.diametro).contains(e.getPoint())) {
                        System.out.println("Objeto detectado -> Nodo: " + nodo.getNombre());
                        editNode.add(nodo);
                    }
                    cnt++;
                }

                // para links
                int HIT_BOX_SIZE = 6;
                int boxX = e.getX() - HIT_BOX_SIZE / 2;
                int boxY = e.getY() - HIT_BOX_SIZE / 2;
                int width = HIT_BOX_SIZE;
                int height = HIT_BOX_SIZE;

                cnt = 0;
                for (Link link : links) {
                    Line2D choque = new Line2D() {
                        @Override
                        public double getX1() {
                            return link.getCalculedX1();
                        }

                        @Override
                        public double getY1() {
                            return link.getCalculedY1();
                        }

                        @Override
                        public Point2D getP1() {
                            return null;
                        }

                        @Override
                        public double getX2() {
                            return link.getCalculedX2();
                        }

                        @Override
                        public double getY2() {
                            return link.getCalculedY2();
                        }

                        @Override
                        public Point2D getP2() {
                            return null;
                        }

                        @Override
                        public void setLine(double x1, double y1, double x2, double y2) {

                        }

                        @Override
                        public Rectangle2D getBounds2D() {
                            return null;
                        }
                    };

                    if (choque.intersects(boxX, boxY, width, height)) {
                        System.out.println("Objeto detectado -> Link: " + link.getNombre());
                        editLink.add(link);
                    }
                    cnt++;
                }
                if (MainFrame.mode.equals("edit"))
                    edit();
                else if (MainFrame.mode.equals("delete"))
                    delete();
            }

            if (e.getButton() == MouseEvent.BUTTON3 && MainFrame.mode.equals("create")) { // clic derecho
                for (int i = 0; i < nodos.size(); i++) {
                    if (new Rectangle(nodos.get(i).getX() - Nodo.diametro / 2, nodos.get(i).getY() - Nodo.diametro / 2, Nodo.diametro, Nodo.diametro).contains(e.getPoint())) {
                        if (p1 == null) {
                            p1 = new Point(nodos.get(i).getX(), nodos.get(i).getY());
                            a = i;
                            nOrigen = nodos.get(i).getNombre();
                            System.out.println("Conexión desde: " + a + " (" + nOrigen + ")");
                        } else {
                            p2 = new Point(nodos.get(i).getX(), nodos.get(i).getY());
                            b = i;
                            nDestino = nodos.get(i).getNombre();
                            System.out.println("Conexión hacia: " + b + " (" + nDestino + ")");

                            String nombre = JOptionPane.showInputDialog("Ingrese el peso del enlace:");
                            try {
                                int peso = Integer.parseInt(nombre);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "ERROR: El valor del link debe ser numerico.");
                                p1 = null;
                                p2 = null;
                                a = 0;
                                b = 0;
                                repaint();
                                return;
                            }
                            this.links.add(new Link(p1.x, p1.y, p2.x, p2.y, a, b, nOrigen, nDestino, nombre, linkCount));
                            linkCount++;
                            graph.get(a).put(b, Integer.parseInt(nombre));
                            p1 = null;
                            p2 = null;
                            a = 0;
                            b = 0;
                            repaint();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(MainFrame.chosenWorkspace.equals("Graph")) {
            System.out.println("Estamos en graph!");
            if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && MainFrame.mode.equals("create")) {
                String nombre;
                if (MainFrame.autoNamedNodes)
                    nombre = nodoCount + "";
                else
                    nombre = JOptionPane.showInputDialog("Ingrese el nombre del nodo:");

                Nodo nuevo = new Nodo(e.getX(), e.getY(), nombre, nodoCount);
                System.out.println(nuevo.getNombre());
                this.nodos.add(new Nodo(e.getX(), e.getY(), nombre, nodoCount));
                nodoCount++;
                graph.add(new HashMap<Integer, Integer>());
                repaint();
            }
            if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON3) {

            } else {
                int c = 0;
                for (Nodo nodo : nodos) {
                    if (new Rectangle(nodo.getX() - Nodo.diametro / 2, nodo.getY() - Nodo.diametro / 2, Nodo.diametro, Nodo.diametro).contains(e.getPoint())) {
                        move = nodo;
                        index = c;
                        break;
                    }
                    c++;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // reset
        move = null;
        index = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(move != null){
            this.nodos.set(index, new Nodo(e.getX(), e.getY(), move.getNombre(), move.getNro()));
            int c = 0;
            for (Link link: links) {
                if(new Rectangle(link.getLinkX1() - Nodo.diametro/2, link.getLinkY1() - Nodo.diametro/2, Nodo.diametro, Nodo.diametro).contains(e.getPoint())){
                    this.links.set(c, new Link(e.getX(), e.getY(), link.getLinkX2(), link.getLinkY2(), link.getOrigen(), link.getDestino(), link.getnOrigen(), link.getnDestino(), link.getNombre(), link.getId()));
                }
                else if(new Rectangle(link.getLinkX2() - Nodo.diametro/2, link.getLinkY2() - Nodo.diametro/2, Nodo.diametro, Nodo.diametro).contains(e.getPoint())){
                    this.links.set(c, new Link(link.getLinkX1(), link.getLinkY1(), e.getX(), e.getY(), link.getOrigen(), link.getDestino(), link.getnOrigen(), link.getnDestino(), link.getNombre(), link.getId()));
                }
                c++;
            }
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void edit(){
        // nodos
        if(editNode.size() > 0) {
            if (editNode.size() > 1) {
                String cad = "Se han detectado " + editNode.size() + " nodos.\n\nSeleccione el nodo a editar:";
                for (int i = 0; i < editNode.size(); i++) {
                    cad += "\n" + (i + 1) + ". Nodo: " + editNode.get(i).getNombre();
                }
                cad+="\n\n";

                String opcion = JOptionPane.showInputDialog(cad);
                if(opcion == null){
                    return;
                }

                int op;
                try {
                    op = Integer.parseInt(opcion);
                }
                catch (Exception e){
                    JOptionPane.showMessageDialog(null, "ERROR: La opcion elegida debe ser numerica.");
                    return;
                }

                String nNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:");
                if(nNombre == null){
                    return;
                }
                if(nNombre.length() == 0){
                    JOptionPane.showMessageDialog(null, "ERROR: Debe ingresar un nombre para el nodo.");
                    return;
                }
                editNode.get(op - 1).setNombre(nNombre);
            } else {
                String nNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:");
                if(nNombre == null){
                    return;
                }
                if(nNombre.length() == 0){
                    JOptionPane.showMessageDialog(null, "ERROR: Debe ingresar un nombre para el nodo.");
                    return;
                }
                editNode.get(0).setNombre(nNombre);
            }
            refresh();
        }

        // links
        if(editLink.size() > 0) {
            if (editLink.size() > 1) {
                String cad = "Se han detectado " + editLink.size() + " conexiones.\n\nSeleccione el link a editar:";
                for (int i = 0; i < editLink.size(); i++) {
                    cad += "\n" + (i + 1) + ". Recorrido: " + editLink.get(i).getnOrigen() + " => " + editLink.get(i).getnDestino();
                }
                cad+="\n\n";
                String opcion = JOptionPane.showInputDialog(cad);
                if(opcion == null){
                    return;
                }
                if(opcion.length() == 0){
                    return;
                }

                int op;
                try {
                    op = Integer.parseInt(opcion);
                }
                catch (Exception e){
                    JOptionPane.showMessageDialog(null, "ERROR: La opcion elegida debe ser numerica.");
                    return;
                }

                String nPeso = JOptionPane.showInputDialog("Ingrese el nuevo valor:");
                if(nPeso == null){
                    return;
                }
                if(nPeso.length() == 0){
                    JOptionPane.showMessageDialog(null, "ERROR: Debe ingresar un valor para el link.");
                    return;
                }
                try{
                    int peso = Integer.parseInt(nPeso);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "ERROR: El valor del link debe ser numerico.");
                    return;
                }
                editLink.get(op - 1).setNombre(nPeso);
            } else {
                String nPeso = JOptionPane.showInputDialog("Ingrese el nuevo valor:");
                if(nPeso == null){
                    return;
                }
                if(nPeso.length() == 0){
                    JOptionPane.showMessageDialog(null, "ERROR: Debe ingresar un valor para el link.");
                    return;
                }
                try{
                    int peso = Integer.parseInt(nPeso);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "ERROR: El valor del link debe ser numerico.");
                    return;
                }
                editLink.get(0).setNombre(nPeso);
            }
            refresh();
        }
        repaint();
    }

    public void refresh(){
        Vector<HashMap<Integer, Integer>> newGraph = new Vector<HashMap<Integer, Integer>>();
        for(Nodo nodo: nodos){
            newGraph.add(new HashMap<Integer, Integer>());
        }
        for(Link link: links){
            int start = link.getOrigen();
            int end = link.getDestino();
            link.setnOrigen(nodos.get(start).getNombre());
            link.setnDestino(nodos.get(end).getNombre());
            newGraph.get(start).put(end, Integer.parseInt(link.getNombre()));
        }
        graph = newGraph;
    }

    public void refreshDeletedNode(int nro){
        Vector<HashMap<Integer, Integer>> newGraph = new Vector<HashMap<Integer, Integer>>();
        Vector<Nodo> newNodos = new Vector<>();
        Vector<Link> newLinks = new Vector<>();
        for(Nodo nodo: nodos){
            if(nodo.getNro() == nro) {
                for(Link link: links){
                    int start = link.getOrigen();
                    int end = link.getDestino();
                    System.out.println("Checando... " + "de " + start + " a " + end);
                    if(start == nro || end == nro) {
                        System.out.println("se va!");
                        continue;
                    }
                    if(start > nro)
                        link.setOrigen(start-1);
                    if(end > nro)
                        link.setDestino(end-1);
                    System.out.println("Ahora es... " + "de " + link.getOrigen() + " a " + link.getDestino());
                    System.out.println("Se agrega cambiado!");
                    newLinks.add(link);
                }
                continue;
            }
            newGraph.add(new HashMap<Integer, Integer>());
            newNodos.add(nodo);
        }
        for(Link link: newLinks){
            newGraph.get(link.getOrigen()).put(link.getDestino(), Integer.parseInt(link.getNombre()));
        }
        graph = newGraph;
        nodos = newNodos;
        links = newLinks;
        refreshID();
    }

    public void refreshDeletedLink(int id){
        Vector<HashMap<Integer, Integer>> newGraph = new Vector<HashMap<Integer, Integer>>();
        Vector<Link> newLinks = new Vector<>();
        for(Nodo nodo: nodos){
            newGraph.add(new HashMap<Integer, Integer>());
        }
        for(Link link: links){
            int linkID = link.getId();
            int start = link.getOrigen();
            int end = link.getDestino();
            if(linkID == id)
                continue;
            newGraph.get(start).put(end, Integer.parseInt(link.getNombre()));
            newLinks.add(link);
        }
        //refreshID();
        graph = newGraph;
        links = newLinks;
    }

    public void refreshID(){
        for(int i = 0; i<links.size(); i++){
            links.get(i).setId(i);
        }
        for(int i = 0; i<nodos.size(); i++){
            nodos.get(i).setNro(i);
        }
    }

    public void delete(){
        // nodos
        if(editNode.size() > 0) {
            if (editNode.size() > 1) {
                String cad = "Se han detectado " + editNode.size() + " nodos.\n\nSeleccione el nodo a eliminar:";
                for (int i = 0; i < editNode.size(); i++) {
                    cad += "\n" + (i + 1) + ". Nodo: " + editNode.get(i).getNombre();
                }
                cad+="\n\n";
                String opcion = JOptionPane.showInputDialog(cad);
                if(opcion == null)
                    return;
                if(opcion.length() == 0) {
                    JOptionPane.showMessageDialog(null, "ERROR: Debe ingresar una opcion");
                    return;
                }
                int op;
                try {
                    op = Integer.parseInt(opcion);
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "ERROR: La opcion elegida debe ser numerica");
                    return;
                }
                refreshDeletedNode(editNode.get(op-1).getNro());
            } else {
                System.out.println("Se va a ir el nodo: " + editNode.get(0).getNro());
                refreshDeletedNode(editNode.get(0).getNro());
            }
            nodoCount--;
        }

        // links
        if(editLink.size() > 0) {
            if (editLink.size() > 1) {
                String cad = "Se han detectado " + editLink.size() + " conexiones.\n\nSeleccione el link a eliminar:";
                for (int i = 0; i < editLink.size(); i++) {
                    cad += "\n" + (i + 1) + ". Recorrido: " + editLink.get(i).getnOrigen() + " => " + editLink.get(i).getnDestino() + " (Valor: " + editLink.get(i).getNombre() + ")";
                }
                cad+="\n\n";
                String opcion = JOptionPane.showInputDialog(cad);
                if(opcion == null)
                    return;
                if(opcion.length() == 0) {
                    JOptionPane.showMessageDialog(null, "ERROR: Debe ingresar una opcion");
                    return;
                }
                int op;
                try{
                    op = Integer.parseInt(opcion);
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "ERROR: La opcion elegida debe ser numerica");
                    return;
                }
                System.out.println("Se va a ir: " + editLink.get(op-1).getId());
                refreshDeletedLink(editLink.get(op-1).getId());
            } else {
                refreshDeletedLink(editLink.get(0).getId());
            }
            linkCount--;
        }
        repaint();
    }
}
