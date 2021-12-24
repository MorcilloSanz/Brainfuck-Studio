package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmPane extends JDialog {

    private String text;
    private JPanel panel;

    public static final int YES = 1;
    public static final int NO = 0;
    private int response;

    public ConfirmPane(final String title, final String text) {
        super();
        response = 0;
        this.text = text;
        setTitle(title);
        setLocationRelativeTo(null);
        setModal(true);
        initComponents();
        pack();
        setVisible(true);
    }

    private void initComponents() {

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelLabel = new JPanel();
        panelLabel.setLayout(new BoxLayout(panelLabel,BoxLayout.X_AXIS));

        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(this.getClass().getResource("/resources/icon.png")));
        panelLabel.add(iconLabel);

        panelLabel.add(Box.createHorizontalStrut(25));

        JLabel label = new JLabel(text);
        label.setForeground(Color.decode("#c999ff"));
        panelLabel.add(label);

        panel.add(panelLabel);

        panel.add(Box.createVerticalStrut(5));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton buttonYes = new JButton("Yes");
        buttonYes.setForeground(new Color(144, 255, 125));
        buttonYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                response = ConfirmPane.YES;
                dispose();
            }
        });
        buttonPanel.add(buttonYes);

        buttonPanel.add(Box.createHorizontalStrut(25));

        JButton buttonNo = new JButton("No");
        buttonNo.setForeground(new Color(255, 87, 87));
        buttonNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                response = ConfirmPane.NO;
                dispose();
            }
        });
        buttonPanel.add(buttonNo);

        buttonPanel.add(Box.createHorizontalStrut(25));

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(buttonCancel);

        panel.add(buttonPanel);

        this.getContentPane().add(panel);
    }

    public int getResponse() {
        return response;
    }
}
