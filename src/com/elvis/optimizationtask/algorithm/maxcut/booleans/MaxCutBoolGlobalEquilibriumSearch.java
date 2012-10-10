package com.elvis.optimizationtask.algorithm.maxcut.booleans;

import com.elvis.model.SimpleBooleanGraph;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 9:47
 */
public class MaxCutBoolGlobalEquilibriumSearch extends MaxCutBoolAbstract {
    public MaxCutBoolGlobalEquilibriumSearch(SimpleBooleanGraph simpleBooleanGraph) {
        super(simpleBooleanGraph);
    }

    @Override
    public void calc() {

    }

    @Override
    public String getHumanID() {
        return "Max Cut Global Equilibrium Search";
    }

    @Override
    public String getID() {
        return "MCGES";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
