package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.Graph;
import com.elvis.model.SimpleWeightFloatGraph;
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
    public MaxCutWeightGAWithCore(SimpleWeightFloatGraph floatGraph) {
        super(floatGraph);
    }

    public MaxCutWeightGAWithCore() {
    }

    @Override
    protected void afterEvolveAction(Genotype genotype) {
        //getCore(genotype.getPopulation());
    }

    @Override
    public void setGraph(Graph graph) {
        super.setGraph(graph);
        coreMain = new int[this.graph.size()];
        setValueForArray(coreMain, -1);
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

    int[] coreMain;

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

    @Override
    public String getHumanID() {
        return "Max Cut Weight Genetic Algorithm + Cores";    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getID() {
        return "MCWGA+C";    //To change body of overridden methods use File | Settings | File Templates.
    }
}
