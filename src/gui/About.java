package gui;

import javax.swing.*;

public class About extends JFrame {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final String TITLE = "About";

    private ImageIcon icon;

    public About() {
        setSize(WIDTH, HEIGHT);
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        icon = new ImageIcon(this.getClass().getResource("/resources/icon.png"));
        setIconImage(icon.getImage());
        setResizable(false);
        setLocationRelativeTo(null);
        addPanel();
        setVisible(true);
        pack();
    }

    private void addPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JLabel logo = new JLabel();
        logo.setIcon(icon);
        panel.add(logo);

        panel.add(Box.createHorizontalStrut(10));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
        panel2.add(Box.createVerticalStrut(5));
        JLabel info = new JLabel("Brainfuck Studio v 1.0");
        panel2.add(info);
        JLabel dev = new JLabel("Dev: Alberto Morcillo Sanz");
        panel2.add(dev);
        JLabel email = new JLabel("E-mail: amorcillosanz@gmail.com");
        panel2.add(email);

        panel.add(panel2);

        getContentPane().add(panel);
    }
}
