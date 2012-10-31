package com.elvis.optimizationtask.ui.control;

import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.algorithm.maxcut.weight.*;
import com.elvis.optimizationtask.algorithm.maxcut.weight.self.MaxCutWeightGAWithCore;
import com.elvis.optimizationtask.ui.model.MaxCutTableModel;
import com.elvis.optimizationtask.ui.view.MaxCutView;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User: el
 * Date: 23.10.12
 * Time: 10:13
 */
public class AlgorithmCalculation implements Runnable {
    private MaxCutView maxCutView;
    private MaxCutTableModel tableModel;
    private File[] filesForCalc;

    public void setMaxCutView(MaxCutView maxCutView) {
        this.maxCutView = maxCutView;
    }

    public void setTableModel(MaxCutTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void setFilesForCalc(File[] filesForCalc) {
        this.filesForCalc = filesForCalc;
    }

    int step;

    @Override
    public void run() {
        if (filesForCalc == null) return;
        maxCutView.getProgressBar().setValue(0);

        tableModel.clear();
        step = 0;

        fireStartCalculation();
        for (File file : filesForCalc) {
            calculate(Utils.getGraph(file));
            fireStepEnd(step++);
        }
        fireEndCalculation();
    }

    private void fireStepEnd(int step) {
        maxCutView.getProgressBar().setValue((int) (100 * step * 1.0 / filesForCalc.length));
    }

    private void fireEndCalculation() {
        maxCutView.getProgressText().setText("end solving maxcut for " + filesForCalc.length + " graphs");
    }

    private void fireStartCalculation() {
        maxCutView.getProgressText().setText("start solving maxcut for " + filesForCalc.length + " graphs");
    }


    private void calculate(SimpleWeightGraph graph) {
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
        if (maxCutView.getSelfGAWithCoreCheckBox().isSelected()) {
            maxCutList.add(new MaxCutWeightGAWithCore(graph));
        }

        Color color = Utils.getRandomColor();
        int locStep = 0;
        for (MaxCut maxCutsAlgorithm : maxCutList) {
            maxCutView.getProgressText().setText("step: " + (step + 1) + ";  " + maxCutsAlgorithm.getHumanID() + " start solve");
            maxCutsAlgorithm.solve();
            tableModel.add(maxCutsAlgorithm, color);
            locStep++;
            maxCutView.getProgressBar().setValue((int) (100 * step * 1.0f / filesForCalc.length
                    + 100.0f / filesForCalc.length * locStep / maxCutList.size()));
        }
    }
}
