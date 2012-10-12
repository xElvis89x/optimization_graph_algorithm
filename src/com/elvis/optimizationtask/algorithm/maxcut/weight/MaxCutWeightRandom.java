package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;

import java.util.Random;

/**
 * Created by User: el
 * Date: 12.10.12
 * Time: 1:35
 */
public class MaxCutWeightRandom extends MaxCutWeightAbstract {
    public MaxCutWeightRandom(SimpleWeightGraph graph) {
        super(graph);
    }

    @Override
    public void calc() {
        int n = graph.getSize();
        res_mask = getRandomMask();
        int i, j;
        for (i = 0, res_maxcut = 0; i < n; i++) { //calculate cut value
            for (j = i + 1; j < n; j++) {
                if (res_mask[i] != res_mask[j]) {
                    res_maxcut += graph.getCell(i, j);
                }
            }
        }
    }

    private boolean[] getRandomMask() {
        boolean[] result = new boolean[graph.getSize()];
        Random rand = new Random(System.nanoTime());
        for (int i = 0; i < result.length; i++) {
            result[i] = rand.nextBoolean();
        }
        return result;
    }

    @Override
    public String getHumanID() {
        return "Max Cut Weight Random";
    }

    @Override
    public String getID() {
        return "MCWR";
    }
}
