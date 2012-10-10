package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.optimizationtask.algorithm.maxcut.MaxCutAbstract;
import com.elvis.model.SimpleWeightGraph;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 15:16
 */
public abstract class MaxCutWeightAbstract extends MaxCutAbstract {
    SimpleWeightGraph graph;
    long res_maxcut;
    boolean[] res_mask;


    protected MaxCutWeightAbstract(SimpleWeightGraph graph) {
        this.graph = graph;
    }


    @Override
    public long getMaxCut() {
        return res_maxcut;
    }

    @Override
    public boolean[] getMask() {
        return res_mask;
    }


}
