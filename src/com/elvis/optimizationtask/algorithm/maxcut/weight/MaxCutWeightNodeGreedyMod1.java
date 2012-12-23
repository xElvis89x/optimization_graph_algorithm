package com.elvis.optimizationtask.algorithm.maxcut.weight;

import java.util.List;

/**
 * Created by User: el
 * Date: 17.11.12
 * Time: 13:23
 */
public class MaxCutWeightNodeGreedyMod1 extends MaxCutWeightNodeGreedy {
    @Override
    protected void resolveResult(List<Integer> nodeList) {
        res_mask = new boolean[graph.size()];
        int prevNode = 0;
        boolean currentFlag = nodeList.indexOf(0) % 2 != 0;
        for (Integer integer : nodeList) {
            if (graph.getCell(prevNode, integer) != 0) {
                res_mask[integer] = currentFlag;
                currentFlag = !currentFlag;
            } else {
                res_mask[integer] = !currentFlag;
            }
        }
    }

    @Override
    public String getHumanID() {
        return super.getHumanID() + "Mod1";
    }

    @Override
    public String getID() {
        return super.getID() + "M1";
    }
}
