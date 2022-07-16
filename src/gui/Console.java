package gui;

import brainfuck.Brainfuck;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Console extends JTextArea {

    private Brainfuck brainfuck;

    public Console() {
        super();
        setFont(new Font(BrainfuckStudio.FONT, 0, Debugger.FONT_SIZE));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) { }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    String[] lines = getText().split("\n");
                    if(lines.length > 0) {
                        String input = lines[lines.length - 1];
                        if(brainfuck != null)
                            brainfuck.setInput(input);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) { }
        });
    }

    public void setBrainfuck(Brainfuck brainfuck) {
        this.brainfuck = brainfuck;
    }

    public void log(final String msg) {
        insert(msg, getText().length());
    }

    public void select() {
        Border border = BorderFactory.createLineBorder(Color.decode("#c999ff"));
        setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        requestFocus();
    }

    public void deselect() {
        Border border = BorderFactory.createLineBorder(Color.decode("#222222"));
        setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    public void reset() {
        deselect();
        setText("");
    }
}
