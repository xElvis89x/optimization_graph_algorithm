package com.elvis.graph.generator.planar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: el
 * Date: 20.06.13
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
public class PlanarGraphGeneratorController {
    private PlanarGeneratorView view;
    private File directoryForSave;
    private Random rand = new Random(System.nanoTime());

    public PlanarGraphGeneratorController(PlanarGeneratorView view) {
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
        int width = (Integer) view.getSpinnerWidth().getValue();
        for (int i = 0; i < count; i++) {
            PlanarGenerator generator = new PlanarGenerator(size, width);
            FileOutputStream outputStream = null;
            try {
                float[][] matrix = generator.generate();
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

    private void writeToStream(int size, String type, float[][] matrix, FileOutputStream outputStream) throws IOException {
        outputStream.write((type + "\n").getBytes());
        outputStream.write((size + "\n").getBytes());
        for (int k = 0; k < size; k++) {
            for (int j = 0; j < size; j++) {
                outputStream.write((matrix[k][j] + " ").getBytes());
            }
            outputStream.write("\n".getBytes());
        }
    }

    class PlanarGenerator {
        public static final int maxEdgeLength = 100;
        private int size;
        private int width;

        public PlanarGenerator(int size, int width) {
            this.size = size;
            this.width = width;
        }


        private float[][] generate() {
            int q = rand.nextInt(size) + 2;
            float[][] matrix = create(size);

            Point[] planarPoints = new Point[size];
            for (int i = 0; i < size; i++) {
                planarPoints[i] = new Point(rand.nextInt(width), rand.nextInt(width));
            }

            for (int k = 0; k < size; k++) {
                for (int j = k + 1; j < size; j++) {
                    if (rand.nextInt(q) != 0) {
                        if (!isHaveIntersect(matrix, planarPoints, k, j)) {
                            matrix[j][k] = matrix[k][j] = (float) planarPoints[k].distance(planarPoints[j]);
                        } else {
                            matrix[j][k] = matrix[k][j] = 0;
                        }
                    }else {
                        matrix[j][k] = matrix[k][j] = 0;
                    }
                }
            }
            return matrix;
        }

        private boolean isHaveIntersect(float[][] martix, Point[] planarPoints, int q, int w) {
            Line2D.Float line1 = new Line2D.Float(planarPoints[q], planarPoints[w]);
            for (int k = 0; k < size; k++) {
                for (int j = k + 1; j < size; j++) {
                    if (martix[k][j] != 0) {
                        if (q != k && w != j && q != j && w != k) {
                            Line2D.Float line2 = new Line2D.Float(planarPoints[k], planarPoints[j]);
                            if (line1.intersectsLine(line2)) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        protected int Null() {
            return 0;
        }

        protected int Rand() {
            return rand.nextInt(maxEdgeLength);
        }

        protected float[][] create(int size) {
            return new float[size][size];
        }

        String getType() {
            return "mw";
        }
    }

}
