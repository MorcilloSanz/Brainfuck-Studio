package gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class Editor extends JTextPane {

    public static final int DEFAULT_FONT_SIZE = 45;

    private StyleContext style;
    private AttributeSet textStyle;

    private int fontSize = 0;

    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet moveKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(144, 255, 125));//23,161,165 || 211 84 0 || 94,109,3
    final AttributeSet valueKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(201, 153, 255));
    final AttributeSet loopKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 82, 255));
    final AttributeSet consoleKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 0, 43));
    final AttributeSet comment = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.decode("#FFFFFF"));

    public Editor() {
        super();
        fontSize = DEFAULT_FONT_SIZE;
        setFontSize(DEFAULT_FONT_SIZE);
        setMargin( new Insets(10,10,10,10) );
        initDocument();
    }

    private void initDocument() {
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
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
            }

        };
        setDocument(doc);
    }

    public void setFontSize(int size) {
        fontSize = size;
        setFont(new Font("Cloude Regular 1.0", 0, size));
    }

    public int getFontSize() {
        return fontSize;
    }
}
