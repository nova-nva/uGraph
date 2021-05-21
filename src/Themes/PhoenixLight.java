package Themes;

import java.awt.*;

public class PhoenixLight extends MainTheme{
    // General
    private Color background = new Color(255, 255, 255);
    private Color zBackground = new Color(0, 0, 0);
    private Color label = new Color(0, 0, 0);
    private Color selectedMenu = new Color(241, 70, 70);

    // Links
    private Color going = new Color(5, 35, 108);
    private Color returning = new Color(0, 255, 165);
    private Color criticPath = new Color(0, 255, 0);

    // Nodes
    private Color node = new Color(245, 66, 66, 255);

    // Johnson
    private Color johnsonAttributes = new Color(186, 47, 245);

    public PhoenixLight() {

    }

    public Color getBackground() {
        return background;
    }

    public Color getLabel() {
        return label;
    }

    public Color getSelectedMenu() {
        return selectedMenu;
    }

    public Color getGoing() {
        return going;
    }

    public Color getReturning() {
        return returning;
    }

    public Color getCriticPath() {
        return criticPath;
    }

    public Color getNode() {
        return node;
    }

    public Color getzBackground() {
        return zBackground;
    }

    public void setzBackground(Color zBackground) {
        this.zBackground = zBackground;
    }

    @Override
    public Color getJohnsonAttributes() {
        return johnsonAttributes;
    }

    @Override
    public void setJohnsonAttributes(Color johnsonAttributes) {
        this.johnsonAttributes = johnsonAttributes;
    }
}
