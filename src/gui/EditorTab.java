package gui;

import javax.swing.*;
import javax.swing.text.BadLocationException;

public class EditorTab extends JPanel {

    private Editor editor;
    private TransparentScrollPane transparentScrollPane;
    private String filePath, fileName;

    public EditorTab(final String text) throws BadLocationException {
       setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
       editor = new Editor(text);
       transparentScrollPane = new TransparentScrollPane(editor);
       add(transparentScrollPane);
    }

    public Editor getEditor() {
        return editor;
    }

    public void setFilePath(final String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
