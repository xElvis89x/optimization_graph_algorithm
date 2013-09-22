package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.optimizationtask.algorithm.maxcut.weight.local.MaxCutWeightTabu;

/**
 * Created by User: el
 * Date: 18.12.12
 * Time: 21:41
 */
public class MaxCutWeightGlobalEquilibriumSearchWithTabu extends MaxCutWeightGlobalEquilibriumSearch {
    @Override
    public void calc() {
        localSearch = new MaxCutWeightTabu(graph, 20, graph.size() / 2);
        super.calc();
    }

    @Override
    public String getHumanID() {
        return super.getHumanID() + " + Tabu";
    }

    @Override
    public String getID() {
        return super.getID() + "T";
    }
}
