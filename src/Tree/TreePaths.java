package Tree;

import Main.DrawingPanel;
import Main.MainFrame;
import Themes.MainTheme;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class TreePaths extends JFrame{
    private JPanel contentPane;
    MainTheme theme;

    private int width = 550, height = 520;

    private Tree tree;

    public TreePaths(String title, MainTheme theme, Tree tree) throws HeadlessException {
        super(title);
        this.theme = theme;
        this.tree = tree;

        setSize(width, height);
        setIconImage(MainFrame.logo);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(theme.getBackground());

        tree.resetStrings();

        JLabel label1 = new JLabel("In Order");
        label1.setForeground(theme.getLabel());
        label1.setFont(new Font("Helvetica", Font.BOLD, 15));
        label1.setBounds(15, 5, 100, 50);
        label1.setVisible(true);
        contentPane.add(label1);

        JTextArea inOrder = new JTextArea(tree.inOrder(tree.getRoot()));
        inOrder.setEditable(false);
        JScrollPane inOrderScroll = new JScrollPane(inOrder);
        inOrderScroll.setBounds(15,         50, 500, 100);
        add(inOrderScroll);

        JLabel label2 = new JLabel("Pre Order");
        label2.setForeground(theme.getLabel());
        label2.setFont(new Font("Helvetica", Font.BOLD, 15));
        label2.setBounds(15, 150, 100, 50);
        label2.setVisible(true);
        contentPane.add(label2);

        JTextArea preOrder = new JTextArea(tree.preOrder(tree.getRoot()));
        preOrder.setEditable(false);
        JScrollPane preOrderScroll = new JScrollPane(preOrder);
        preOrderScroll.setBounds(15, 200, 500, 100);
        add(preOrderScroll);

        JLabel label3 = new JLabel("Post Order");
        label3.setForeground(theme.getLabel());
        label3.setFont(new Font("Helvetica", Font.BOLD, 15));
        label3.setBounds(15, 300, 500, 50);
        label3.setVisible(true);
        contentPane.add(label3);

        JTextArea postOrder = new JTextArea(tree.postOrder(tree.getRoot()));
        postOrder.setEditable(false);
        JScrollPane postOrderScroll = new JScrollPane(postOrder);
        postOrderScroll.setBounds(15, 350, 500, 100);
        add(postOrderScroll);
    }
}
