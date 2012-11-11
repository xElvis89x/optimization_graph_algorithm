package com.elvis.optimizationtask.ui.control;

import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.Algorithm;
import com.elvis.optimizationtask.ui.view.ProgressUIHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User: el
 * Date: 23.10.12
 * Time: 10:13
 */
public class AlgorithmCalculation implements Runnable {
    private ProgressUIHolder progressUIHolder;
    private File[] filesForCalc;


    private List<AlgorithmCalculationListener> algorithmCalculationListeners = new ArrayList<AlgorithmCalculationListener>();

    public boolean addListener(AlgorithmCalculationListener algorithmCalculationListener) {
        return algorithmCalculationListeners.add(algorithmCalculationListener);
    }

    public void setProgressUIHolder(ProgressUIHolder progressUIHolder) {
        this.progressUIHolder = progressUIHolder;
    }

    public void setFilesForCalc(File[] filesForCalc) {
        this.filesForCalc = filesForCalc;
    }

    int step;

    @Override
    public void run() {
        if (filesForCalc == null) return;
        progressUIHolder.getProgressBar().setValue(0);
        step = 0;

        fireStartCalculation();
        for (File file : filesForCalc) {
            calculate(Utils.getGraph(file));
            fireStepEnd(step++);
        }
        fireEndCalculation();
    }

    private void fireStepEnd(int step) {
        progressUIHolder.getProgressBar().setValue((int) (100 * step * 1.0 / filesForCalc.length));
    }

    private void fireEndCalculation() {
        progressUIHolder.getProgressText().setText("end solving maxcut for " + filesForCalc.length + " graphs");
    }

    private void fireStartCalculation() {
        progressUIHolder.getProgressText().setText("start solving maxcut for " + filesForCalc.length + " graphs");
    }


    private void calculate(SimpleWeightGraph graph) {
        int locStep = 0;
        List<Algorithm> list = fireStartSolving(graph);
        for (Algorithm algorithm : list) {
            progressUIHolder.getProgressText().setText("step: " + (step + 1) + ";  " + algorithm.getHumanID() + " start solve");
            algorithm.solve();
            fireSolvedUse(graph, algorithm);
            locStep++;
            progressUIHolder.getProgressBar().setValue((int) (100 * step * 1.0f / filesForCalc.length
                    + 100.0f / filesForCalc.length * locStep / list.size()));
        }
        fireSolved(graph);
    }

    List<Algorithm> fireStartSolving(Graph graph) {
        List<Algorithm> list = new ArrayList<Algorithm>();
        for (AlgorithmCalculationListener algorithmCalculationListener : algorithmCalculationListeners) {
            list.addAll(algorithmCalculationListener.startSolving(graph));
        }
        return list;
    }

    void fireSolvedUse(Graph graph, Algorithm algorithm) {
        for (AlgorithmCalculationListener algorithmCalculationListener : algorithmCalculationListeners) {
            algorithmCalculationListener.solvedUse(graph, algorithm);
        }
    }

    void fireSolved(Graph graph) {
        for (AlgorithmCalculationListener algorithmCalculationListener : algorithmCalculationListeners) {
            algorithmCalculationListener.solved(graph);
        }
    }

}
