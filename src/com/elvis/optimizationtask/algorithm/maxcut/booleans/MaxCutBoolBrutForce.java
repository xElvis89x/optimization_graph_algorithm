package com.elvis.optimizationtask.algorithm.maxcut.booleans;

import com.elvis.model.SimpleBooleanGraph;

/**
 * Created by User: el
 * Date: 02.10.12
 * Time: 17:45
 * <p>
 * Brut Force algorithm for find max cut on boolean graph
 * </p>
 * <p>
 * Main idea divide all graph point on two set,
 * calculate cut sum.
 * iterate two set of graph point and save max value for cut
 * </p>
 * <p>
 * mask - dividing graph point on two set with label 1 and 0
 * </p>
 * <p>
 * max cut - max value for graph cut
 * </p>
 */
public class MaxCutBoolBrutForce extends MaxCutBoolAbstract {

    public MaxCutBoolBrutForce(SimpleBooleanGraph simpleBooleanGraph) {
        super(simpleBooleanGraph);
    }

    @Override
    public void calc() {
        int n = graph.getSize();
        res_mask = new boolean[n];
        res_maxcut = 0;

        boolean mask[] = new boolean[n];
        int i, j, cut;
        do { //all cuts
            for (i = 0, cut = 0; i < n; i++) { //calculate cut value
                for (j = i + 1; j < n; j++) {
                    cut += (graph.getCell(i, j) && mask[i] != mask[j]) ? 1 : 0;
                }
            }
            if (res_maxcut < cut) { //store result
                res_maxcut = cut;
                System.arraycopy(mask, 0, res_mask, 0, n);
            }
        } while (nextPermutation(mask));
    }

    static boolean nextPermutation(boolean[] m) {
        for (int i = 0; i < m.length; i++) {
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
        return "MaxCut Brute Force Algorithm";
    }

    @Override
    public String getID() {
        return "MCBFA";
    }
}
