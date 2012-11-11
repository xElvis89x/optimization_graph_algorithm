package com.elvis.optimizationtask.algorithm.tsp;

import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.Algorithm;
import com.elvis.optimizationtask.algorithm.TimeMark;

/**
 * Created by User: el
 * Date: 30.10.12
 * Time: 15:28
 */
public interface TSP extends TimeMark, Algorithm {
    public int[] getResult();

    public float getValue();

    public Graph getGraph();

    public void setGraph(SimpleWeightGraph graph);

}
