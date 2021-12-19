package gui;

import javax.swing.*;
import java.awt.*;

public class Console extends JTextArea {

    Console() {
        super();
        setFont(new Font("Cloude Regular 1.0", 0, 30));
    }

    public void log(final String msg) {
        insert(msg, getText().length());
    }

    public void reset() {
        setText("");
    }
}
