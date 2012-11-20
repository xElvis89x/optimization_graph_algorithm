package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCutAbstract;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 15:16
 */
public abstract class MaxCutWeightAbstract extends MaxCutAbstract {
    protected SimpleWeightGraph graph;

    public MaxCutWeightAbstract(SimpleWeightGraph graph) {
        setGraph(graph);
    }

    protected MaxCutWeightAbstract() {
    }

    public void setGraph(Graph graph) {
        this.graph = (SimpleWeightGraph) graph;
    }

    @Override
    public Graph getGraph() {
        return graph;
    }

    @Override
    public float getMaxCutValue() {
        return cutValue(res_mask);
    }

    public float cutValue(boolean[] mask) {
        float cut = 0;
        for (int i = 0; i < graph.size(); i++) {
            for (int j = i + 1; j < graph.size(); j++) {
                if (mask[i] != mask[j]) {
                    cut += graph.getCell(i, j);
                }
            }
        }
        return cut;
    }
}
