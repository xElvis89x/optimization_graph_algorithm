package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;

/**
 * Created by User: el
 * Date: 10.10.12
 * Time: 13:44
 */
public class MaxCutWeightGlobalEquilibriumSearch extends MaxCutWeightAbstract {
    protected MaxCutWeightGlobalEquilibriumSearch(SimpleWeightGraph graph) {
        super(graph);
    }

    @Override
    public void calc() {

        int n_u = 10;
        int n_p = 10;

        float u[] = new float[n_u];
        float a = 1;
        for (int i = 2; i < n_u; i++) {
            u[i] = a * u[i - 1];
        }

        double[][] p_u = new double[n_p][n_u];

        for (int j = 0; j < n_p; j++) {
            for (int k = 0; k < n_u; k++) {

                p_u[j][k] = 1 / (1 + (1 - p_u[j][0]) / p_u[j][0] * Math.exp(u[k] - u[0])*(getCutValue(u)));
            }
        }
    }

    public int getCutValue(boolean[] mask) {
        int cut = 0;
        for (int i = 0; i < graph.getSize(); i++) {
            for (int j = i + 1; j < graph.getSize(); j++) {
                cut += graph.getCell(i, j) * (mask[i] != mask[j] ? 1 : 0);
            }
        }
        return cut;
    }

    @Override
    public String getHumanID() {
        return "Max Cut Weight Global Equilibrium Search";
    }

    @Override
    public String getID() {
        return "MCWGES";
    }
}
