package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;

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
        res_mask = getRandomSolution(graph.getSize());
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
