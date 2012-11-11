package com.elvis.optimizationtask.rule;

import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightGraph;

/**
 * Created by User: el
 * Date: 08.11.12
 * Time: 13:54
 */
public class RuleOfDirac implements Rule {

    boolean isHamiltonian(Graph graph) {

        SimpleWeightGraph g = (SimpleWeightGraph) graph;


        return true;
    }

}
