package gui;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Editor extends JTextPane {

    public static final int DEFAULT_FONT_SIZE = 32;

    private String text;
    private int fontSize = 0;

    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet moveKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(144, 255, 125));
    final AttributeSet valueKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(201, 153, 255));
    final AttributeSet loopKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 82, 255));
    final AttributeSet consoleKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 0, 43));
    final AttributeSet comment = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.decode("#FFFFFF"));

    private TabComponent tabComponent;
    private UndoManager undoManager;

    public Editor(final String text) throws BadLocationException {
        super();
        this.text = text;
        fontSize = DEFAULT_FONT_SIZE;
        setFontSize(DEFAULT_FONT_SIZE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        initDocument();
    }

    private void initDocument() throws BadLocationException {
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);
                for(int i = 0; i < str.length(); i ++) {
                    char c = str.charAt(i);
                    if(c == '>' || c == '<')
                        setCharacterAttributes(offset + i, 1, moveKeyword, false);
                    else if(c == '+' || c == '-')
                        setCharacterAttributes(offset + i, 1, valueKeyword, false);
                    else if(c == '[' || c == ']')
                        setCharacterAttributes(offset + i, 1, loopKeyword, false);
                    else if(c == '.' || c == ',')
                        setCharacterAttributes(offset + i, 1, consoleKeyword, false);
                    else
                        setCharacterAttributes(offset + i, 1, comment, false);
                }
                tabComponent.setSave(false);
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
            }

        };
        // Set undo listener
        undoManager = new UndoManager();
        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent undoableEditEvent) {
                undoManager.addEdit(undoableEditEvent.getEdit());
            }
        });
        getActionMap().put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(undoManager.canUndo())
                        undoManager.undo();
                }catch (CannotUndoException e) {
                    e.printStackTrace();
                }
            }
        });
        getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
        // Set current document
        setDocument(doc);
        // Set default text
        try {
            doc.insertString(doc.getLength(), text, null);
        }catch (Exception e) {}
    }

    public void setTabComponent(TabComponent tabComponent) {
        this.tabComponent = tabComponent;
    }

    public void setFontSize(int size) {
        fontSize = size;
        setFont(new Font(BrainfuckStudio.FONT, 0, size));
    }

    public int getFontSize() {
        return fontSize;
    }
}
