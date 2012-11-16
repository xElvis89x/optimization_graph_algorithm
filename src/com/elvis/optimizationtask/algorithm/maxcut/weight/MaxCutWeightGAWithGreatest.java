package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.maxcut.weight.ga.CustomChromosome;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.BooleanGene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User: el
 * Date: 12.11.12
 * Time: 0:23
 */
public class MaxCutWeightGAWithGreatest extends MaxCutWeightGeneticAlgorithm {
    public MaxCutWeightGAWithGreatest() {
    }

    public MaxCutWeightGAWithGreatest(SimpleWeightGraph graph) {
        super(graph);
    }

    @Override
    protected void endEvolveAction(Genotype genotype) {
        super.endEvolveAction(genotype);
        System.out.println(genotype.getFittestChromosome().getFitnessValue());
        IChromosome chromosome = genotype.getFittestChromosome();
        modificationList.clear();
        IChromosome newB = cloneChrome(chromosome);
        IChromosome best;
//        do {
        best = newB;
        newB = getBest(best);
//        } while (newB != null);

        if (best != chromosome) {
            genotype.getPopulation().addChromosome(best);
        }
        System.out.println(genotype.getFittestChromosome().getFitnessValue());
    }

    List<Integer> modificationList = new ArrayList<Integer>();

    IChromosome getBest(IChromosome chromosome) {
        float best = cutValue(chromosome);
        IChromosome bestChrome = null;
        int bestIndex = -1;
        for (int i = 0; i < chromosome.size(); i++) {
            if (modificationList.indexOf(i) > -1) {
                continue;
            }
            chromosome.getGene(i).setAllele(!((BooleanGene) chromosome.getGene(i)).booleanValue());
            float res = cutValue(chromosome);
            if (best < res) {
                best = res;
                bestChrome = chromosome;
                bestIndex = i;
                System.out.println("+" + i + " " + best);
            } else {
                chromosome.getGene(i).setAllele(!((BooleanGene) chromosome.getGene(i)).booleanValue());
            }
        }
        if (bestIndex != -1) {
            modificationList.add(bestIndex);
        }

        return bestChrome;
    }

    IChromosome cloneChrome(IChromosome forClone) {
        CustomChromosome clone = null;
        try {
            clone = new CustomChromosome(gaConf, forClone.size());
            for (int i = 0; i < clone.size(); i++) {
                clone.setGene(i, new BooleanGene(gaConf, ((BooleanGene) forClone.getGene(i)).booleanValue()));
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return clone;
    }

    @Override
    public String getHumanID() {
        return super.getHumanID() + " + Greater";
    }

    @Override
    public String getID() {
        return super.getID() + "+G";    //To change body of overridden methods use File | Settings | File Templates.
    }
}
