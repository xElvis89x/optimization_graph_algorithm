package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightFloatGraph;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 15:31
 */

public class MaxCutWeightBruteForce extends MaxCutWeightAbstract {

    public MaxCutWeightBruteForce() {
    }

    public MaxCutWeightBruteForce(SimpleWeightFloatGraph graph) {
        super(graph);
    }

    @Override
    public void calc() {
        int n = graph.size();
        res_mask = new boolean[n];
        boolean mask[] = new boolean[n];

        float cut, best = 0;
        do {
            cut = cutValue(mask);
            if (best < cut) { //store result
                best = cut;
                System.arraycopy(mask, 0, res_mask, 0, n);
            }
        } while (nextPermutation(mask));

    }

    static boolean nextPermutation(boolean[] m) {
        m[0] = false;
        for (int i = 1; i < m.length; i++) {
            if (!m[i]) {
                m[i] = true;
                return true;
            }
            m[i] = false;
        }
        return false;
    }


    @Override
    public String getHumanID() {
        return "Max Cut Weight Brute Force";
    }

    @Override
    public String getID() {
        return "MCWBF";
    }
}
