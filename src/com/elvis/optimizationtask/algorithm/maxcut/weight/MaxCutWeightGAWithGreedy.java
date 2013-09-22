package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightFloatGraph;
import com.elvis.optimizationtask.algorithm.maxcut.weight.local.MaxCutWeightGreedy;
import org.jgap.Genotype;

/**
 * Created by User: el
 * Date: 12.11.12
 * Time: 0:23
 */
public class MaxCutWeightGAWithGreedy extends MaxCutWeightGeneticAlgorithm {
    public MaxCutWeightGAWithGreedy() {
    }

    public MaxCutWeightGAWithGreedy(SimpleWeightFloatGraph floatGraph) {
        super(floatGraph);
    }

    MaxCutWeightGreedy greedy = new MaxCutWeightGreedy();

    @Override
    protected void endEvolveAction(Genotype genotype) {
        boolean[] mask = convertChromosomeToArray(genotype.getFittestChromosome());
        greedy.setGraph(graph);
        greedy.setMask_best(mask);
        greedy.calc();
        res_mask = greedy.getResult();
    }

    @Override
    public String getHumanID() {
        return super.getHumanID() + " + Greedy";
    }

    @Override
    public String getID() {
        return super.getID() + "+G";    //To change body of overridden methods use File | Settings | File Templates.
    }
}
