package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Debugger extends JPanel {

    public static final int MAX_FIELDS = 35;
    public static final int SIZE = 30;

    private ArrayList<JTextField> memoryFields = new ArrayList<>();

    private JPanel panelCells;

    public Debugger() {
        super();
        setLayout(new BoxLayout(this ,BoxLayout.X_AXIS));

        panelCells = new JPanel();
        panelCells.setLayout(new BoxLayout(panelCells ,BoxLayout.X_AXIS));
        generateMemoryFields();
    }

    private void generateMemoryFields() {
        for(int i = 0; i < MAX_FIELDS; i ++) {
            JTextField textField = new JTextField("0");
            textField.setFont(new Font("Cloude Regular 1.0", 0, 40));
            textField.setDisabledTextColor(Color.white);
            textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, SIZE));
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.setEnabled(false);
            memoryFields.add(textField);
            panelCells.add(textField);
        }
        add(panelCells);
    }

    public void reset() {
        int index = 0;
        for(JTextField textField : memoryFields) {
            textField.setText("0");
            memoryFields.get(index).setBackground(Color.decode("#333333"));
            memoryFields.get(index).setForeground(Color.decode("#FFFFFF"));
            index ++;
        }

    }

    public void setValue(int pos, byte value) {
        memoryFields.get(pos).setText("" + value);
    }

    public void setActive(int pos) {
        int index = 0;
        for(JTextField textField : memoryFields) {
            if(index == pos) {
                memoryFields.get(index).setBackground(Color.decode("#c999ff"));
                memoryFields.get(index).setDisabledTextColor(Color.decode("#000000"));
            }
            else {
                memoryFields.get(index).setBackground(Color.decode("#333333"));
                memoryFields.get(index).setDisabledTextColor(Color.decode("#FFFFFF"));
            }

            index ++;
        }
    }
}
