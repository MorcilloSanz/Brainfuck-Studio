package gui;

import brainfuck.Brainfuck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Debugger extends JPanel {

    public static final int MAX_FIELDS = 40;
    public static final int SIZE = 30;

    public static final int FONT_SIZE = 32;

    private ArrayList<JTextField> memoryFields = new ArrayList<>();

    private JButton buttonLeft, buttonRight;

    private JPanel panelCells;

    private Brainfuck brainfuck;

    private int pointer;

    public Debugger() {
        super();
        setLayout(new BoxLayout(this ,BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel labelMemory = new JLabel("Output ");
        labelMemory.setFont(new Font(BrainfuckStudio.FONT, 0, 32));
        labelMemory.setForeground(Color.decode("#c999ff"));
        add(labelMemory);
        panelCells = new JPanel();
        panelCells.setLayout(new BoxLayout(panelCells ,BoxLayout.X_AXIS));
        panelCells.setBorder(null);
        generateMemoryFields();
    }

    private void generateMemoryFields() {
        for(int i = 0; i < MAX_FIELDS; i ++) {
            JTextField textField = new JTextField("0");
            textField.setBorder(null);
            textField.setFont(new Font(BrainfuckStudio.FONT, 0, FONT_SIZE));
            textField.setDisabledTextColor(Color.white);
            textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, SIZE));
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.setEnabled(false);
            memoryFields.add(textField);
            panelCells.add(textField);
        }
        add(panelCells);

        buttonLeft = new JButton();
        buttonLeft.setSize(new Dimension(24, 24));
        buttonLeft.setIcon(new ImageIcon(this.getClass().getResource("/resources/leftArrow.png")));
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateMemoryFields();
            }
        });
        //add(buttonLeft);

        buttonRight = new JButton();
        buttonRight.setSize(new Dimension(24, 24));
        buttonRight.setIcon(new ImageIcon(this.getClass().getResource("/resources/rightArrow.png")));
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateMemoryFields();
            }
        });
        //add(buttonRight);
    }

    private void updateMemoryFields() {
        if(brainfuck == null) return;

        int index = 0;
        for(byte b : brainfuck.getBuffer()) {

            index ++;
        }
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
        pointer = pos;
        for(JTextField textField : memoryFields) {
            if(index == pointer)
                memoryFields.get(index).setBackground(Color.decode("#c999ff"));
            else {
                memoryFields.get(index).setBackground(Color.decode("#333333"));
                memoryFields.get(index).setDisabledTextColor(Color.decode("#FFFFFF"));
            }
            index ++;
        }
    }

    public void setBrainfuck(Brainfuck brainfuck) {
        this.brainfuck = brainfuck;
    }

    public int length() {
        return memoryFields.size();
    }
}
