package Sorts;

import Main.MainFrame;
import Themes.MainTheme;

import javax.swing.*;
import java.util.Vector;

public class SortingPanel extends JPanel {
    // Main Attributes
    private int width, height;

    // Logic Part
    public static Sort sorter;
    Vector<Integer> data;

    // Theme
    MainTheme theme;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public SortingPanel(int width, int height) {
        sorter = new Sort(data);
        this.width = width;
        this.height = height;
        this.theme = MainFrame.theme;
        setBackground(theme.getBackground());
    }

    public void reset(){
        sorter = new Sort(data);
    }

    public Vector<Integer> getData() {
        return data;
    }

    public void setData(Vector<Integer> data) {
        this.data = data;
        System.out.println("Sorting panel data: " + data);
    }


}
