package com.elvis.optimizationtask.algorithm.tsp.weight;

import com.elvis.model.SimpleWeightGraph;

/**
 * Created by User: el
 * Date: 30.10.12
 * Time: 16:29
 */
public class TSPWeightBruteForce extends TSPWeightAbstract {
    public TSPWeightBruteForce(SimpleWeightGraph graph) {
        super(graph);
    }

    public TSPWeightBruteForce() {
    }

    @Override
    public void calc() {
        int n = graph.size();
        result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = -1;
        }
        int[] localResult = new int[n];
        initPermutation(localResult);

        float localPathLenght, best = Float.MAX_VALUE;
        int temp;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (i != j) {
                    temp = localResult[i];
                    localResult[i] = localResult[j];
                    localResult[j] = temp;

                    localPathLenght = pathLength(localResult);
                    if (localPathLenght < best) { //store result
                        best = localPathLenght;
                        System.arraycopy(localResult, 0, result, 0, n);
                    }

                    temp = localResult[i];
                    localResult[i] = localResult[j];
                    localResult[j] = temp;
                }
            }
        }
    }


    static void initPermutation(int[] m) {
        for (int i = 0; i < m.length; i++) {
            m[i] = i;
        }
    }

    @Override
    public String getHumanID() {
        return "TSP Weight Brute Force";
    }

    @Override
    public String getID() {
        return "TSPWBF";
    }
}
