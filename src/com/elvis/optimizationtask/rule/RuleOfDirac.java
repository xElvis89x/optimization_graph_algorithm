package com.elvis.optimizationtask.rule;

import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightGraph;

/**
 * Created by User: el
 * Date: 08.11.12
 * Time: 13:54
 * Rule Of Dirac. If all node of graph have more then graph size divide on 2 then graph have hamilton cycle
 */
public class RuleOfDirac implements Rule {

    @Override
    public boolean check(Graph graph) {
        SimpleWeightGraph g = (SimpleWeightGraph) graph;
        for (int i = 1; i < g.size(); i++) {
            int count = 0;
            for (int j = i + 1; j < g.size(); j++) {
                if (g.getCell(i, j) > 0) {
                    count++;
                }
            }
            if (count < g.size() / 2) {
                return false;
            }
        }
        return true;
    }
}
