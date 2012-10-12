package com.elvis.graph.generator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by User: el
 * Date: 12.10.12
 * Time: 0:37
 */
public class GeneratorController {
    public static final int maxEdgeLength = 100;
    GeneratorView view;

    public GeneratorController(GeneratorView view) {
        this.view = view;
    }

    public void init() {
        view.getGenerateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDirectory();
                if (directoryForSave != null) {
                    generateAndSaveGraph();
                    JOptionPane.showMessageDialog(view.getContentPane(), "graphs generated");
                }
            }
        });
    }

    File directoryForSave;

    private void selectDirectory() {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showSaveDialog(view.getContentPane());
        directoryForSave = chooser.getSelectedFile();
    }

    Random rand = new Random(System.nanoTime());


    private void generateAndSaveGraph() {
        int count = (Integer) view.getSpinnerCount().getValue();
        int size = (Integer) view.getSpinnerSize().getValue();
        for (int i = 0; i < count; i++) {
            float[][] matrix = new float[size][size];
            for (int k = 0; k < size; k++) {
                matrix[k][k] = 1;
                for (int j = k + 1; j < size; j++) {
                    matrix[k][j] = matrix[j][k] = rand.nextFloat() * maxEdgeLength;
                }
            }
            FileOutputStream outputStream = null;
            try {
                String path = directoryForSave.getAbsolutePath() + "\\test_" + Arrays.deepHashCode(matrix) + ".graph";
                System.out.println(path);
                outputStream = new FileOutputStream(path);

                outputStream.write("mw\n".getBytes());
                outputStream.write((size + "\n").getBytes());
                for (int k = 0; k < size; k++) {
                    for (int j = 0; j < size; j++) {
                        outputStream.write((matrix[k][j] + " ").getBytes());
                    }
                    outputStream.write("\n".getBytes());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}

