package com.elvis.optimizationtask.algorithm.tsp;

import com.elvis.optimizationtask.algorithm.tsp.weight.TSPWeightBruteForce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User: el
 * Date: 11.11.12
 * Time: 13:20
 */
public class TSPWeightFactory {
    private static Map<TSPType, TSP> tspMap = new HashMap<TSPType, TSP>() {
        {
            put(TSPType.BRUTE_FORCE, new TSPWeightBruteForce());
        }
    };

    public static List<TSPType> getTSPList() {
        return new ArrayList<TSPType>(tspMap.keySet());
    }

    public static TSP getTSPInstance(TSPType type) {
        return tspMap.get(type);
    }

}
