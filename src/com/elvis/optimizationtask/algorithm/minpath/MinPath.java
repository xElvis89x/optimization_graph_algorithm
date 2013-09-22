package com.elvis.optimizationtask.algorithm.minpath;

import com.elvis.model.Graph;
import com.elvis.optimizationtask.algorithm.Algorithm;

/**
 * Created by User: el
 * Date: 15.11.12
 * Time: 12:09
 */
public interface MinPath extends Algorithm {
    public void setFloatGraph(Graph graph);

    public Graph getFloatGraph();

    public Graph getResult();
}
