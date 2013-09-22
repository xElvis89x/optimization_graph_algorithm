package com.elvis.optimizationtask.algorithm.tsp.weight;

import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightFloatGraph;
import com.elvis.optimizationtask.algorithm.tsp.TSPAbstract;

/**
 * Created by User: el
 * Date: 30.10.12
 * Time: 16:29
 */
public abstract class TSPWeightAbstract extends TSPAbstract {
    protected SimpleWeightFloatGraph graph;

    protected TSPWeightAbstract(SimpleWeightFloatGraph graph) {
        this.graph = graph;
    }

    protected TSPWeightAbstract() {
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = (SimpleWeightFloatGraph) graph;
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
