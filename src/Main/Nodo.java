package Main;

import Themes.MainTheme;

import java.awt.*;

public class Nodo {
    private int x,y; // coordenadas
    public static final int diametro = 40;

    private String nombre;
    private int nro;

    private MainTheme theme;

    public Nodo(int x, int y, String nombre, int nro){
        this.x = x;
        this.y = y;
        this.nombre = nombre;
        this.nro = nro;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void paint(Graphics g){
        theme = MainFrame.theme;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(theme.getNode());
        g2d.fillOval(this.x - diametro/2, this.y - diametro/2, diametro, diametro);
        g2d.setColor(theme.getLabel());
        g2d.setFont(new Font("Helvetica", Font.BOLD, 12));
        g2d.drawString(nombre, x - (nombre.length()*3), y+4);
    }
}
