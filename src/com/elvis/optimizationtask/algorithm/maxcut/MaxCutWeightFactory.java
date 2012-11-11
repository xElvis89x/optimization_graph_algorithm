package com.elvis.optimizationtask.algorithm.maxcut;

import com.elvis.optimizationtask.algorithm.maxcut.weight.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User: el
 * Date: 06.11.12
 * Time: 13:37
 */
public class MaxCutWeightFactory {
    private static Map<MaxCutType, MaxCut> maxCutMap = new HashMap<MaxCutType, MaxCut>() {
        {
            put(MaxCutType.BRUTE_FORCE, new MaxCutWeightBrutForce());
            put(MaxCutType.GA, new MaxCutWeightGeneticAlgorithm());
            put(MaxCutType.GES, new MaxCutWeightGlobalEquilibriumSearch());
            put(MaxCutType.LORENA, new MaxCutWeightLorena());
            put(MaxCutType.RANDOM, new MaxCutWeightRandom());
        }
    };

    public static List<MaxCutType> getMaxCutList() {
        return new ArrayList<MaxCutType>(maxCutMap.keySet());
    }

    public static MaxCut getMaxCutInstance(MaxCutType type) {
        return maxCutMap.get(type);
    }
}
