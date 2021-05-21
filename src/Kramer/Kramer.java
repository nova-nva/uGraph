package Kramer;

import Main.DrawingPanel;
import Main.MainFrame;
import Themes.MainTheme;

import javax.swing.*;
import java.awt.*;

public class Kramer extends JFrame {
    private JPanel contentPane;
    MainTheme theme;

    String title;
    private final int width = 800, height = 400;

    boolean minimize;

    public Kramer(String title, MainTheme theme, boolean minimize) throws HeadlessException {
        super(title);
        this.theme = theme;
        this.minimize = minimize;
        this.title = title;

        setSize(width, height);
        setIconImage(MainFrame.logo);
        setLocationRelativeTo(null);
        setResizable(true);

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(theme.getBackground());

        JLabel mainTitle = new JLabel((minimize) ? "Minimized Kramer" : "Maximized Kramer");
        mainTitle.setForeground(theme.getLabel());
        mainTitle.setFont(new Font("Helvetica", Font.BOLD, 15));
        mainTitle.setBounds(50, 20, width, 50);
        mainTitle.setVisible(true);
        contentPane.add(mainTitle);

        SolvedTable solvedTable = new SolvedTable(theme, DrawingPanel.graph, minimize);
        solvedTable.setBounds(50, 80, width - 50*2, 100);
        contentPane.add(solvedTable);

        // show total attribute
        JLabel totalAttribute = new JLabel("Total Attribute: " + solvedTable.getTotalAttribute());
        totalAttribute.setForeground(theme.getLabel());
        totalAttribute.setFont(new Font("Helvetica", Font.PLAIN, 12));
        totalAttribute.setBounds(width/2 - 55, 185, width, 50);
        totalAttribute.setVisible(true);
        add(totalAttribute);

    }
}
