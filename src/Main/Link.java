package Main;

import Themes.MainTheme;

import java.awt.*;
import java.awt.geom.*;

public class Link{
    private int x1, y1, x2, y2;
    private int calculedX1, calculedY1, calculedX2, calculedY2;
    private String nombre;
    private int id;
    public double theta;
    private int origen, destino;
    private String nOrigen, nDestino;
    private boolean prioridad = false;

    private MainTheme theme;

    public String getnOrigen() {
        return nOrigen;
    }

    public void setnOrigen(String nOrigen) {
        this.nOrigen = nOrigen;
    }

    public String getnDestino() {
        return nDestino;
    }

    public void setnDestino(String nDestino) {
        this.nDestino = nDestino;
    }

    public int getLinkX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getLinkY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getLinkX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getLinkY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public String getNombre(){ return nombre; }

    public void setNombre(String nombre){ this.nombre = nombre; }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPrioridad() {
        return prioridad;
    }

    public void setPrioridad(boolean prioridad) {
        this.prioridad = prioridad;
    }

    public int getCalculedX1() {
        return calculedX1;
    }

    public void setCalculedX1(int calculedX1) {
        this.calculedX1 = calculedX1;
    }

    public int getCalculedY1() {
        return calculedY1;
    }

    public void setCalculedY1(int calculedY1) {
        this.calculedY1 = calculedY1;
    }

    public int getCalculedX2() {
        return calculedX2;
    }

    public void setCalculedX2(int calculedX2) {
        this.calculedX2 = calculedX2;
    }

    public int getCalculedY2() {
        return calculedY2;
    }

    public void setCalculedY2(int calculedY2) {
        this.calculedY2 = calculedY2;
    }

