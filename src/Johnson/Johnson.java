package Johnson;

import Main.DrawingPanel;
import Main.MainFrame;
import Themes.MainTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class Johnson extends JFrame {
    private JPanel contentPane;
    MainTheme theme;

    private int width = 1100, height = 600;

    public Johnson(String title, MainTheme theme) throws HeadlessException {
        super(title);
        this.theme = theme;

        setSize(width, height);
        setIconImage(MainFrame.logo);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(theme.getzBackground());

        Details detailsPane = new Details(theme);
        detailsPane.setBounds(801, 0, 299, 270);
        contentPane.add(detailsPane);

        Pad interactivePad = new Pad(theme);
        interactivePad.setBounds(801, 271, 299, 349);
        contentPane.add(interactivePad);

        Path criticalPath = new Path(theme, DrawingPanel.graph, detailsPane, interactivePad);
        criticalPath.setBounds(0,0, 800, 600);
        contentPane.add(criticalPath);
    }
}
