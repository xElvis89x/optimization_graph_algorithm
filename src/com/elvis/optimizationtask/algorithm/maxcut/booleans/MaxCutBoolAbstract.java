package com.elvis.optimizationtask.algorithm.maxcut.booleans;

import com.elvis.model.Graph;
import com.elvis.model.SimpleBooleanGraph;
import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCutAbstract;

/**
 * Created by User: el
 * Date: 02.10.12
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public abstract class MaxCutBoolAbstract extends MaxCutAbstract {

    protected SimpleBooleanGraph graph;
    protected long res_maxcut;

    public MaxCutBoolAbstract(SimpleBooleanGraph graph) {
        this.graph = graph;
    }

    @Override
    public float getMaxCutValue() {
        return res_maxcut;
    }

    @Override
    public Graph getGraph() {
        return graph;
    }

    @Override
    public void setGraph(SimpleWeightGraph graph) {

    }
}
