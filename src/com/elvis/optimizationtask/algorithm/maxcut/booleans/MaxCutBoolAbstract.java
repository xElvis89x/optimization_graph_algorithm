package com.elvis.optimizationtask.algorithm.maxcut.booleans;

import com.elvis.model.Graph;
import com.elvis.model.SimpleBooleanGraph;
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
    protected boolean[] res_mask;

    public MaxCutBoolAbstract(SimpleBooleanGraph graph) {
        this.graph = graph;
    }

    @Override
    public float getMaxCut() {
        return res_maxcut;
    }

    @Override
    public boolean[] getMask() {
        return res_mask;
    }

    @Override
    public Graph getGraph() {
        return graph;
    }
}
