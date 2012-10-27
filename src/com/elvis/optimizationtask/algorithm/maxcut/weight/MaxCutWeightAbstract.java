package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCutAbstract;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 15:16
 */
public abstract class MaxCutWeightAbstract extends MaxCutAbstract {
    SimpleWeightGraph graph;
    boolean[] res_mask;


    public MaxCutWeightAbstract(SimpleWeightGraph graph) {
        this.graph = graph;
    }

    @Override
    public float getMaxCut() {
        return cutValue(res_mask);
    }

    @Override
    public boolean[] getMask() {
        return res_mask;
    }

    public float cutValue(boolean[] mask) {
        float cut = 0;
        for (int i = 0; i < graph.getSize(); i++) {
            for (int j = i + 1; j < graph.getSize(); j++) {
                cut += graph.getCell(i, j) * (mask[i] != mask[j] ? 1 : 0);
            }
        }
        return cut;
    }
}
