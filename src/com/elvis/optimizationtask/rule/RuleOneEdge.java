package com.elvis.optimizationtask.rule;

import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightGraph;

/**
 * Created by User: el
 * Date: 14.12.12
 * Time: 13:26
 */
public class RuleOneEdge implements Rule {
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
            if (count <= 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String id() {
        return "rule_one_edge";
    }
}
