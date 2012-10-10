package com.elvis.optimizationtask.ui.control;

import com.elvis.graph.GraphVisualizator;
import com.elvis.model.SimpleBooleanGraph;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.algorithm.maxcut.booleans.MaxCutBoolBrutForce;
import com.elvis.optimizationtask.algorithm.maxcut.booleans.MaxCutBoolGeneticAlgorithm;
import com.elvis.optimizationtask.algorithm.maxcut.booleans.MaxCutBoolLorena;
import com.elvis.optimizationtask.algorithm.maxcut.booleans.MaxCutBoolRandom;
import com.elvis.optimizationtask.export.ExcelExporter;
import com.elvis.optimizationtask.parser.ParserManager;
import com.elvis.optimizationtask.ui.model.MaxCutTableModel;
import com.elvis.optimizationtask.ui.view.MaxCutTableCellRenderer;
import com.elvis.optimizationtask.ui.view.MaxCutView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

/**
 * Created by User: el
 * Date: 03.10.12
 * Time: 9:31
 * To change this template use File | Settings | File Templates.
 */
public class MaxCutController {
    MaxCutView maxCutView;
    JFrame frame;
    SimpleBooleanGraph graph;
    MaxCutTableModel tableModel;
    private File[] filesForCalc;

    public void setFilesForCalc(File[] filesForCalc) {
        this.filesForCalc = filesForCalc;
        String text = "";
        for (File file : filesForCalc) {
            text += file.getName() + ",";
        }
        maxCutView.getFileNames().setText(text);
    }

    public MaxCutController(MaxCutView maxCutView) {
        this.maxCutView = maxCutView;
        init();
    }

    public void init() {
        initFrame();
        tableModel = new MaxCutTableModel();
        maxCutView.getTable1().setModel(tableModel);
        MaxCutTableCellRenderer cellRenderer = new MaxCutTableCellRenderer();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            maxCutView.getTable1().getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        maxCutView.getFileNames().setText("");

        maxCutView.getSelectFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFilesForCalc(chooseFileWithGraph());
            }
        });
        maxCutView.getStartCalcButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filesForCalc == null) return;
                maxCutView.getProgressBar().setValue(0);

                tableModel.clear();
                step = 0;
                for (File file : filesForCalc) {
                    graph = getGraph(file);
                    calculate();
                    maxCutView.getProgressBar().setValue((int) (100 * step * 1.0 / filesForCalc.length));
                    step++;
                }
            }
        });

        maxCutView.getExcelExport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = getFileForSaveXLS();
                if (file == null) return;
                try {
                    new ExcelExporter(tableModel.getMaxCuts()).writeData(new FileOutputStream(file));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        maxCutView.getVisualizeGraphButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GraphVisualizator(getGraph(filesForCalc[0])).start();
            }
        });
    }

    File getFileForSaveXLS() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel File", "xls"));
        fileChooser.showSaveDialog(frame);
        File f = fileChooser.getSelectedFile();
        if (f == null) return null;
        if (!f.getName().endsWith(".xls"))
            f = new File(f.getPath() + ".xls");
        return f;
    }

    int step;

    private void initFrame() {
        frame = new JFrame("Max Cut Algorithm");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(maxCutView.getContentPane());
        frame.setPreferredSize(new Dimension(500, 400));
        frame.pack();
    }

    Random random = new Random();

    Color getRandomColor() {
        return Color.getHSBColor(random.nextFloat(), 0.3f, 1.0f);
    }

    private void calculate() {
        MaxCut[] maxCutsAlgorithms = {
                new MaxCutBoolBrutForce(graph),
                new MaxCutBoolGeneticAlgorithm(graph),
                new MaxCutBoolLorena(graph),
                new MaxCutBoolRandom(graph)
        };
        Color color = getRandomColor();
        int locStep = 0;
        for (MaxCut maxCutsAlgorithm : maxCutsAlgorithms) {
            maxCutsAlgorithm.solve();
            tableModel.add(maxCutsAlgorithm, color);

            maxCutView.getProgressBar().setValue((int) (100 * (step - 1) * 1.0f / filesForCalc.length + 100.0f / filesForCalc.length * locStep / maxCutsAlgorithms.length));
            locStep++;
        }
    }

    private SimpleBooleanGraph getGraph(File file) {
        SimpleBooleanGraph result = null;
        if (file != null) {
            FileInputStream stream = null;
            try {
                stream = new FileInputStream(file);
                result = ParserManager.getGraphReader(stream).getGraphFromStream();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }
        return result;
    }

    private File[] chooseFileWithGraph() {
        JFileChooser fileChooser = new JFileChooser();
        //crutch
        fileChooser.setCurrentDirectory(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile().getParentFile().getParentFile());

        fileChooser.setMultiSelectionEnabled(true);

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Graph file", "graph"));
        fileChooser.showOpenDialog(frame);
        return fileChooser.getSelectedFiles();
    }

    public void start() {
        frame.setVisible(true);
    }
}
