package Main;

import Asignacion.Asignacion;
import Johnson.Johnson;
import Kramer.Kramer;
import Themes.MainTheme;
import Themes.PhoenixDark;
import Themes.PhoenixLight;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Vector;
import Matrix.Matrix;

public class MainFrame extends JFrame{
    private boolean menuActivated = false;
    private boolean editionActivated = false;
    public static String version = "[dvlp...] uGraph 7.0 (Beta)";
    public static BufferedImage logo;

    public static String mode = "create";
    private DrawingPanel canvas;

    public static MainTheme theme;
    private String temaElegido = "Dark";
    private String chosenWorkspace = "Graph";
    private Vector<JLabel> interactiveComps;

    private String nombreArchivo = null;

    public static boolean autoNamedNodes = true;

    public MainFrame(String chosenWorkspace){
        this.chosenWorkspace = chosenWorkspace;

        // Caracteristicas del frame
        setTitle(version);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLayout(null);

        // Logo
        File f = new File("Images/logo.png");
        //System.out.println(f.getAbsolutePath()); // Esto te da la ruta absoluta del file que buscas
        try {
            logo = ImageIO.read(new File(String.valueOf(f)));
            setIconImage(logo);
            // Path p = f.toPath();
            // Files.readAllBytes(p);
        }
        catch (Exception e){
            System.out.println("No se puede");
            e.printStackTrace();
        }

        // Configuraciones
        refreshThemeSettings(temaElegido);
        interactiveComps = new Vector<>();

        // Caracteristicas del panel
        canvas = new DrawingPanel();
        canvas.setLayout(null);
        canvas.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(canvas);

        // Los elementos del menu interactivo dependeran del workspace elegido
        switch (chosenWorkspace) {
            case "Graph" -> {
                // ELEMENTS
                JLabel menu = new JLabel("...");
                menu.setFont(new Font("Helvetica", Font.BOLD, 25));
                menu.setBounds(50, 10, 100, 50);
                pintarComponente(menu);
                add(menu);
                interactiveComps.add(menu);

                JLabel matrix = new JLabel("To Matrix");
                matrix.setFont(new Font("Helvetica", Font.BOLD, 15));
                matrix.setBounds(55, 65, 100, 50);
                pintarComponente(matrix);
                matrix.setVisible(false);
                add(matrix);
                interactiveComps.add(matrix);

                JLabel johnson = new JLabel("Johnson");
                johnson.setFont(new Font("Helvetica", Font.BOLD, 15));
                johnson.setBounds(55, 120, 100, 50);
                pintarComponente(johnson);
                johnson.setVisible(false);
                add(johnson);
                interactiveComps.add(johnson);

                JLabel asignation = new JLabel("Assignation");
                asignation.setFont(new Font("Helvetica", Font.BOLD, 15));
                asignation.setBounds(50, 175, 100, 50);
                pintarComponente(asignation);
                asignation.setVisible(false);
                add(asignation);
                interactiveComps.add(asignation);

                JLabel kramer = new JLabel("Kramer");
                kramer.setFont(new Font("Helvetica", Font.BOLD, 15));
                kramer.setBounds(60, 230, 100, 50);
                pintarComponente(kramer);
                kramer.setVisible(false);
                add(kramer);
                interactiveComps.add(kramer);

                JLabel menuTail = new JLabel(".    .    .");
                menuTail.setFont(new Font("Helvetica", Font.BOLD, 25));
                menuTail.setBounds(50, 275, 100, 50);
                pintarComponente(menuTail);
                menuTail.setVisible(false);
                add(menuTail);
                interactiveComps.add(menuTail);

                JLabel credits = new JLabel(version + " - © All Rights Reserved");
                credits.setFont(new Font("Helvetica", Font.BOLD, 15));
                credits.setBounds(255, 485, 500, 50);
                pintarComponente(credits);
                add(credits);
                interactiveComps.add(credits);

                JLabel editionMenu = new JLabel("...");
                editionMenu.setFont(new Font("Helvetica", Font.BOLD, 25));
                editionMenu.setBounds(710, 10, 100, 50);
                pintarComponente(editionMenu);
                add(editionMenu);
                interactiveComps.add(editionMenu);

                JLabel create = new JLabel("Create");
                create.setFont(new Font("Helvetica", Font.BOLD, 15));
                create.setBounds(667, 50, 100, 50);
                pintarComponente(create);
                create.setVisible(false);
                add(create);
                interactiveComps.add(create);

                JLabel edit = new JLabel("Edit");
                edit.setFont(new Font("Helvetica", Font.BOLD, 15));
                edit.setBounds(667, 80, 100, 50);
                pintarComponente(edit);
                edit.setVisible(false);
                add(edit);
                interactiveComps.add(edit);

                JLabel delete = new JLabel("Delete");
                delete.setFont(new Font("Helvetica", Font.BOLD, 15));
                delete.setBounds(667, 110, 100, 50);
                pintarComponente(delete);
                delete.setVisible(false);
                add(delete);
                interactiveComps.add(delete);

                JLabel editionMenuTail = new JLabel(".    .    .");
                editionMenuTail.setFont(new Font("Helvetica", Font.BOLD, 25));
                editionMenuTail.setBounds(652, 137, 100, 50);
                pintarComponente(editionMenuTail);
                editionMenuTail.setVisible(false);
                add(editionMenuTail);
                interactiveComps.add(editionMenuTail);

                // LISTENERS
                menu.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!menuActivated) {
                            menu.setText(".    .    .");
                            matrix.setVisible(true);
                            johnson.setVisible(true);
                            asignation.setVisible(true);
                            kramer.setVisible(true);
                            menuTail.setVisible(true);
                            menuActivated = true;
                        } else {
                            menu.setText("...");
                            matrix.setVisible(false);
                            johnson.setVisible(false);
                            asignation.setVisible(false);
                            kramer.setVisible(false);
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

                asignation.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        callAsignation();
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

                kramer.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        callKramer();
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
                        if (!editionActivated) {
                            editionMenu.setText(".    .    .");
                            editionMenu.setBounds(652, 10, 100, 50);
                            create.setVisible(true);
                            edit.setVisible(true);
                            delete.setVisible(true);
                            editionMenuTail.setVisible(true);
                            editionActivated = true;
                            if (mode.equals("create")) {
                                create.setForeground(theme.getSelectedMenu());
                            } else if (mode.equals("edit")) {
                                edit.setForeground(theme.getSelectedMenu());
                            } else if (mode.equals("delete")) {
                                delete.setForeground(theme.getSelectedMenu());
                            }
                        } else {
                            editionMenu.setText("...");
                            editionMenu.setBounds(710, 10, 100, 50);
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
                        create.setForeground(theme.getSelectedMenu());
                        edit.setForeground(theme.getLabel());
                        delete.setForeground(theme.getLabel());
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
                        create.setForeground(theme.getLabel());
                        edit.setForeground(theme.getSelectedMenu());
                        delete.setForeground(theme.getLabel());
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
                        create.setForeground(theme.getLabel());
                        edit.setForeground(theme.getLabel());
                        delete.setForeground(theme.getSelectedMenu());
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
            case "Tree" -> {
                JLabel menu = new JLabel("...");
                menu.setFont(new Font("Helvetica", Font.BOLD, 25));
                menu.setBounds(50, 10, 100, 50);
                pintarComponente(menu);
                add(menu);
                interactiveComps.add(menu);

                JLabel plain = new JLabel("Plain Input");
                plain.setFont(new Font("Helvetica", Font.BOLD, 15));
                plain.setBounds(50, 65, 100, 50);
                pintarComponente(plain);
                plain.setVisible(false);
                add(plain);
                interactiveComps.add(plain);

                JLabel txt = new JLabel(".txt Input");
                txt.setFont(new Font("Helvetica", Font.BOLD, 15));
                txt.setBounds(55, 120, 100, 50);
                pintarComponente(txt);
                txt.setVisible(false);
                add(txt);
                interactiveComps.add(txt);

                JLabel random = new JLabel("Random");
                random.setFont(new Font("Helvetica", Font.BOLD, 15));
                random.setBounds(60, 175, 100, 50);
                pintarComponente(random);
                random.setVisible(false);
                add(random);
                interactiveComps.add(random);

                JLabel menuTail = new JLabel(".    .    .");
                menuTail.setFont(new Font("Helvetica", Font.BOLD, 25));
                menuTail.setBounds(50, 220, 100, 50);
                pintarComponente(menuTail);
                menuTail.setVisible(false);
                add(menuTail);
                interactiveComps.add(menuTail);

                JLabel credits = new JLabel(version + " - © All Rights Reserved");
                credits.setFont(new Font("Helvetica", Font.BOLD, 15));
                credits.setBounds(255, 485, 500, 50);
                pintarComponente(credits);
                add(credits);
                interactiveComps.add(credits);

                // LISTENERS
                menu.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!menuActivated) {
                            menu.setText(".    .    .");
                            plain.setVisible(true);
                            txt.setVisible(true);
                            random.setVisible(true);
                            menuTail.setVisible(true);
                            menuActivated = true;
                        } else {
                            menu.setText("...");
                            plain.setVisible(false);
                            txt.setVisible(false);
                            random.setVisible(false);
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

                plain.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

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

                txt.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

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

                random.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

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
            case "Sort" -> {
                JLabel credits = new JLabel(version + " - © All Rights Reserved");
                credits.setFont(new Font("Helvetica", Font.BOLD, 15));
                credits.setBounds(255, 485, 500, 50);
                pintarComponente(credits);
                add(credits);
                interactiveComps.add(credits);
            }
            case "Compet" -> {
                JLabel credits = new JLabel(version + " - © All Rights Reserved");
                credits.setFont(new Font("Helvetica", Font.BOLD, 15));
                credits.setBounds(255, 485, 500, 50);
                pintarComponente(credits);
                add(credits);
                interactiveComps.add(credits);
            }
        }


        JMenuBar menuBar = new JMenuBar();
        //menuBar.setBackground(new Color(0,0,0));
        menuBar.setBounds(0, 0, 800,25);
        menuBar.setVisible(true);
        setJMenuBar(menuBar);

        JMenu workspace = new JMenu("Workspace");
        workspace.setVisible(true);
        menuBar.add(workspace);

        JRadioButtonMenuItem graph = new JRadioButtonMenuItem("Graph");
        if(chosenWorkspace.equals("Graph")){
            graph.setSelected(true);
        }
        workspace.add(graph);
        graph.setVisible(true);

        JRadioButtonMenuItem tree = new JRadioButtonMenuItem("Tree");
        if(chosenWorkspace.equals("Tree")){
            tree.setSelected(true);
        }
        workspace.add(tree);
        tree.setVisible(true);

        JRadioButtonMenuItem sort = new JRadioButtonMenuItem("Sort");
        if(chosenWorkspace.equals("Sort")){
            sort.setSelected(true);
        }
        workspace.add(sort);
        sort.setVisible(true);

        JRadioButtonMenuItem compet = new JRadioButtonMenuItem("Compet");
        if(chosenWorkspace.equals("Compet")){
            compet.setSelected(true);
        }
        workspace.add(compet);
        compet.setVisible(true);

        graph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.setSelected(true);
                tree.setSelected(false);
                sort.setSelected(false);
                compet.setSelected(false);

                MainFrame newFrame = new MainFrame("Graph");
                newFrame.setVisible(true);
                setVisible(false);
            }
        });

        tree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.setSelected(false);
                tree.setSelected(true);
                sort.setSelected(false);
                compet.setSelected(false);

                MainFrame newFrame = new MainFrame("Tree");
                newFrame.setVisible(true);
                setVisible(false);
            }
        });

        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.setSelected(false);
                tree.setSelected(false);
                sort.setSelected(true);
                compet.setSelected(false);

                MainFrame newFrame = new MainFrame("Sort");
                newFrame.setVisible(true);
                setVisible(false);
            }
        });

        compet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.setSelected(false);
                tree.setSelected(false);
                sort.setSelected(false);
                compet.setSelected(true);

                MainFrame newFrame = new MainFrame("Compet");
                newFrame.setVisible(true);
                setVisible(false);
            }
        });

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
                String filesLog = "config/files.spt";
                try {
                    // Comprobar que el archivo existe
                    DataInputStream in = new DataInputStream(new FileInputStream(filesLog));
                    String file, nombre;
                    boolean exists = false;
                    while (in.available() != 0) {
                        file = in.readUTF();
                        if (file.equals(nombreArchivo)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        JOptionPane.showMessageDialog(null, "Imposible guardar cambios de un archivo inexistente\nUtilice 'Save as...' si es la primera vez que guarda el archivo");
                        return;
                    }
                }catch (Exception exception) {
                    System.out.println("Aun no existe el archivo config/files.spt");
                    JOptionPane.showMessageDialog(null, "Imposible guardar cambios de un archivo inexistente\nUtilice 'Save as...' si es la primera vez que guarda el archivo");
                    return;
                }

                try{
                    // Registrar el archivo
                    String savePath = "Files/" + nombreArchivo;
                    DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath, false));
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
                String filesLog = "config/files.spt";
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
                    while (in.available() != 0) {
                        file = in.readUTF();
                        if (file.equals(nombre + ".gph")) {
                            JOptionPane.showMessageDialog(null, "No se pueden guardar dos archivos con el mismo nombre");
                            return;
                        }
                    }
                }catch (Exception exe){
                    System.out.println("Aun no existe archivo pa comprobar");
                }

                try{
                    // Registrar el archivo
                    DataOutputStream generalOut = new DataOutputStream(new FileOutputStream(filesLog, true));
                    generalOut.writeUTF(nombre + ".gph");
                    generalOut.close();
                    String savePath = "Files/" + nombre + ".gph";
                    nombreArchivo = nombre + ".gph";
                    DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath, false));
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

        JMenu settings = new JMenu("Settings");
        settings.setVisible(true);
        menuBar.add(settings);

        JMenu themeSetting = new JMenu("Theme");
        themeSetting.setVisible(true);
        settings.add(themeSetting);

        JRadioButtonMenuItem darkPhoenix = new JRadioButtonMenuItem("Dark Phoenix");
        if(temaElegido.equals("Dark")){
            darkPhoenix.setSelected(true);
        }
        darkPhoenix.setVisible(true);
        themeSetting.add(darkPhoenix);

        JRadioButtonMenuItem lightPhoenix = new JRadioButtonMenuItem("Light Phoenix");
        if(temaElegido.equals("Light")){
            lightPhoenix.setSelected(true);
        }
        lightPhoenix.setVisible(true);
        themeSetting.add(lightPhoenix);

        darkPhoenix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temaElegido = "Dark";
                darkPhoenix.setSelected(true);
                lightPhoenix.setSelected(false);
                refreshThemeSettings("Dark");
                for (JLabel item: interactiveComps)
                    pintarComponente(item);
                repaint();
                canvas.repaint();
            }
        });
        lightPhoenix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temaElegido = "Light";
                darkPhoenix.setSelected(false);
                lightPhoenix.setSelected(true);
                refreshThemeSettings("Light");
                for (JLabel item: interactiveComps)
                    pintarComponente(item);
                repaint();
                canvas.repaint();
                canvas.paint(canvas.getGraphics());
            }
        });

        JCheckBoxMenuItem autoNamed = new JCheckBoxMenuItem("Auto-named nodes");
        autoNamed.setSelected(true);
        autoNamed.setVisible(true);
        autoNamed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(autoNamedNodes){
                    autoNamedNodes = false;
                    autoNamed.setSelected(false);
                }
                else{
                    autoNamedNodes = true;
                    autoNamed.setSelected(true);
                }
            }
        });
        settings.add(autoNamed);

        JMenu view = new JMenu("View");
        view.setVisible(true);
        menuBar.add(view);

        /*JMenu help = new JMenu("Help");
        view.setVisible(true);
        menuBar.add(help);*/

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
    }

    private void loadFiles(JMenu root){
        root.removeAll();
        String name = "config/files.spt";
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
                            String nombre = "Files/" + item.getText();
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
                            //canvas.repaint();
                            repaint();
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
            System.out.println("Aun no existe el archivo config/files.spt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void generateGraph(){
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

    private void pintarComponente(JLabel comp){
        comp.setForeground(theme.getLabel());
    }

    private void refreshThemeSettings(String chosenOption){
        if(chosenOption.equals("Dark"))
            theme = new PhoenixDark();
        else if (chosenOption.equals("Light"))
            theme = new PhoenixLight();
    }

    /*
    private void writeThemeConfig(String newTheme) throws IOException {
        DataOutputStream generalOut = new DataOutputStream(new FileOutputStream("config.spt", false));
        generalOut.writeUTF(newTheme);
        generalOut.close();
    }

    private void readThemeConfig() throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream("config.spt"));
        while(in.available() != 0){
            temaElegido = in.readUTF();
        }
        in.close();
    }
    */

    // Calls

    private void callMatrix(){
        JFrame frame = new Matrix(version + " - Graph to Matrix", theme, DrawingPanel.graph);
        frame.setVisible(true);
    }

    private void callJohnson(){
        Johnson frame = new Johnson(version + " - Johnson Algorithm", theme);
        frame.setVisible(true);
    }

    private void callAsignation(){
        String option = JOptionPane.showInputDialog("Seleccione una opción:\n1. Maximizar\n2. Minimizar");
        int op = Integer.parseInt(option);

        Asignacion frame;
        if(op == 1){
            frame = new Asignacion(version + " - Assignation Algorithm", theme, true);
        }
        else if(op == 2){
            frame = new Asignacion(version + " - Assignation Algorithm", theme, false);
        }
        else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un numero de las opciones");
            return;
        }
        frame.setVisible(true);
    }

    private void callKramer(){
        String option = JOptionPane.showInputDialog("Seleccione una opción:\n1. Maximizar\n2. Minimizar");
        int op = Integer.parseInt(option);

        Kramer frame;
        if(op == 1){
            frame = new Kramer(version + " - Kramer Algorithm", theme, false);
        }
        else if(op == 2){
            frame = new Kramer(version + " - Kramer Algorithm", theme, true);
        }
        else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un numero de las opciones");
            return;
        }
        frame.setVisible(true);
    }
}