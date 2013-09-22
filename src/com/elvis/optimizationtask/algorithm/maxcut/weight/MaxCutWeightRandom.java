package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightFloatGraph;

/**
 * Created by User: el
 * Date: 12.10.12
 * Time: 1:35
 */
public class MaxCutWeightRandom extends MaxCutWeightAbstract {
    public MaxCutWeightRandom() {
    }

    public MaxCutWeightRandom(SimpleWeightFloatGraph floatGraph) {
        super(floatGraph);
    }

    @Override
    public void calc() {
        res_mask = getRandomSolution(graph.size());
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
