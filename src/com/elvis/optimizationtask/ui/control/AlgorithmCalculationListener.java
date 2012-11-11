package com.elvis.optimizationtask.ui.control;

import com.elvis.model.Graph;
import com.elvis.optimizationtask.algorithm.Algorithm;

import java.util.List;

/**
 * Created by User: el
 * Date: 02.11.12
 * Time: 0:42
 */
public interface AlgorithmCalculationListener {
    public List<Algorithm> startSolving(Graph graph);

    public void solvedUse(Graph graph, Algorithm algorithm);

    public void solved(Graph graph);

}
