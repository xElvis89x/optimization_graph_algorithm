package com.elvis.optimizationtask.algorithm.maxcut.booleans;

import com.elvis.model.SimpleBooleanGraph;

import java.util.Random;

/**
 * Created by User: el
 * Date: 06.10.12
 * Time: 10:08
 */
public class MaxCutBoolRandom extends MaxCutBoolAbstract {

    public MaxCutBoolRandom(SimpleBooleanGraph simpleBooleanGraph) {
        super(simpleBooleanGraph);
    }

    @Override
    public void calc() {
        int n = simpleBooleanGraph.getSize();
        res_mask = getRandomMask();
        int i, j;
        for (i = 0, res_maxcut = 0; i < n; i++) { //calculate cut value
            for (j = i + 1; j < n; j++) {
                res_maxcut += (simpleBooleanGraph.getCell(i, j) && res_mask[i] != res_mask[j]) ? 1 : 0;
            }
        }
    }

    private boolean[] getRandomMask() {
        boolean[] result = new boolean[simpleBooleanGraph.getSize()];
        Random rand = new Random(System.nanoTime());
        for (int i = 0; i < result.length; i++) {
            result[i] = rand.nextBoolean();
        }
        return result;
    }

    @Override
    public String getHumanID() {
        return "MaxCut Random algorithm";
    }

    @Override
    public String getID() {
        return "MCRA";
    }
}
