package gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class Editor extends JTextPane {

    public static final int DEFAULT_FONT_SIZE = 45;

    private String text;
    private int fontSize = 0;

    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet moveKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(144, 255, 125));
    final AttributeSet valueKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(201, 153, 255));
    final AttributeSet loopKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 82, 255));
    final AttributeSet consoleKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 0, 43));
    final AttributeSet comment = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.decode("#FFFFFF"));

    private TabComponent tabComponent;

    public Editor(final String text) throws BadLocationException {
        super();
        this.text = text;
        fontSize = DEFAULT_FONT_SIZE;
        setFontSize(DEFAULT_FONT_SIZE);
        setMargin( new Insets(10,10,10,10) );
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
        setDocument(doc);
    }

    public void setTabComponent(TabComponent tabComponent) {
        this.tabComponent = tabComponent;
    }

    public void setFontSize(int size) {
        fontSize = size;
        setFont(new Font("Cloude Regular 1.0", 0, size));
    }

    public int getFontSize() {
        return fontSize;
    }
}
