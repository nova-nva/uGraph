package Asignacion;

import Main.DrawingPanel;
import Main.MainFrame;
import Themes.MainTheme;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class SolvedTable extends JPanel {
    private Vector<HashMap<Integer, Integer>> graph;
    private MainTheme theme;
    private boolean maximize;

    int[][] dataMatrix;
    Vector<Vector<Integer>> solutionIndex, notAlteredSolutionIndex;
    int totalAttr;

    public SolvedTable(MainTheme theme, Vector<HashMap<Integer, Integer>> graph, boolean maximize){
        super(new GridLayout(1,0));

        this.theme = theme;
        this.graph = graph;
        this.maximize = maximize;

        int nroEmisores = 0, nroReceptores = 0;
        Vector<Integer> emisor = new Vector<>();
        Vector<Integer> receptor = new Vector<>();

        for(int i = 0; i<graph.size(); i++){
            System.out.print("Nodo: " + i + " ");
            System.out.println(graph.get(i) + " ");
            if(graph.get(i).size() != 0) {
                nroEmisores++;
                emisor.add(i);
            }
            else {
                nroReceptores++;
                receptor.add(i);
            }
        }
        System.out.println("Emisores: " + nroEmisores);
        System.out.println(emisor);
        System.out.println("Receptores: " + nroReceptores);
        System.out.println(receptor);

        int dimension = Math.max(nroEmisores, nroReceptores);

        // create data matrix
        dataMatrix = new int[dimension][dimension];
        for(int i = 0; i<dimension; i++){
            for(int j = 0; j<dimension; j++){
                if(nroReceptores < nroEmisores && j == receptor.size()){
                    dataMatrix[i][j] = 0;
                    continue;
                }
                if(nroReceptores > nroEmisores && i == emisor.size()){
                    dataMatrix[i][j] = 0;
                    continue;
                }
                dataMatrix[i][j] = graph.get(emisor.get(i)).get(receptor.get(j));
            }
        }

        if(!maximize){
            for(int i = 0; i<dimension; i++){
                for(int j = 0; j<dimension; j++){
                    dataMatrix[i][j] *= -1;
                }
            }
        }

        AsignationSolver solver = new AsignationSolver(dataMatrix);
        solutionIndex = solver.solve();
        System.out.println("Solution Inaltered: " + solutionIndex);
        setTotalAttribute(maximize);
        notAlteredSolutionIndex = new Vector<>(solutionIndex);

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

        if(!maximize){
            for(int i = 0; i<dimension; i++){
                for(int j = 0; j<dimension; j++){
                    dataMatrix[i][j] *= -1;
                }
            }
        }
        Object[][] data = new Object[nroEmisores][nroReceptores + 1];
        for(int i = 0; i<emisor.size(); i++){
            Vector<Object> preData = new Vector<>();
            for(int j = 0; j<receptor.size(); j++){
                preData.add(dataMatrix[i][j]);
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
        final JTable table = new CellColor(data, columnNames, new Vector<>(solutionIndex), maximize);
        table.setPreferredScrollableViewportSize(new Dimension(500, 80));
        table.setFillsViewportHeight(true);
        table.setEnabled(true);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);

        /*
        String[] columnNames = {"", "Examen A", "Examen B"};
        Object[][] data = {
                {"Estudiante 0", 5, 10},
                {"Estudiante 1", 2, 7},
                {"Estudiante 2", 10, 1}
        };

        Vector<Vector<Integer>> results = new Vector<>();
        Vector<Integer> one = new Vector<>();
        one.add(0);
        one.add(0);
        Vector<Integer> two = new Vector<>();
        two.add(1);
        two.add(0);
        Vector<Integer> three = new Vector<>();
        three.add(2);
        three.add(1);

        results.add(one);
        results.add(two);
        results.add(three);

        final JTable table = new CellColor(data, columnNames, results);
        TableCellEditor editor = table.getCellEditor();
        table.setPreferredScrollableViewportSize(new Dimension(500, 80));
        table.setFillsViewportHeight(true);
        table.setEnabled(true);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
        */
    }

    /*
    public static void main(String[] args){
        JFrame frame = new JFrame("Table Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SolvedTable table = new SolvedTable(MainFrame.theme, DrawingPanel.graph);
        frame.setContentPane(table);

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
     */

    public void setTotalAttribute(boolean maximize){
        int counter = 0;
        for (int i = 0; i<solutionIndex.size(); i++) {
            System.out.println(dataMatrix[solutionIndex.get(i).get(0)][solutionIndex.get(i).get(1)]);
            counter += dataMatrix[solutionIndex.get(i).get(0)][solutionIndex.get(i).get(1)];
        }
        System.out.println(counter);
        if(maximize)
            totalAttr = counter;
        else
            totalAttr = -counter;
    }

    public int getTotalAttribute(){
        return this.totalAttr;
    }

    private void printMatrix(int dimension, int[][] dataMatrix){
        // print
        System.out.println("Matriz resultante: ");
        for(int i = 0; i<dimension; i++){
            for(int j = 0; j<dimension; j++){
                System.out.print(dataMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
