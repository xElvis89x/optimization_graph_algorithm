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

    private static Random rand = new Random(System.nanoTime());

    @Override
    public void calc() {
        res_mask = getRandomMask(graph.getSize());
    }

    private static boolean[] getRandomMask(int n) {
        boolean[] result = new boolean[n];
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
