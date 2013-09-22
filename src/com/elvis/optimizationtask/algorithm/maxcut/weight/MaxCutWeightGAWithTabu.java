package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.optimizationtask.algorithm.maxcut.weight.local.MaxCutWeightTabu;
import org.jgap.Genotype;

/**
 * Created by User: el
 * Date: 20.12.12
 * Time: 1:32
 */
public class MaxCutWeightGAWithTabu extends MaxCutWeightGeneticAlgorithm {


    @Override
    protected void endEvolveAction(Genotype genotype) {
        boolean[] mask = convertChromosomeToArray(genotype.getFittestChromosome());
        MaxCutWeightTabu tabu = new MaxCutWeightTabu(graph, 20, graph.size() / 2);
        tabu.setMask_best(mask);
        tabu.setMask(mask);
        tabu.calc();
        res_mask = tabu.getResult();
    }

    @Override
    public String getHumanID() {
        return super.getHumanID() + " + Tabu";
    }

    @Override
    public String getID() {
        return super.getID() + "T";    //To change body of overridden methods use File | Settings | File Templates.
    }
}
