package com.elvis.optimizationtask.algorithm.maxcut.weight;

/**
 * Created by User: el
 * Date: 17.12.12
 * Time: 2:21
 */
public class MaxCutWeightGreedy extends MaxCutWeightAbstract {
    @Override
    public void calc() {
        res_mask = new boolean[graph.size()];
        while (true) {
            int v_max = -1;
            float max = 0;
            float rmv = cutValue(res_mask);
            for (int j = 0; j < graph.size(); j++) {
                res_mask[j] = !res_mask[j];
                float tmpcv = cutValue(res_mask);
                if (rmv < tmpcv && max < tmpcv) {
                    v_max = j;
                    max = tmpcv;
                }
                res_mask[j] = !res_mask[j];
            }
            if (v_max == -1) {
                break;
            }
            res_mask[v_max] = !res_mask[v_max];
        }

    }

    @Override
    public String getHumanID() {
        return "Max Cut Weight Greedy";
    }

    @Override
    public String getID() {
        return "MCWG";
    }
}
