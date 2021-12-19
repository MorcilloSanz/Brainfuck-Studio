package gui;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class StylesLoader {

    public static void loadDark() {

        // Menu bar
        UIManager.put("MenuBar.background", Color.decode("#333333"));
        UIManager.put("MenuBar.selectionBackground", Color.decode("#c999ff"));
        UIManager.put("MenuBar.border", BorderFactory.createLineBorder(Color.decode("#333333"), 0));
        // Menu
        UIManager.put("Menu.background", Color.decode("#333333"));
        UIManager.put("Menu.selectionBackground", Color.decode("#c999ff"));
        UIManager.put("Menu.foreground", Color.decode("#FFFFFF"));
        UIManager.put("Menu.selectionForeground", Color.decode("#000000"));
        UIManager.put("Menu.border", BorderFactory.createLineBorder(Color.decode("#333333"), 0));
        UIManager.put("Menu.selectionForeground", Color.decode("#000000"));
        // Menu Item
        UIManager.put("MenuItem.background", Color.decode("#333333"));
        UIManager.put("MenuItem.selectionBackground", Color.decode("#c999ff"));
        UIManager.put("MenuItem.foreground", Color.decode("#FFFFFF"));
        UIManager.put("MenuItem.selectionForeground", Color.decode("#000000"));
        UIManager.put("MenuItem.border", BorderFactory.createLineBorder(Color.decode("#333333"), 0));
        // Pop menu
        UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(Color.decode("#333333"), 0));
        UIManager.put("PopupMenu.foreground", Color.decode("#000000"));
        // Button
        UIManager.put("Button.background", Color.decode("#333333"));
        UIManager.put("Button.foreground", Color.decode("#FFFFFF"));
        UIManager.put("Button.selectionForeground", Color.decode("#c999ff"));
        UIManager.put("Button.border", BorderFactory.createLineBorder(Color.decode("#333333"), 0));
        UIManager.put("Button.select", false);
        // Panel
        UIManager.put("Panel.background", Color.decode("#333333"));
        UIManager.put("Panel.border", BorderFactory.createLineBorder(Color.decode("#333333"), 1));
        // Split pane
        UIManager.put("SplitPane.background", Color.decode("#333333"));
        UIManager.put("SplitPane.foreground", Color.decode("#c999ff"));
        UIManager.put("SplitPane.border", BorderFactory.createLineBorder(Color.decode("#333333"), 1));
        UIManager.put("SplitPane.dividerSize", 2);
        // Text pane
        UIManager.put("TextPane.background", Color.decode("#000000"));
        UIManager.put("TextPane.foreground", Color.decode("#FFFFFF"));
        UIManager.put("TextPane.border", BorderFactory.createLineBorder(Color.decode("#333333"), 0));
        UIManager.put("TextPane.caretForeground", Color.decode("#FFFFFF"));
        UIManager.put("TextPane.selectionForeground", Color.decode("#000000"));
        UIManager.put("TextPane.selectionBackground", Color.decode("#c999ff"));
        // Text area
        UIManager.put("TextArea.background", Color.decode("#000000"));
        UIManager.put("TextArea.foreground", Color.decode("#FFFFFF"));
        UIManager.put("TextArea.border", BorderFactory.createLineBorder(Color.decode("#333333"), 0));
        UIManager.put("TextArea.caretForeground", Color.decode("#FFFFFF"));
        UIManager.put("TextArea.selectionForeground", Color.decode("#000000"));
        UIManager.put("TextArea.selectionBackground", Color.decode("#c999ff"));
        // TextField
        UIManager.put("TextField.background", Color.decode("#333333"));
        UIManager.put("TextField.foreground", Color.decode("#FFFFFF"));
        UIManager.put("TextField.border", BorderFactory.createLineBorder(Color.decode("#222222"), 1));
        // Scroll pane
        UIManager.put("ScrollPane.background", Color.decode("#333333"));
        UIManager.put("ScrollPane.foreground", Color.decode("#FFFFFF"));
        UIManager.put("ScrollPane.border", BorderFactory.createLineBorder(Color.decode("#222222"), 0));
        // Scroll bar
        UIManager.put("ScrollBar.background", new ColorUIResource(Color.decode("#333333")));
    }
}
