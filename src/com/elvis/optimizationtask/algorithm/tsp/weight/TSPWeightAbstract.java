package com.elvis.optimizationtask.algorithm.tsp.weight;

import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.tsp.TSPAbstract;

/**
 * Created by User: el
 * Date: 30.10.12
 * Time: 16:29
 */
public abstract class TSPWeightAbstract extends TSPAbstract {
    protected SimpleWeightGraph graph;

    protected TSPWeightAbstract(SimpleWeightGraph graph) {
        this.graph = graph;
    }

    protected TSPWeightAbstract() {
    }

    public SimpleWeightGraph getGraph() {
        return graph;
    }

    public void setGraph(SimpleWeightGraph graph) {
        this.graph = graph;
    }

    @Override
    public float getValue() {
        return pathLength(result);
    }

    public float pathLength(int[] path) {
        if (path[0] == path[1]) {
            return Float.POSITIVE_INFINITY;
        }
        float sum = 0;
        for (int i = 0; i < path.length; i++) {
            float edgeLength = graph.getCell(path[i], path[(i + 1) % path.length]);
            if (edgeLength == 0) {
                edgeLength = Float.MAX_VALUE;
            }
            sum += edgeLength;
        }
        return sum;
    }
}