    public Link(int x1, int y1, int x2, int y2, int origen, int destino, String nOrigen, String nDestino, String nombre, int id) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.nombre = nombre;
        this.origen = origen;
        this.destino = destino;
        this.nOrigen = nOrigen;
        this.nDestino = nDestino;
        this.id = id;
    }

    public Link(int x1, int y1, int x2, int y2, int origen, int destino, String nombre, int id, boolean prioridad) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.nombre = nombre;
        this.origen = origen;
        this.destino = destino;
        this.prioridad = prioridad;
        this.id = id;
    }

    public void paint(Graphics g, int connections){
        theme = MainFrame.theme;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        if(prioridad)
            g2d.setColor(new Color(96, 232, 96));
        else
            g2d.setColor(new Color(0,0,0));

        // se van a calcular los nuevos puntos de desplazamiento con trigonometria
        double alfa = Math.atan2((y2-y1) , (x2-x1));
        double dx = Math.cos(alfa) * (Nodo.diametro/2);
        double dy = Math.sin(alfa) * (Nodo.diametro/2);
        theta = alfa;
        //double degrees = Math.toDegrees(alfa);
        //System.out.println("Angulo: " + degrees);

        setCalculedX1((int)(x1 + dx));
        setCalculedY1((int)(y1 + dy));
        setCalculedX2((int)(x2 - dx));
        setCalculedY2((int)(y2 - dy));

        if(connections > 1) {
            drawCurveArrow(g2d, (int) (x1 + dx), (int) (y1 + dy), (int) (x2 - dx), (int) (y2 - dy));
        }
        else
            drawArrow(g2d, (int)(x1+dx), (int)(y1+dy), (int)(x2-dx), (int)(y2-dy));

        //selfCurve(g);
        //curve(g2d);
    }

    public void paint(Graphics g, boolean self){
        theme = MainFrame.theme;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        if(prioridad)
            g2d.setColor(theme.getCriticPath());
        else
            g2d.setColor(theme.getGoing());

        //g2d.setColor(new Color(55, 188, 255));
        CubicCurve2D c = new CubicCurve2D.Float(x1-Nodo.diametro/2, y1, x1-Nodo.diametro/2-35, y1-15, x1-Nodo.diametro/2+5, y1-55, x1, y1-Nodo.diametro/2);
        g2d.draw(c);

        setCalculedX1(x1-Nodo.diametro/2);
        setCalculedY1(y1);
        setCalculedX2(x1);
        setCalculedY2(y1-Nodo.diametro/2);

        //g2d.draw(new Rectangle2D.Float((int)(x1-Nodo.diametro/2), y1, 3.0f, 3.0f));
        //g2d.draw(new Rectangle2D.Float(x1-Nodo.diametro/2-35, y1-15, 3.0f, 3.0f));
        //g2d.draw(new Rectangle2D.Float(x1-Nodo.diametro/2+5, y1-55, 3.0f, 3.0f));
        //g2d.draw(new Rectangle2D.Float(x1, y1-Nodo.diametro/2, 3.0f, 3.0f));


        double alfa=Math.atan2(y1-Nodo.diametro/2-y1,x1-x1-Nodo.diametro/2);
        //g.drawLine(x1-Nodo.diametro/2, y1, x1, y1-Nodo.diametro/2);
        int k=5;
        int xa=(int)(x1-Nodo.diametro/2-k*Math.cos(alfa+1));
        int ya=(int)(y1-k*Math.sin(alfa+1));
        // Se dibuja un extremo de la dirección de la flecha.
        g.drawLine(xa,ya,x1-Nodo.diametro/2,y1);
        xa=(int)(x1-Nodo.diametro/2-k*Math.cos(alfa-1));
        ya=(int)(y1-k*Math.sin(alfa-1));
        // Se dibuja el otro extremo de la dirección de la flecha.
        //g.drawLine(xa, ya, x1-Nodo.diametro/2, y1);
        //g2d.draw(new Rectangle2D.Float(x1-Nodo.diametro/2, y1, 3.0f, 3.0f));

        g2d.setFont(new Font("Helvetica", Font.BOLD, 12));
        g2d.drawString(nombre, x1-Nodo.diametro/2-15, y1 - 35);

    }

    public void drawArrow(Graphics2D g, int x0,int y0,int x1,int y1){
        theme = MainFrame.theme;
        if (prioridad)
            g.setColor(theme.getCriticPath());
        else
            g.setColor(theme.getGoing());

        double alfa=Math.atan2(y1-y0,x1-x0);
        g.drawLine(x0,y0,x1,y1);

        int k=5;
        int xa=(int)(x1-k*Math.cos(alfa+1));
        int ya=(int)(y1-k*Math.sin(alfa+1));
        // Se dibuja un extremo de la dirección de la flecha.
        g.drawLine(xa,ya,x1,y1);
        xa=(int)(x1-k*Math.cos(alfa-1));
        ya=(int)(y1-k*Math.sin(alfa-1));
        // Se dibuja el otro extremo de la dirección de la flecha.
        g.drawLine(xa,ya,x1,y1);

        // Peso
        g.setFont(new Font("Helvetica", Font.BOLD, 12));
        if(x1>x2 && y1 > y2){
            g.drawString(nombre, x0 - Math.abs((x0-x1)/2), y0 - Math.abs((y0-y1)/2) + 15);
        }
        if(x1 < x2 && y1 < y2){
            g.drawString(nombre, x1 - Math.abs((x0-x1)/2), y1 - Math.abs((y0-y1)/2) + 15);
        }
        if(x1 > x2 && y1 < y2){
            g.drawString(nombre, x0 - Math.abs((x0-x1)/2), y1 - Math.abs((y0-y1)/2) + 15);
        }
        if(x1 < x2 && y1 > y2){
            g.drawString(nombre, x1 - Math.abs((x0-x1)/2), y0 - Math.abs((y0-y1)/2) + 15);
        }

    }

    public void drawCurveArrow(Graphics2D g, int x0,int y0,int x1,int y1){
        theme = MainFrame.theme;
        g.setStroke(new BasicStroke(2));
        double alfa=Math.atan2(y1-y0,x1-x0);

        double deltaX =  Math.abs(Math.cos(theta));
        double deltaY =  1-Math.abs(Math.cos(theta));
        System.out.println("Alpha: " + theta);
        System.out.println("cos Theta: " + deltaX);
        System.out.println("opuesto Theta: " + deltaY);
        System.out.println();
        System.out.println("Porcentaje en x: " + deltaX*30);
        System.out.println("Porcentaje opuesto: " + deltaY*30);
        System.out.println();


        int ctrlx = 50, ctrly = 50;
        //g.draw(new Rectangle2D.Float(ctrlx, ctrly, 2.0f, 2.0f));
        if(x0>x1 && y0 > y1){
            // segundo punto en diagonal derecha inferior
            // de 3 a 6
            //System.out.println("1. x0>x1 && y0 > y1");
            ctrlx = x0 - Math.abs((x0-x1)/2) + (int)(deltaY*30);
            ctrly = y0 - Math.abs((y0-y1)/2) - (int)(deltaX*30);
            //g.draw(new Rectangle2D.Float(x0 - Math.abs((x0-x1)/2) + (int)(deltaY*30), y0 - Math.abs((y0-y1)/2) - (int)(deltaX*30), 1.0f, 1.0f));
        }
        if(x0 < x1 && y0 < y1){
            // segundo punto en diagonal izquierda superior
            // de 9 a 12
            //System.out.println("2. x0 < x1 && y0 < y1");
            ctrlx = x1 - Math.abs((x0-x1)/2) - (int)(deltaY*30);
            ctrly = y1 - Math.abs((y0-y1)/2) + (int)(deltaX*30);
            //g.draw(new Rectangle2D.Float(x1 - Math.abs((x0-x1)/2) - (int)(deltaY*30), y1 - Math.abs((y0-y1)/2) + (int)(deltaX*30) , 1.0f, 1.0f));
        }
        if(x0 > x1 && y0 < y1){
            // segundo punto en diagonal derecha superior
            // de 12 a 3
            //System.out.println("3. x0 > x1 && y0 < y1");
            ctrlx = x0 - Math.abs((x0-x1)/2) - (int)(deltaY*30);
            ctrly = y1 - Math.abs((y0-y1)/2) - (int)(deltaX*30);
            //g.draw(new Rectangle2D.Float(x0 - Math.abs((x0-x1)/2) - (int)(deltaY*30), y1 - Math.abs((y0-y1)/2) - (int)(deltaX*30), 1.0f, 1.0f));
        }
        if(x0 < x1 && y0 > y1){
            // segundo punto en diagonal izquierda inferior
            // de 6 a 9
            //System.out.println("4. x0 < x1 && y0 > y1");
            ctrlx = x1 - Math.abs((x0-x1)/2) + (int)(deltaY*30);
            ctrly = y0 - Math.abs((y0-y1)/2) + (int)(deltaX*30);
            //g.draw(new Rectangle2D.Float(x1 - Math.abs((x0-x1)/2) + (int)(deltaY*30), y0 - Math.abs((y0-y1)/2) + (int)(deltaX*30), 1.0f, 1.0f));
        }

        if(x0 == x1 && y0 > y1){ // vertical completa
            //System.out.println("5. x0 == x1 && y0 > y1");
            ctrlx = x1 + (int)(deltaY*30);
            ctrly = y0 - Math.abs((y0-y1)/2) + (int)(deltaX);
            //g.draw(new Rectangle2D.Float(x1 + (int)(deltaY*30), y0 - Math.abs((y0-y1)/2) + (int)(deltaX), 3.0f, 3.0f));
        }
        if(x0 == x1 && y0 < y1){ // vertical completa
            //System.out.println("6. x0 == x1 && y0 < y1");
            ctrlx = x1 - (int)(deltaY*30);
            ctrly = y1 - Math.abs((y0-y1)/2) + (int)(deltaX);
            //g.draw(new Rectangle2D.Float(x1 - (int)(deltaY*30), y1 - Math.abs((y0-y1)/2) + (int)(deltaX), 1.0f, 1.0f));
        }
        if (y0 == y1 && x0 < x1){ // horizontal completa
            //System.out.println("7. y0 == y1 && x0 < x1");
            ctrlx = x1 - Math.abs((x0-x1)/2)+ (int)(deltaY);
            ctrly = y1+(int)(deltaX*30);
            //g.draw(new Rectangle2D.Float(x1 - Math.abs((x0-x1)/2)+ (int)(deltaY), y1+(int)(deltaX*30), 1.0f, 1.0f));
        }
        if (y0 == y1 && x0 > x1){ // horizontal completa
            //System.out.println("8. y0 == y1 && x0 > x1");
            ctrlx = x0 - Math.abs((x0-x1)/2) + (int)(deltaY);
            ctrly = y1-(int) (deltaX*30);
            //g.draw(new Rectangle2D.Float(x0 - Math.abs((x0-x1)/2) + (int)(deltaY), y1-(int) (deltaX*30), 1.0f, 1.0f));
        }
        if (prioridad)
            g.setColor(theme.getCriticPath());
        else
            g.setColor(theme.getReturning());
        QuadCurve2D q = new QuadCurve2D.Float(x0, y0, ctrlx, ctrly, x1, y1);
        g.draw(q);

        /*
        System.out.println("Punto 1:\n" + "x: "+x0 + "  y: "+y0);
        System.out.println();
        System.out.println("Punto 2:\n" + "x: "+x1 + "  y: "+y1);
        System.out.println();
        System.out.println("Control de curva:\n" + "x: "+ctrlx + "  y: "+ctrly);
        System.out.println();
        */
        int k=5;
        int xa=(int)(x1-k*Math.cos(alfa+1.3));
        int ya=(int)(y1-k*Math.sin(alfa+1.3));
        // Se dibuja un extremo de la dirección de la flecha.
        g.drawLine(xa,ya,x1,y1);
        xa=(int)(x1-k*Math.cos(alfa-1));
        ya=(int)(y1-k*Math.sin(alfa-1));
        // Se dibuja el otro extremo de la dirección de la flecha.
        g.drawLine(xa,ya,x1,y1);

        // Peso
        g.setFont(new Font("Helvetica", Font.BOLD, 12));
        if(x1>x2 && y1 > y2){
            g.drawString(nombre, x0 - Math.abs((x0-x1)/2), y0 - Math.abs((y0-y1)/2) - 20);
        }
        if(x1 < x2 && y1 < y2){
            g.drawString(nombre, x1 - Math.abs((x0-x1)/2), y1 - Math.abs((y0-y1)/2) - 20);
        }
        if(x1 > x2 && y1 < y2){
            g.drawString(nombre, x0 - Math.abs((x0-x1)/2), y1 - Math.abs((y0-y1)/2) - 20);
        }
        if(x1 < x2 && y1 > y2){
            g.drawString(nombre, x1 - Math.abs((x0-x1)/2), y0 - Math.abs((y0-y1)/2) - 20);
        }
    }

    public void curve (Graphics2D g2) {
        g2.setColor(Color.blue);
        g2.setStroke(new BasicStroke(3.0f));
        QuadCurve2D q = new QuadCurve2D.Float(40.0f, 70.0f, 40.0f, 170.0f, 190.0f, 220.0f);
        g2.draw(q);
        g2.setColor(Color.red);
        g2.draw(new Rectangle2D.Float(40.0f, 70.0f, 1.0f, 1.0f));
        g2.draw(new Rectangle2D.Float(40.0f, 170.0f, 1.0f, 1.0f));
        g2.draw(new Rectangle2D.Float(190.0f, 220.0f, 1.0f, 1.0f));
    }

    public void selfCurve (Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.blue);
        g2.setStroke(new BasicStroke(3.0f));
        CubicCurve2D c = new CubicCurve2D.Float(40.0f, 210.0f, 42.0f, 185.f, 58.0f, 185.0f, 60.0f, 210.0f);
        g2.draw(c);
        g2.setColor(Color.red);
        g2.draw(new Rectangle2D.Float(40.0f,210.0f, 1.0f, 1.0f));
        g2.draw(new Rectangle2D.Float(42.0f, 185.0f, 1.0f, 1.0f));
        g2.draw(new Rectangle2D.Float(58.0f, 185.0f, 1.0f, 1.0f));
        g2.draw(new Rectangle2D.Float(60.0f, 210.0f, 1.0f, 1.0f));
    }
}
