package Compet;

import Main.DrawingPanel;
import Main.MainFrame;
import Themes.MainTheme;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class Compet extends JFrame {
    private JPanel contentPane;
    MainTheme theme;

    private int width = 400, height = 150;

    private Vector<HashMap<Integer, Integer>> graph;

    public Compet(String title, MainTheme theme, Vector<HashMap<Integer, Integer>> graph) throws HeadlessException {
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

        Vector<Vector<Double>> data = new Vector<>();
        int size = DrawingPanel.nodos.size();
        for(int i = 0; i<DrawingPanel.nodos.size(); i++){
            // multi input for x, y
            JTextField xField = new JTextField(5);
            JTextField yField = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("X:"));
            myPanel.add(xField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Y:"));
            myPanel.add(yField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    DrawingPanel.nodos.get(i).getNombre() + " data", JOptionPane.DEFAULT_OPTION);

            double x=0, y=0;
            try {
                x = Double.parseDouble(xField.getText());
                y = Double.parseDouble(yField.getText());
                Vector<Double> currentData = new Vector<>();
                currentData.add(x);
                currentData.add(y);
                data.add(currentData);
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, "Invalid Input!");
                return;
            }
        }
        // agregar los primeros al final
        Vector<Double> aux = new Vector<>();
        aux.add(data.get(0).get(0));
        aux.add(data.get(0).get(1));
        data.add(aux);

        // hallar el centroide
        while(!xConsistency(data, size) || !yConsistency(data, size)){
            for(int i = 0; i<size; i++){
                // System.out.println(data.get(i).get(0) + " + " + data.get(i+1).get(0) + " / 2");
                data.get(i).set(0, ((data.get(i).get(0) + data.get(i+1).get(0)) / 2));
                data.get(i).set(1, ((data.get(i).get(1) + data.get(i+1).get(1)) / 2));
            }
            data.get(size).set(0, data.get(0).get(0));
            data.get(size).set(1, data.get(0).get(1));
            System.out.println(data);
        }

        // mostrar resultados
        System.out.println("X: " + data.get(0).get(0));
        System.out.println("Y: " + data.get(0).get(1));

        JLabel results = new JLabel("Results");
        results.setForeground(theme.getLabel());
        results.setFont(new Font("Helvetica", Font.BOLD, 15));
        results.setBounds(width / 2 - 30, 5, 100, 50);
        results.setVisible(true);
        contentPane.add(results);

        JLabel x = new JLabel("X: " + data.get(0).get(0));
        x.setForeground(theme.getLabel());
        x.setFont(new Font("Helvetica", Font.BOLD, 13));
        x.setBounds(120, 50, 100, 50);
        x.setVisible(true);
        contentPane.add(x);

        JLabel y = new JLabel("Y: " + data.get(0).get(1));
        y.setForeground(theme.getLabel());
        y.setFont(new Font("Helvetica", Font.BOLD, 13));
        y.setBounds(220, 50, 100, 50);
        y.setVisible(true);
        contentPane.add(y);
    }

    private boolean xConsistency(Vector<Vector<Double>> data, int size){
        for(int i = 0; i<size; i++){
            if(!data.get(i).get(0).equals(data.get(i+1).get(0))){
                return false;
            }
        }
        return true;
    }

    private boolean yConsistency(Vector<Vector<Double>> data, int size){
        for(int i = 0; i<size; i++){
            if(!data.get(i).get(1).equals(data.get(i + 1).get(1))){
                return false;
            }
        }
        return true;
    }
}
