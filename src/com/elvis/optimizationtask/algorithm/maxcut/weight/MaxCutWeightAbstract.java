package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCutAbstract;

import java.util.Random;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 15:16
 */
public abstract class MaxCutWeightAbstract extends MaxCutAbstract {
    protected SimpleWeightGraph graph;
    protected boolean[] res_mask;

    public MaxCutWeightAbstract(SimpleWeightGraph graph) {
        this.graph = graph;
    }

    @Override
    public Graph getGraph() {
        return graph;
    }

    protected static Random rand = new Random(System.nanoTime());

    protected static boolean[] getRandomSolution(int n) {
        boolean[] result = new boolean[n];
        for (int i = 0; i < result.length; i++) {
            result[i] = rand.nextBoolean();
        }
        return result;
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
