package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.impl.BooleanGene;

/**
 * Created by User: el
 * Date: 30.10.12
 * Time: 22:37
 */
public class MaxCutWeightGAWithCore extends MaxCutWeightGeneticAlgorithm {
    public MaxCutWeightGAWithCore(SimpleWeightGraph graph) {
        super(graph);
    }

    @Override
    protected void afterEvolveAction(Genotype genotype) {
        //getCore(genotype.getPopulation());
    }

    @Override
    protected void endEvolveAction(Genotype genotype) {
        int[] core = new int[graph.size()];
        setValueForArray(core, -1);
        for (int i = 0; i < graph.size(); i++) {
            for (IChromosome iChromosome : genotype.getPopulation().getChromosomes()) {
                BooleanGene bgene = (BooleanGene) iChromosome.getGene(i);
                int r = bgene.booleanValue() ? 1 : 0;
                if (core[i] == -1) {
                    core[i] = r;
                } else if (r != core[i]) {
                    core[i] = 2;
                }
            }
        }
        for (int i : core) {
            System.out.print(i + " ");
        }
        System.out.println(genotype.getFittestChromosome().getFitnessValue());

    }

    int[] coreMain = new int[graph.size()];

    {
        setValueForArray(coreMain, -1);
    }

    void getCore(Population population) {
        int[] core = new int[graph.size()];
        setValueForArray(core, -1);
        for (int i = 0; i < graph.size(); i++) {
            for (IChromosome iChromosome : population.getChromosomes()) {
                BooleanGene bgene = (BooleanGene) iChromosome.getGene(i);
                int r = bgene.booleanValue() ? 1 : 0;
                if (core[i] == -1) {
                    core[i] = r;
                } else if (r != core[i]) {
                    core[i] = 2;
                }
            }
        }
        for (int i = 0; i < graph.size(); i++) {
            if (coreMain[i] == -1) {
                coreMain[i] = core[i];
            } else {
                if (coreMain[i] == 2) {
                    coreMain[i] = core[i];
                } else if (coreMain[i] != core[i]) {
                    coreMain[i] = 2;
                }
            }

        }
        for (int i : coreMain) {
            System.out.print(i + " ");
        }
        System.out.println();

    }

    void setValueForArray(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
    }
}
