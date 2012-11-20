package com.elvis.optimizationtask.ui.control;

import com.elvis.graph.visualizer.GraphVisualizer;
import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.Algorithm;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCutWeightFactory;
import com.elvis.optimizationtask.algorithm.tsp.TSP;
import com.elvis.optimizationtask.algorithm.tsp.TSPWeightFactory;
import com.elvis.optimizationtask.algorithm.tsp.weight.TSPWeightBruteForce;
import com.elvis.optimizationtask.algorithm.tsp.weight.decomposition.TSPWeightDecomposition;
import com.elvis.optimizationtask.ui.model.FileListModel;
import com.elvis.optimizationtask.ui.model.TSPTableModel;
import com.elvis.optimizationtask.ui.view.MaxCutTableCellRenderer;
import com.elvis.optimizationtask.ui.view.TSPView;
import com.elvis.optimizationtask.ui.view.components.TSPDecompositionUIElement;
import org.springframework.beans.factory.InitializingBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User: el
 * Date: 01.11.12
 * Time: 12:57
 */
public class TSPController extends AbstractController implements InitializingBean {
    private TSPView tspView;
    private TSPTableModel tableModel;

    public void setTspView(TSPView tspView) {
        this.tspView = tspView;
    }

    public void setTableModel(TSPTableModel tableModel) {
        this.tableModel = tableModel;
    }

    private void init() {
        algorithmCalculation.addListener(new AlgorithmCalculationAdapter() {
            Color color;

            @Override
            public List<Algorithm> startSolving(Graph _graph) {
                SimpleWeightGraph graph = (SimpleWeightGraph) _graph;
                color = Utils.getRandomColor();
                java.util.List<Algorithm> maxCutList = new ArrayList<Algorithm>();
                if (tspView.getBruteForceCheckBox().isSelected()) {
                    maxCutList.add(new TSPWeightBruteForce(graph));
                }

                for (TSPDecompositionUIElement element : tspView.getList()) {
                    TSPWeightDecomposition alg = new TSPWeightDecomposition(graph);
                    alg.setDecompositionAlgorithm(MaxCutWeightFactory.getInstance().getMaxCutInstance(element.getMaxCutType().toString()));
                    alg.setTsp(TSPWeightFactory.getTSPInstance(element.getTSPType()));
                    maxCutList.add(alg);
                }
                return maxCutList;
            }

            @Override
            public void solvedUse(Graph graph, Algorithm algorithm) {
                tableModel.add((TSP) algorithm, color);
            }
        });
        tspView.getResultsTable().setModel(tableModel);
        MaxCutTableCellRenderer cellRenderer = new MaxCutTableCellRenderer();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            tspView.getResultsTable().getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        listModel = new FileListModel();
        tspView.getFileList().setModel(listModel);

        tspView.getSelectFilesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
                setFilesForCalc(chooseFileWithGraph());
            }
        });
        tspView.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmCalculation.setFilesForCalc(filesForCalc);
                tableModel.clear();
                new Thread(algorithmCalculation).start();
            }
        });

        tspView.getExcelExportButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = getFileForSaveXLS();
                if (file == null) return;
                OutputStream fileStream = null;
                try {
                    fileStream = new FileOutputStream(file);
                    //new ExcelExporter(tableModel.getMaxCuts()).writeData(fileStream);
                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(getComponent(),
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

        tspView.getVisualizeGraphButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tspView.getFileList().getSelectedIndex();
                if (index >= 0 && index < listModel.getSize()) {
                    SimpleWeightGraph graph = Utils.getGraph(listModel.get(index));
                    GraphVisualizer visualizer = new GraphVisualizer(graph);
//                    visualizer.setMaxCutMask(getMaxCutForGraph(graph).getMask());
                    visualizer.start();
                }
            }
        });

    }

    @Override
    protected Component getComponent() {
        return tspView.getContentPane();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
