package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ToolBar extends JPanel {

    public ToolBar() {
        super();
        setBorder(new EmptyBorder(0, 10, 0, 10));
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
    }

}
