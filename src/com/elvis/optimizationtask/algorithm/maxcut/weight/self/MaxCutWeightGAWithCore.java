package com.elvis.optimizationtask.algorithm.maxcut.weight.self;

import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.maxcut.weight.MaxCutWeightGeneticAlgorithm;
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
        getCore(genotype);
    }

    int[] coreMain = new int[graph.getSize()];

    {
        setValueForArray(coreMain, -1);
    }

    void getCore(Genotype genotype) {
        Population population = genotype.getPopulation();
        int[] core = new int[graph.getSize()];
        setValueForArray(core, -1);
        for (int i = 0; i < graph.getSize(); i++) {
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
        for (int i = 0; i < graph.getSize(); i++) {
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
