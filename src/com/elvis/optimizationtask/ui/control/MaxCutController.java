package com.elvis.optimizationtask.ui.control;

import com.elvis.graph.visualizer.phisics.GraphVisualizer;
import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightFloatGraph;
import com.elvis.optimizationtask.algorithm.Algorithm;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCutWeightFactory;
import com.elvis.optimizationtask.export.ExcelExporter;
import com.elvis.optimizationtask.ui.model.FileListModel;
import com.elvis.optimizationtask.ui.model.MaxCutTableModel;
import com.elvis.optimizationtask.ui.view.MaxCutTableCellRenderer;
import com.elvis.optimizationtask.ui.view.MaxCutView;
import org.springframework.beans.factory.InitializingBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by User: el
 * Date: 03.10.12
 * Time: 9:31
 * To change this template use File | Settings | File Templates.
 */
public class MaxCutController extends AbstractController implements InitializingBean {
    private MaxCutTableModel tableModel;
    private MaxCutView maxCutView;

    public MaxCutController() {
    }

    public void setMaxCutView(MaxCutView maxCutView) {
        this.maxCutView = maxCutView;
    }

    public void setTableModel(MaxCutTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void init() {
        algorithmCalculation.addListener(new AlgorithmCalculationAdapter() {
            Color color;

            @Override
            public java.util.List<Algorithm> startSolving(Graph _graph) {
                SimpleWeightFloatGraph floatGraph = (SimpleWeightFloatGraph) _graph;
                color = Utils.getRandomColor();
                java.util.List<Algorithm> maxCutList = new ArrayList<Algorithm>();
                for (String maxCutType : maxCutView.getListAlgorithm()) {
                    MaxCut maxCut = MaxCutWeightFactory.getInstance().getMaxCutInstance(maxCutType);
                    maxCut.setGraph(floatGraph);
                    maxCutList.add(maxCut);
                }
                return maxCutList;
            }

            @Override
            public void solvedUse(Graph graph, Algorithm algorithm) {
                tableModel.add((MaxCut) algorithm, color);
            }
        });
        maxCutView.getResultsTable().setModel(tableModel);
        MaxCutTableCellRenderer cellRenderer = new MaxCutTableCellRenderer();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            maxCutView.getResultsTable().getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
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
                algorithmCalculation.setFilesForCalc(filesForCalc);
                tableModel.clear();
                new Thread(algorithmCalculation).start();
            }
        });

        maxCutView.getExcelExport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = getFileForSaveXLS();
                if (file == null) return;
                OutputStream fileStream = null;
                try {
                    fileStream = new FileOutputStream(file);
                    new ExcelExporter(tableModel.getMaxCuts()).writeData(fileStream);
                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(maxCutView.getContentPane(),
                            "Can not write to file.\n No access to file!!!");
                    e1.printStackTrace();
                } finally {
                    try {
                        assert fileStream != null;
                        fileStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        maxCutView.getVisualizeGraphButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = maxCutView.getFileList().getSelectedIndex();
                if (index >= 0 && index < listModel.getSize()) {
                    SimpleWeightFloatGraph floatGraph = Utils.getGraph(listModel.get(index));
                    GraphVisualizer visualizer = new GraphVisualizer(floatGraph);
                    MaxCut mc;
                    if ((mc = getMaxCutForGraph(floatGraph)) != null) {
                        visualizer.setMaxCutMask(mc.getResult());
                    }
                    visualizer.start();
                }
            }
        });
    }

    MaxCut getMaxCutForGraph(Graph graph) {
        MaxCut result = null;
        for (MaxCut maxCut : tableModel.getMaxCuts()) {
            if (maxCut.getGraph().equals(graph)) {
                result = maxCut;
            }
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    @Override
    protected Component getComponent() {
        return maxCutView.getContentPane();
    }
}
