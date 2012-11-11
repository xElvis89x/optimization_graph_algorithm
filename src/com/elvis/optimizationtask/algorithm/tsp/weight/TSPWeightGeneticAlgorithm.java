package com.elvis.optimizationtask.algorithm.tsp.weight;

import com.elvis.model.SimpleWeightGraph;
import org.jgap.Configuration;
import org.jgap.impl.DefaultConfiguration;

/**
 * Created by User: el
 * Date: 05.11.12
 * Time: 1:15
 */
public class TSPWeightGeneticAlgorithm extends TSPWeightAbstract {
    final static int populationSize = 20;
    Configuration gaConf = new DefaultConfiguration();

    protected TSPWeightGeneticAlgorithm(SimpleWeightGraph graph) {
        super(graph);

    }

    @Override
    public void calc() {

    }

    @Override
    public String getHumanID() {
        return "TSP Weight Genetic Algorithm";
    }

    @Override
    public String getID() {
        return "TSPGA";
    }
}
