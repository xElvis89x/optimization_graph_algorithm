package com.elvis.graph.generator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

    private GeneratorView view;
    private File directoryForSave;
    private Random rand = new Random(System.nanoTime());

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

    private void selectDirectory() {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showSaveDialog(view.getContentPane());
        directoryForSave = chooser.getSelectedFile();
    }



    private void generateAndSaveGraph() {
        int count = (Integer) view.getSpinnerCount().getValue();
        int size = (Integer) view.getSpinnerSize().getValue();
        for (int i = 0; i < count; i++) {
            Generator generator = null;
            if (view.getGraphTypeComboBox().getSelectedItem() == "Float") {
                generator = new GeneratorFloat(size);
            } else if (view.getGraphTypeComboBox().getSelectedItem() == "Integer") {
                generator = new GeneratorInt(size);
            }
            FileOutputStream outputStream = null;
            try {
                Object[][] matrix = generator.generate();
                String path = directoryForSave.getAbsolutePath() + "\\testS" + size + "_" + Arrays.deepHashCode(matrix) + ".graph";
                System.out.println(path);
                writeToStream(size, generator.getType(), matrix, outputStream = new FileOutputStream(path));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private void writeToStream(int size, String type, Object[][] matrix, FileOutputStream outputStream) throws IOException {
        outputStream.write((type + "\n").getBytes());
        outputStream.write((size + "\n").getBytes());
        for (int k = 0; k < size; k++) {
            for (int j = 0; j < size; j++) {
                outputStream.write((matrix[k][j] + " ").getBytes());
            }
            outputStream.write("\n".getBytes());
        }
    }

    class Generator<T> {
        public static final int maxEdgeLength = 100;
        int size;

        public Generator(int size) {
            this.size = size;
        }

        private T[][] generate() {
            int q = rand.nextInt(size - 2) + 2;
            T[][] matrix = create(size);
            for (int k = 0; k < size; k++) {
                matrix[k][k] = Null();
                for (int j = k + 1; j < size; j++) {
                    matrix[k][j] = matrix[j][k] = rand.nextInt(q) == 0 ? Null() : Rand();
                }
            }
            return matrix;
        }

        protected T Null() {
            return null;
        }

        protected T Rand() {
            return null;
        }

        protected T[][] create(int size) {
            return null;
        }

        String getType() {
            return "";
        }
    }

    class GeneratorFloat extends Generator<Float> {
        GeneratorFloat(int size) {
            super(size);
        }

        @Override
        protected Float Null() {
            return 0f;
        }

        @Override
        protected Float[][] create(int size) {
            return new Float[size][size];
        }

        @Override
        protected Float Rand() {
            return rand.nextFloat() * maxEdgeLength;
        }

        String getType() {
            return "mw";
        }
    }

    class GeneratorInt extends Generator<Integer> {
        GeneratorInt(int size) {
            super(size);
        }

        @Override
        protected Integer Null() {
            return 0;
        }

        @Override
        protected Integer[][] create(int size) {
            return new Integer[size][size];
        }

        @Override
        protected Integer Rand() {
            return rand.nextInt(maxEdgeLength);
        }

        String getType() {
            return "mi";
        }
    }

}

