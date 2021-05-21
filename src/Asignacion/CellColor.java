package Asignacion;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Vector;

public class CellColor extends JTable {
    Vector<Vector<Integer>> results;
    boolean maximize;

    public CellColor(Object[][] data, String[] columnNames, Vector<Vector<Integer>> results, boolean maximize) {
        super(data, columnNames);
        this.results = new Vector<>(results);
        parseResults();
        this.maximize = maximize;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component component = super.prepareRenderer(renderer, row, column);

        if(isCritic(row, column)){
            if(maximize){
                component.setBackground(Color.red);
            }
            else {
                component.setBackground(Color.blue);
            }
            component.setForeground(Color.white);
        }
        else {
            component.setBackground(Color.white);
            component.setForeground(Color.black);
        }

        /*
        if(getValueAt(row, column).getClass().equals(Integer.class)){
            int valor = Integer.parseInt(this.getValueAt(row, column).toString());
            System.out.print(valor + " ");
            if(valor < 4){
                System.out.println("mayor a 4");
                component.setBackground(Color.red);
                component.setForeground(Color.white);
            }
            else {
                component.setBackground(Color.white);
                component.setForeground(Color.black);
            }
        }
        else {
            component.setBackground(Color.white);
            component.setForeground(Color.black);
        }

         */
        return component;
    }

    private boolean isCritic(int row, int column){
            for (int i = 0; i<results.size(); i++){
            if(results.get(i).get(0) == row && results.get(i).get(1) == column)
                return true;
        }
        return false;
    }

    private void parseResults(){
        for (int i = 0; i<results.size(); i++){
            Vector<Integer> aux = results.get(i);
            //aux.set(0, aux.get(0) + 1);
            aux.set(1, aux.get(1) + 1);

            results.set(i, aux);
        }
        System.out.println(results);
    }


}
