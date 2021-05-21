package Themes;

import java.awt.*;

public class MainTheme {
    // General
    private Color background = null;
    private Color zBackground = null;
    private Color label = null;
    private Color selectedMenu = null;

    // Links
    private Color going = null;
    private Color returning = null;
    private Color criticPath = null;

    // Nodes
    private Color node = null;

    // Johnson
    private Color johnsonAttributes = null;

    public MainTheme() {

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

    public Color getJohnsonAttributes() {
        return johnsonAttributes;
    }

    public void setJohnsonAttributes(Color johnsonAttributes) {
        this.johnsonAttributes = johnsonAttributes;
    }
}
