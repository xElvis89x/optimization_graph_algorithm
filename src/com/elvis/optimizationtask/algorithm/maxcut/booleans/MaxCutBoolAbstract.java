package com.elvis.optimizationtask.algorithm.maxcut.booleans;

import com.elvis.optimizationtask.algorithm.maxcut.MaxCutAbstract;
import com.elvis.model.SimpleBooleanGraph;

/**
 * Created by User: el
 * Date: 02.10.12
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public abstract class MaxCutBoolAbstract extends MaxCutAbstract {

    SimpleBooleanGraph simpleBooleanGraph;
    long res_maxcut;
    boolean[] res_mask;

    public MaxCutBoolAbstract(SimpleBooleanGraph simpleBooleanGraph) {
        this.simpleBooleanGraph = simpleBooleanGraph;
    }

    @Override
    public long getMaxCut() {
        return res_maxcut;
    }

    @Override
    public boolean[] getMask() {
        return res_mask;
    }
}
