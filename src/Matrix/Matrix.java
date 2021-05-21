package Matrix;

import Themes.MainTheme;
import Main.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class Matrix extends JFrame {
    private JPanel contentPane;
    MainTheme theme;

    private int width = 400, height = 300;

    private Vector<HashMap<Integer, Integer>> graph;

    public Matrix(String title, MainTheme theme, Vector<HashMap<Integer, Integer>> graph) throws HeadlessException {
        super(title);
        this.theme = theme;
        this.graph = graph;

        setSize(width, height);
        setIconImage(MainFrame.logo);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(theme.getBackground());


        String cad = "";

        cad+="\t";
        for(int i = 0; i< DrawingPanel.nodos.size(); i++){
            if(i== DrawingPanel.nodos.size()-1)
                cad += DrawingPanel.nodos.get(i).getNombre() + "\n\n";
            else
                cad += DrawingPanel.nodos.get(i).getNombre() + "\t";
        }

        for(int j = 0; j<this.graph.size(); j++){
            cad += DrawingPanel.nodos.get(j).getNombre() + "\t";
            String peso;
            int valorHorizontal = 0;
            for(int k = 0; k<this.graph.size(); k++) {
                if (this.graph.get(j).containsKey(k)) {
                    peso = String.valueOf(this.graph.get(j).get(k));
                    valorHorizontal += this.graph.get(j).get(k);
                }
                else
                    peso = "-";
                cad += peso + "\t";
            }
            cad += valorHorizontal + "\n\n";
        }

        cad += "\t";
        for(int j = 0; j<this.graph.size(); j++){
            int valorVertical = 0;
            for(int k = 0; k<this.graph.size(); k++) {
                if (this.graph.get(k).containsKey(j)) {
                    valorVertical += this.graph.get(k).get(j);
                }
            }
            cad += valorVertical + "\t";
        }
        cad += "\n\n";

        JTextArea jt = new JTextArea();
        jt.setFont(new Font("Helvetica", Font.BOLD, 12));;
        jt.setText(cad);
        jt.setBackground(theme.getBackground());
        jt.setForeground(theme.getLabel());
        jt.setEditable(false);
        //contentPane.add(jt);

        JScrollPane js = new JScrollPane(jt);
        js.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js.setBounds(20, 20, 1600, 800);
        js.setBackground(theme.getBackground());
        js.setForeground(theme.getLabel());
        js.setBorder(new EmptyBorder(0,0,0,0));
        js.setFont(new Font("Helvetica", Font.BOLD, 12));
        contentPane.add(js);
    }
}