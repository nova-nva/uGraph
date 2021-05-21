package Themes;

import java.awt.*;

public class PhoenixDark extends MainTheme{
    // General
    private Color background = new Color(50, 46, 46);
    private Color zBackground = new Color(255, 255, 255);
    private Color label = new Color(255, 255, 255);
    private Color selectedMenu = new Color(255, 0, 0);

    // Links
    private Color going = new Color(55, 188, 255);
    private Color returning = new Color(255, 228, 65);
    private Color criticPath = new Color(96, 232, 96);

    // Nodes
    private Color node = new Color(236, 97, 97, 255);

    // Johnson
    private Color johnsonAttributes = new Color(226, 74, 255);

    public PhoenixDark() {

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
