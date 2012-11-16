package com.elvis.optimizationtask.algorithm.minpath;

import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightGraph;

/**
 * Created by User: el
 * Date: 15.11.12
 * Time: 12:06
 */
public class FloydWarshall implements MinPath {
    SimpleWeightGraph graph;

    SimpleWeightGraph result;

    @Override
    public void solve() {
        result = graph;
        for (int k = 0; k < graph.size(); k++) {
            SimpleWeightGraph graph1 = result;
            result = new SimpleWeightGraph();
            result.create(graph.size());
            for (int i = 1; i < graph.size(); k++) {
                for (int j = i + 1; j < graph.size(); k++) {
                    if (graph1.getCell(i, k) != 0 && graph1.getCell(j, k) != 0) {
                        float r = Math.min(graph1.getCell(i, j), graph1.getCell(i, k) + graph1.getCell(j, k));
                        result.setCell(i, j, r);
                        result.setCell(j, i, r);
                    }
                }
            }
        }
    }

    @Override
    public String getHumanID() {
        return "Min Path Floyd Warshall algorithm";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getID() {
        return "MPFW";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setGraph(Graph graph) {
        this.graph = (SimpleWeightGraph) graph;
    }

    @Override
    public Graph getGraph() {
        return graph;
    }

    @Override
    public Graph getResult() {
        return result;
    }
}
