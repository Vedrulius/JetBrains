package com.mihey.texteditor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextEditor extends JFrame {

    public static final String TITLE = "Text Editor";

    public TextEditor() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setTitle(TITLE);
        initComponents();
        setVisible(true);

    }

    private void initComponents() {

        JPanel panelTop = new JPanel();
        FlowLayout flowLayout = new FlowLayout(10, 10, 10);
        panelTop.setLayout(flowLayout);

        JTextField textField = new JTextField(20);
        textField.setName("FilenameField");

        JButton saveButton = new JButton("Save");
        saveButton.setName("SaveButton");

        JButton loadButton = new JButton("Load");
        loadButton.setName("LoadButton");

        panelTop.add(textField);
        panelTop.add(loadButton);
        panelTop.add(saveButton);

        JPanel panelMain = new JPanel();

        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");

        Dimension d = new Dimension(570, 300);
        scrollPane.setMinimumSize(d);
        scrollPane.setMaximumSize(d);
        scrollPane.setPreferredSize(d);

        panelMain.add(scrollPane);

        add(panelTop, BorderLayout.NORTH);
        add(panelMain, BorderLayout.CENTER);

        saveButton.addActionListener((event) -> {
            try(FileWriter output = new FileWriter(textField.getText())) {

                textArea.write(output);
            } catch (IOException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        });

        loadButton.addActionListener((event) -> {
            File file = new File(textField.getText());
            if (file.exists()) {
                try (FileReader input = new FileReader(textField.getText())) {

                    textArea.read(input, null);
                } catch (Exception ex) {
                    System.err.println("Error: " + ex.getMessage());
                }
            } else {
                textArea.setText("");
            }


        });

    }
    public static void main(String[] args) {
        new TextEditor();
    }
}