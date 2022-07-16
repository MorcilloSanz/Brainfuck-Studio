package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TabComponent extends JPanel {

    private JLabel labelIcon;
    private JLabel label, labelSave;
    private int index;

    boolean save;

    public TabComponent(int index, final ImageIcon icon, final String title, JButton closeButton) {
        this.index = index;
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

        labelIcon = new JLabel();
        labelIcon.setIcon(icon);
        labelIcon.setBorder(new EmptyBorder(2, 0, 0, 0));

        label = new JLabel(title);
        label.setBorder(new EmptyBorder(8, 0, 0, 0));

        labelSave = new JLabel(".");
        labelSave.setForeground(Color.blue);

        closeButton.setBorder(new EmptyBorder(5, 0, 0, 0));

        add(labelIcon);
        add(Box.createHorizontalStrut(5));
        add(label);
        add(Box.createHorizontalStrut(5));
        add(labelSave);
        add(Box.createHorizontalStrut(5));
        add(closeButton);

        setOpaque(false);
    }

    public void setSave(boolean save) {
        this.save = save;
        labelSave.setText((save) ? "" : ".");
    }

    public boolean isSaved() {
        return save;
    }

    public void setTitle(final String title) {
        label.setText(title);
    }

    public int getIndex() {
        return index;
    }
}
