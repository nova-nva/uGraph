package DeletedFiles;
import Themes.PhoenixDark;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.HashMap;
import java.util.Vector;

public class MainDos {
    /*
    public static boolean menuActivated = false;
    public static boolean editionActivated = false;
    public static String version = "uGraph 4.1 (Beta)";

    public static String mode = "create";
    public static DrawingPanel canvas;

    public static PhoenixDark theme;

    public static String nombreArchivo = null;

    public MainDos() {

    }

    public static void main(String[] args){

        theme = new PhoenixDark();

        JFrame window = new JFrame(MainDos.version);
        canvas = new DrawingPanel();
        canvas.setLayout(null);
        canvas.setBorder(new EmptyBorder(5,5,5,5));

        window.setContentPane(canvas);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);

        JLabel menu = new JLabel("...");
        menu.setFont(new Font("Helvetica", Font.BOLD, 25));
        menu.setBounds(50, 35, 100, 50);
        menu.setForeground(new Color(255,255,255));
        canvas.add(menu);

        JLabel matrix = new JLabel("To Matrix");
        matrix.setFont(new Font("Helvetica", Font.BOLD, 15));
        matrix.setBounds(55, 90, 100, 50);
        matrix.setForeground(new Color(255,255,255));
        matrix.setVisible(false);
        canvas.add(matrix);

        JLabel johnson = new JLabel("Johnson");
        johnson.setFont(new Font("Helvetica", Font.BOLD, 15));
        johnson.setBounds(55, 145, 100, 50);
        johnson.setForeground(new Color(255,255,255));
        johnson.setVisible(false);
        canvas.add(johnson);

        JLabel menuTail = new JLabel(".    .    .");
        menuTail.setFont(new Font("Helvetica", Font.BOLD, 25));
        menuTail.setBounds(50, 190, 100, 50);
        menuTail.setForeground(new Color(255,255,255));
        menuTail.setVisible(false);
        canvas.add(menuTail);

        JLabel credits = new JLabel(version +" - © All Rights Reserved");
        credits.setFont(new Font("Helvetica", Font.BOLD, 15));
        credits.setBounds(250, 510, 500, 50);
        credits.setForeground(new Color(255,255,255));
        canvas.add(credits);

        JLabel editionMenu = new JLabel("...");
        editionMenu.setFont(new Font("Helvetica", Font.BOLD, 25));
        editionMenu.setBounds(710, 35, 100, 50);
        editionMenu.setForeground(new Color(255,255,255));
        canvas.add(editionMenu);

        JLabel create = new JLabel("Create");
        create.setFont(new Font("Helvetica", Font.BOLD, 15));
        create.setBounds(667, 75, 100, 50);
        create.setForeground(new Color(255,255,255));
        create.setVisible(false);
        canvas.add(create);

        JLabel edit = new JLabel("Edit");
        edit.setFont(new Font("Helvetica", Font.BOLD, 15));
        edit.setBounds(667, 105, 100, 50);
        edit.setForeground(new Color(255,255,255));
        edit.setVisible(false);
        canvas.add(edit);

        JLabel delete = new JLabel("Delete");
        delete.setFont(new Font("Helvetica", Font.BOLD, 15));
        delete.setBounds(667, 135, 100, 50);
        delete.setForeground(new Color(255,255,255));
        delete.setVisible(false);
        canvas.add(delete);

        JLabel editionMenuTail = new JLabel(".    .    .");
        editionMenuTail.setFont(new Font("Helvetica", Font.BOLD, 25));
        editionMenuTail.setBounds(652, 162, 100, 50);
        editionMenuTail.setForeground(new Color(255,255,255));
        editionMenuTail.setVisible(false);
        canvas.add(editionMenuTail);

        JMenuBar menuBar = new JMenuBar();
        //menuBar.setBackground(new Color(0,0,0));
        menuBar.setBounds(0, 0, 800,25);
        menuBar.setVisible(true);
        //canvas.add(menuBar);
        window.setJMenuBar(menuBar);

        JMenu file = new JMenu("File");
        file.setVisible(true);
        menuBar.add(file);

        JMenu open = new JMenu("Open");
        open.setVisible(true);
        file.add(open);
        loadFiles(open);
        if(open.getItemCount() < 1){
            JMenuItem item = new JMenuItem("(no saved projects...)");
            item.setVisible(true);
            open.add(item);
        }

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filesLog = "files.spt";
                try {
                    // Comprobar que el archivo existe
                    DataInputStream in = new DataInputStream(new FileInputStream(filesLog));
                    String file, nombre;
                    boolean exists = false;
                    while(in.available() != 0){
                        file = in.readUTF();
                        if(file.equals(nombreArchivo)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        JOptionPane.showMessageDialog(null, "Imposible guardar cambios de un archivo inexistente\nUtilice 'Save as...' si es la primera vez que guarda el archivo");
                        return;
                    }

                    // Registrar el archivo
                    DataOutputStream out = new DataOutputStream(new FileOutputStream(nombreArchivo, false));
                    out.writeUTF("Links:" + DrawingPanel.links.size());
                    for (Link link : DrawingPanel.links) {
                        out.writeInt(link.getLinkX1());
                        out.writeInt(link.getLinkY1());
                        out.writeInt(link.getLinkX2());
                        out.writeInt(link.getLinkY2());
                        out.writeUTF(link.getNombre());
                        out.writeInt(link.getOrigen());
                        out.writeInt(link.getDestino());
                        out.writeUTF(link.getnOrigen());
                        out.writeUTF(link.getnDestino());
                        out.writeInt(link.getId());
                    }
                    out.writeUTF("Nodos:" + DrawingPanel.nodos.size());
                    for (Nodo nodo : DrawingPanel.nodos) {
                        out.writeInt(nodo.getX());
                        out.writeInt(nodo.getY());
                        out.writeUTF(nodo.getNombre());
                        out.writeInt(nodo.getNro());
                    }
                    JOptionPane.showMessageDialog(null, "Changes Saved Correctly");
                    out.close();
                    loadFiles(open);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        save.setVisible(true);
        file.add(save);

        JMenuItem saveAs = new JMenuItem("Save as...");
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filesLog = "files.spt";
                String nombre = JOptionPane.showInputDialog("Ingrese el nombre del archivo:");
                System.out.println("Respuesta usuario: " + nombre);
                if(nombre == null)
                    return;
                if(nombre.length() == 0){
                    JOptionPane.showMessageDialog(null, "Debe ingresar un nombre para guardar el archivo");
                    return;
                }
                try {
                    // Comprobar que no existen files con el mismo nombre
                    DataInputStream in = new DataInputStream(new FileInputStream(filesLog));
                    String file;
                    while(in.available() != 0){
                        file = in.readUTF();
                        if(file.equals(nombre + ".gph")) {
                            JOptionPane.showMessageDialog(null, "No se pueden guardar dos archivos con el mismo nombre");
                            return;
                        }
                    }

                    // Registrar el archivo
                    DataOutputStream generalOut = new DataOutputStream(new FileOutputStream("files.spt", true));
                    generalOut.writeUTF(nombre + ".gph");
                    generalOut.close();
                    DataOutputStream out = new DataOutputStream(new FileOutputStream(nombre + ".gph", false));
                    out.writeUTF("Links:" + DrawingPanel.links.size());
                    for (Link link : DrawingPanel.links) {
                        out.writeInt(link.getLinkX1());
                        out.writeInt(link.getLinkY1());
                        out.writeInt(link.getLinkX2());
                        out.writeInt(link.getLinkY2());
                        out.writeUTF(link.getNombre());
                        out.writeInt(link.getOrigen());
                        out.writeInt(link.getDestino());
                        out.writeUTF(link.getnOrigen());
                        out.writeUTF(link.getnDestino());
                        out.writeInt(link.getId());
                    }
                    out.writeUTF("Nodos:" + DrawingPanel.nodos.size());
                    for (Nodo nodo : DrawingPanel.nodos) {
                        out.writeInt(nodo.getX());
                        out.writeInt(nodo.getY());
                        out.writeUTF(nodo.getNombre());
                        out.writeInt(nodo.getNro());
                    }
                    JOptionPane.showMessageDialog(null, "Saved Correctly");
                    out.close();
                    loadFiles(open);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        saveAs.setVisible(true);
        file.add(saveAs);

        JMenu view = new JMenu("View");
        view.setVisible(true);
        menuBar.add(view);

        JMenu help = new JMenu("Help");
        view.setVisible(true);
        menuBar.add(help);

        JMenuItem clean = new JMenuItem("Clean");
        clean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingPanel.linkCount = 1;
                DrawingPanel.nodoCount = 0;
                DrawingPanel.links.clear();
                DrawingPanel.nodos.clear();
                DrawingPanel.graph.clear();
                nombreArchivo = null;
                canvas.repaint();
            }
        });
        clean.setVisible(true);
        view.add(clean);

        menu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!menuActivated) {
                    menu.setText(".    .    .");
                    matrix.setVisible(true);
                    johnson.setVisible(true);
                    menuTail.setVisible(true);
                    menuActivated = true;
                }
                else{
                    menu.setText("...");
                    matrix.setVisible(false);
                    johnson.setVisible(false);
                    menuTail.setVisible(false);
                    menuActivated = false;
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
        });

        matrix.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Vector<HashMap<Integer, Integer>> graph = DrawingPanel.graph;
                callMatrix();
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
        });

        johnson.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // crear Johnson
                Vector<HashMap<Integer, Integer>> graph = DrawingPanel.graph;
                callJohnson();
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
        });

        editionMenu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!editionActivated) {
                    editionMenu.setText(".    .    .");
                    editionMenu.setBounds(652, 35, 100, 50);
                    create.setVisible(true);
                    edit.setVisible(true);
                    delete.setVisible(true);
                    editionMenuTail.setVisible(true);
                    editionActivated = true;
                    if(mode.equals("create")){
                        create.setForeground(new Color(255, 0, 0));
                    }
                    else if(mode.equals("edit")){
                        edit.setForeground(new Color(255, 0, 0));
                    }
                    else if(mode.equals("delete")){
                        delete.setForeground(new Color(255, 0, 0));
                    }
                }
                else{
                    editionMenu.setText("...");
                    editionMenu.setBounds(710, 35, 100, 50);
                    create.setVisible(false);
                    edit.setVisible(false);
                    delete.setVisible(false);
                    editionMenuTail.setVisible(false);
                    editionActivated = false;
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
        });

        create.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mode = "create";
                create.setForeground(new Color(255,0,0));
                edit.setForeground((new Color(255,255,255)));
                delete.setForeground((new Color(255,255,255)));
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
        });

        edit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mode = "edit";
                create.setForeground(new Color(255, 255, 255));
                edit.setForeground((new Color(255, 0, 0)));
                delete.setForeground((new Color(255,255,255)));
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
        });

        delete.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mode = "delete";
                create.setForeground(new Color(255, 255, 255));
                edit.setForeground((new Color(255,255,255)));
                delete.setForeground((new Color(255, 0, 0)));
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
        });

    }

    public static void loadFiles(JMenu root){
        root.removeAll();
        String name = "files.spt";
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(name));
            String file;
            while(in.available() != 0){
                file = in.readUTF();
                System.out.println("Archivo: " + file);
                JMenuItem item = new JMenuItem(file);
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            nombreArchivo = item.getText();
                            System.out.println("Nombre: "+ item.getText());
                            String nombre = item.getText();
                            DataInputStream leer = new DataInputStream(new FileInputStream(nombre));
                            System.out.println("Se esta leyendo...  ");
                            DrawingPanel.nodos.clear();
                            DrawingPanel.links.clear();
                            DrawingPanel.graph.clear();
                            boolean links = false, nodos = false, grafo = false;
                            String first = leer.readUTF();
                            String data[] = first.split(":");
                            String name = data[0];
                            int size = Integer.parseInt(data[1]);
                            System.out.println(name + "->" + size);
                            for(int i =0; i<size; i++){
                                int X1 = leer.readInt();
                                int Y1 = leer.readInt();
                                int X2 = leer.readInt();
                                int Y2 = leer.readInt();
                                String nombreLink = leer.readUTF();
                                int origen = leer.readInt();
                                int destino = leer.readInt();
                                String nOrigen = leer.readUTF();
                                String nDestino = leer.readUTF();
                                int id = leer.readInt();
                                Link readed = new Link(X1, Y1, X2, Y2, origen, destino, nOrigen, nDestino, nombreLink, id);
                                DrawingPanel.links.add(readed);
                                System.out.println("Links leido correctamente");
                            }
                            String second = leer.readUTF();
                            String data2[] = second.split(":");
                            String name2 = data2[0];
                            int size2 = Integer.parseInt(data2[1]);
                            System.out.println(name2 + "->" + size2);
                            for(int i =0; i<size2; i++){
                                int X = leer.readInt();
                                int Y = leer.readInt();
                                String nombreNodo = leer.readUTF();
                                int nro = leer.readInt();
                                Nodo readed = new Nodo(X, Y, nombreNodo, nro);
                                DrawingPanel.nodos.add(readed);
                                System.out.println("Nodo leido correctamente");
                            }
                            canvas.repaint();
                            generateGraph();
                            } catch (FileNotFoundException notFoundException) {
                            notFoundException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
                item.setVisible(true);
                root.add(item);
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Aun no existe el archivo files.spt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void generateGraph(){
        Vector<HashMap<Integer, Integer>> newGraph = new Vector<HashMap<Integer, Integer>>();
        for (Nodo nodo: DrawingPanel.nodos) {
            newGraph.add(new HashMap<Integer, Integer>());
        }
        for(Link link: DrawingPanel.links) {
            int start = link.getOrigen();
            int end = link.getDestino();
            newGraph.get(start).put(end, Integer.parseInt(link.getNombre()));
        }
        DrawingPanel.graph = newGraph;
    }

    public static void callMatrix(){
        Matrix frame = new Matrix(DrawingPanel.graph);
        frame.setVisible(true);
    }

    public static void callJohnson(){
        Johnson frame = new Johnson(DrawingPanel.graph);
        frame.setVisible(true);
    }
*/
}