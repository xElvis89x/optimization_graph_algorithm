package com.elvis.optimizationtask.ui.control;

import com.elvis.graph.GraphVisualizator;
import com.elvis.graph.generator.GraphGenerator;
import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.algorithm.maxcut.weight.*;
import com.elvis.optimizationtask.export.ExcelExporter;
import com.elvis.optimizationtask.parser.ParserManager;
import com.elvis.optimizationtask.ui.model.FileListModel;
import com.elvis.optimizationtask.ui.model.MaxCutTableModel;
import com.elvis.optimizationtask.ui.view.MaxCutTableCellRenderer;
import com.elvis.optimizationtask.ui.view.MaxCutView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    SimpleWeightGraph graph;
    MaxCutTableModel tableModel;
    FileListModel listModel;
    private File[] filesForCalc;

    JMenuBar menuBar = new JMenuBar();

    public void setFilesForCalc(File[] filesForCalc) {
        this.filesForCalc = filesForCalc;
        for (File file : filesForCalc) {
            listModel.addFile(file);
        }
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

        listModel = new FileListModel();
        maxCutView.getFileList().setModel(listModel);

        maxCutView.getSelectFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (File file : filesForCalc) {
                            graph = getGraph(file);
                            calculate();
                            maxCutView.getProgressBar().setValue((int) (100 * step * 1.0 / filesForCalc.length));
                            step++;
                        }
                    }
                }).start();
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
                int index = maxCutView.getFileList().getSelectedIndex();
                if (index >= 0 && index < listModel.getSize()) {
                    new GraphVisualizator(getGraph(listModel.get(index))).start();
                }
            }
        });


        JMenu menuFile = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuFile.add(exitItem);


        JMenu menuTools = new JMenu("Tools");
        JMenuItem item = new JMenuItem("Graph Generator");
        item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GraphGenerator().start();
            }
        });
        menuTools.add(item);


        menuBar.add(menuFile);
        menuBar.add(menuTools);


    }

    File getFileForSaveXLS() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
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
        frame.setJMenuBar(menuBar);
        frame.setPreferredSize(new Dimension(500, 400));
        frame.pack();
    }

    Random random = new Random();

    Color getRandomColor() {
        return Color.getHSBColor(random.nextFloat(), 0.3f, 1.0f);
    }

    private void calculate() {
        List<MaxCut> maxCutList = new ArrayList<MaxCut>();
        if (maxCutView.getBrutForceCheckBox().isSelected()) {
            maxCutList.add(new MaxCutWeightBrutForce(graph));
        }
        if (maxCutView.getGeneticAlgorithmCheckBox().isSelected()) {
            maxCutList.add(new MaxCutWeightGeneticAlgorithm(graph));
        }
        if (maxCutView.getLorenaCheckBox().isSelected()) {
            maxCutList.add(new MaxCutWeightLorena(graph));
        }
        if (maxCutView.getRandomCheckBox().isSelected()) {
            maxCutList.add(new MaxCutWeightRandom(graph));
        }
        if (maxCutView.getGESCheckBox().isSelected()) {
            maxCutList.add(new MaxCutWeightGlobalEquilibriumSearch(graph));
        }

        Color color = getRandomColor();
        int locStep = 0;
        for (MaxCut maxCutsAlgorithm : maxCutList) {
            maxCutsAlgorithm.solve();
            tableModel.add(maxCutsAlgorithm, color);
            maxCutView.getProgressBar().setValue((int) (100 * step * 1.0f / filesForCalc.length + 100.0f / filesForCalc.length * locStep / maxCutList.size()));
            locStep++;
        }
    }

    private SimpleWeightGraph getGraph(File file) {
        SimpleWeightGraph result = null;
        if (file != null) {
            FileInputStream stream = null;
            try {
                stream = new FileInputStream(file);
                result = (SimpleWeightGraph) ParserManager.getGraphReader(stream).getGraphFromStream();
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
        fileChooser.setCurrentDirectory(new File("."));
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
