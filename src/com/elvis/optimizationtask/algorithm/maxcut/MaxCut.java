package com.elvis.optimizationtask.algorithm.maxcut;

import com.elvis.model.Graph;
import com.elvis.optimizationtask.algorithm.Algorithm;
import com.elvis.optimizationtask.algorithm.TimeMark;

/**
 * Created by User: el
 * Date: 02.10.12
 * Time: 17:50
 */
public interface MaxCut extends TimeMark, Algorithm {

    public float getMaxCutValue();

    public boolean[] getResult();

    public Graph getGraph();

    public void setGraph(Graph graph);


}
