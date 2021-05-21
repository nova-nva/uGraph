package Kramer;

import Main.DrawingPanel;
import Themes.MainTheme;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class SolvedTable extends JPanel {
    private Vector<HashMap<Integer, Integer>> graph;
    private MainTheme theme;
    private boolean minimize;

    private int[][] dataMatrix;
    private int[][] solutionMatrix;
    private Vector<Vector<Integer>> solutionIndex;
    private int totalAttr;

    public SolvedTable(MainTheme theme, Vector<HashMap<Integer, Integer>> graph, boolean minimize) {
        super(new GridLayout(1, 0));

        this.graph = graph;
        this.theme = theme;
        this.minimize = minimize;

        int nroEmisores = 0, nroReceptores = 0;
        Vector<Integer> emisor = new Vector<>();
        Vector<Integer> receptor = new Vector<>();

        Vector<String> emisorNames = new Vector<>();
        Vector<String> receptorNames = new Vector<>();

        for(int i = 0; i<graph.size(); i++){
            System.out.print("Nodo: " + i + " ");
            System.out.println(graph.get(i) + " ");
            if(graph.get(i).size() != 0) {
                nroEmisores++;
                emisor.add(i);
                emisorNames.add(DrawingPanel.nodos.get(i).getNombre());
            }
            else {
                nroReceptores++;
                receptor.add(i);
                receptorNames.add(DrawingPanel.nodos.get(i).getNombre());
            }
        }
        System.out.println("Emisores: " + nroEmisores);
        System.out.println(emisor);
        System.out.println("Receptores: " + nroReceptores);
        System.out.println(receptor);
        System.out.println("Emisores Nombre: " + emisorNames);
        System.out.println(emisor);
        System.out.println("Receptores Nombre: " + receptorNames);
        System.out.println(receptor);

        int rows = nroEmisores;
        int cols = nroReceptores;

        // create data matrix
        dataMatrix = new int[rows][cols];
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                dataMatrix[i][j] = graph.get(emisor.get(i)).get(receptor.get(j));
            }
        }
        printMatrix(dataMatrix, rows, cols);

        // check supplies
        JPanel suppliesPanel = new JPanel();
        Vector<JTextField> suppliesFields = new Vector<>();
        for(int i = 0; i<rows; i++){
            JTextField field = new JTextField(5);
            suppliesFields.add(field);

            suppliesPanel.add(new JLabel(emisorNames.get(i)));
            suppliesPanel.add(field);
            suppliesPanel.add(Box.createHorizontalStrut(15));
        }

        int[] supplies = new int[rows];
        int totalSupplies = 0;

        int result = JOptionPane.showConfirmDialog(null, suppliesPanel, "Enter Supplies", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            for(int i = 0; i<suppliesFields.size(); i++){
                // System.out.println(emisor.get(i) + ": " + suppliesFields.get(i).getText());
                supplies[i] = Integer.parseInt(suppliesFields.get(i).getText());
                totalSupplies += supplies[i];
            }
        }

        // check demands
        JPanel demandsPanel = new JPanel();
        Vector<JTextField> demandsFields = new Vector<>();
        for(int i = 0; i<cols; i++){
            JTextField field = new JTextField(5);
            demandsFields.add(field);

            demandsPanel.add(new JLabel(receptorNames.get(i)));
            demandsPanel.add(field);
            demandsPanel.add(Box.createHorizontalStrut(15));
        }

        int[] demands = new int[cols];
        int totalDemands = 0;

        int result2 = JOptionPane.showConfirmDialog(null, demandsPanel, "Enter Demands", JOptionPane.OK_CANCEL_OPTION);
        if(result2 == JOptionPane.OK_OPTION){
            for(int i = 0; i<demandsFields.size(); i++){
                // System.out.println(receptor.get(i) + ": " + demandsFields.get(i).getText());
                demands[i] = Integer.parseInt(demandsFields.get(i).getText());
                totalDemands += demands[i];
            }
        }

        if(totalSupplies == totalDemands){
            // JOptionPane.showMessageDialog(null, "JALA!");
            // TODO: Solve the problem
            KramerSolver solver = new KramerSolver(dataMatrix, supplies, demands, minimize);
            solutionIndex = solver.solve();
            totalAttr = solver.getTotalAttribute();
            solutionMatrix = solver.getSolutionValues();

            // generar vista de la tabla
            Vector<String> preColumn = new Vector<>();
            preColumn.add("");
            for(int i = 0; i<receptor.size(); i++){
                preColumn.add(DrawingPanel.nodos.get(receptor.get(i)).getNombre());
            }

            System.out.println("Column names: ");
            String[] columnNames = new String[preColumn.size()];
            for(int k = 0; k<preColumn.size(); k++){
                columnNames[k] = preColumn.get(k);
                System.out.print(columnNames[k] + "  ");
            }
            System.out.println();

            Object[][] data = new Object[nroEmisores][nroReceptores + 1];
            for(int i = 0; i<emisor.size(); i++){
                Vector<Object> preData = new Vector<>();
                for(int j = 0; j<receptor.size(); j++){
                    preData.add(dataMatrix[i][j] + " => " + solutionMatrix[i][j]);
                }
                preData.add(0, DrawingPanel.nodos.get(emisor.get(i)).getNombre());
                for(int k = 0; k<preData.size(); k++){
                    data[i][k] = preData.get(k);
                }
            }

            System.out.println("Data Object: ");
            for(int k = 0; k<data.length; k++){
                for(int h = 0; h<data[0].length; h++){
                    System.out.print(data[k][h] + "  ");
                }
                System.out.println();
            }
            System.out.println();

            // create table
            final JTable table = new CellColor(data, columnNames, new Vector<>(solutionIndex), minimize);
            table.setPreferredScrollableViewportSize(new Dimension(500, 80));
            table.setFillsViewportHeight(true);
            table.setEnabled(true);

            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane);
        }
        else{
            JOptionPane.showMessageDialog(null, "Ups! We're still working with non equal supplies/demands.\nWait for the update!");
        }

    }

    private void printMatrix(int[][] dataMatrix, int rows, int cols){
        // print
        System.out.println("Matriz resultante: ");
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                System.out.print(dataMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private void multiInputs(){
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("x:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("y:"));
        myPanel.add(yField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("x value: " + xField.getText());
            System.out.println("y value: " + yField.getText());
        }
    }

    private void multiInputsAlternative(){
        JTextField username = new JTextField();
        JTextField password = new JPasswordField();
        Object[] message = {
                "Username:", username,
                "Password:", password
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (username.getText().equals("h") && password.getText().equals("h")) {
                System.out.println("Login successful");
            } else {
                System.out.println("login failed");
            }
        } else {
            System.out.println("Login canceled");
        }
    }

    public int getTotalAttribute(){
        return this.totalAttr;
    }
}
