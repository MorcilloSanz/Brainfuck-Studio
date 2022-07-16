package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Help extends JFrame {

    public static final int WIDTH = 539;
    public static final int HEIGHT = 216;
    public static final String TITLE = "Help";

    private ImageIcon icon;

    public Help() {
        super();
        setSize(WIDTH, HEIGHT);
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        icon = new ImageIcon(this.getClass().getResource("/resources/icon.png"));
        setIconImage(icon.getImage());
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        addPanel();
        pack();
    }

    private void addPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        JLabel label = new JLabel();
        //label.setBounds(0, 0, WIDTH, HEIGHT);
        label.setIcon(new ImageIcon(this.getClass().getResource("/resources/help.png")));
        panel.add(label);
        getContentPane().add(panel);
    }
}
