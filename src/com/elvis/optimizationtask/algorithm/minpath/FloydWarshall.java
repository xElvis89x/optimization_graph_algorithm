package com.elvis.optimizationtask.algorithm.minpath;

import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightFloatGraph;

/**
 * Created by User: el
 * Date: 15.11.12
 * Time: 12:06
 */
public class FloydWarshall implements MinPath {
    SimpleWeightFloatGraph floatGraph;

    SimpleWeightFloatGraph result;

    @Override
    public void solve() {
        result = floatGraph;
        for (int k = 0; k < floatGraph.size(); k++) {
            SimpleWeightFloatGraph floatGraph1 = result;
            result = new SimpleWeightFloatGraph();
            result.create(floatGraph.size());
            for (int i = 1; i < floatGraph.size(); k++) {
                for (int j = i + 1; j < floatGraph.size(); k++) {
                    if (floatGraph1.getCell(i, k) != 0 && floatGraph1.getCell(j, k) != 0) {
                        float r = Math.min(floatGraph1.getCell(i, j), floatGraph1.getCell(i, k) + floatGraph1.getCell(j, k));
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
    public void setFloatGraph(Graph floatGraph) {
        this.floatGraph = (SimpleWeightFloatGraph) floatGraph;
    }

    @Override
    public Graph getFloatGraph() {
        return floatGraph;
    }

    @Override
    public Graph getResult() {
        return result;
    }
}
