package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;
import org.jgap.*;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.DefaultConfiguration;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 18:59
 */
public class MaxCutWeightGeneticAlgorithm extends MaxCutWeightAbstract {
    final static int populationSize = 20;
    Configuration gaConf = new DefaultConfiguration();


    public MaxCutWeightGeneticAlgorithm(SimpleWeightGraph graph) {
        super(graph);
        Configuration.reset();
        gaConf.setPreservFittestIndividual(true);
        gaConf.setKeepPopulationSizeConstant(true);
        gaConf.setSelectFromPrevGen(0.2);
        try {
            gaConf.setPopulationSize(populationSize);
            gaConf.setFitnessFunction(new MaxCutFitness());
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void calc() {
        res_mask = new boolean[graph.getSize()];
        int chromeSize = graph.getSize();
        int numEvolutions = chromeSize;
        double maxFitness = Math.pow(2.0, chromeSize) - 1;

        Genotype genotype;
        try {
            IChromosome sampleChromosome = new Chromosome(gaConf, new BooleanGene(gaConf), chromeSize);
            gaConf.setSampleChromosome(sampleChromosome);
            genotype = Genotype.randomInitialGenotype(gaConf);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return;
        }
        int percentEvolution = numEvolutions / 100;

        for (int i = 0; i < numEvolutions; i++) {
            genotype.evolve();                // 1 step evolution
            afterEvolveAction(genotype);
            if (percentEvolution > 0 && i % percentEvolution == 0) {
                IChromosome fittest = genotype.getFittestChromosome();
                double fitness = fittest.getFitnessValue();
                if (fitness >= maxFitness) {
                    break;
                }
            }
        }

        IChromosome fittest = genotype.getFittestChromosome();

        //res_maxcut = (long) fittest.getFitnessValue();
        int i = 0;
        for (Gene gene : fittest.getGenes()) {
            res_mask[i++] = ((BooleanGene) gene).booleanValue();
        }

    }

    protected void afterEvolveAction(Genotype genotype) {
    }

    private class MaxCutFitness extends FitnessFunction {
        @Override
        protected double evaluate(IChromosome iChromosome) {
            int i, j, cut = 0, n = graph.getSize();
            for (i = 0; i < n; i++) { //calculate cut value
                for (j = i + 1; j < n; j++) {
                    cut += graph.getCell(i, j) *
                            (((BooleanGene) iChromosome.getGene(i)).booleanValue() != ((BooleanGene) iChromosome.getGene(j)).booleanValue() ? 1 : 0);
                }
            }
            //TODO: crutch, fix it
            if (cut < 0) {
                cut = 0;
            }
            return cut;
        }
    }


    @Override
    public String getHumanID() {
        return "Max Cut Wieght Genetic Algorithm";
    }

    @Override
    public String getID() {
        return "MCWGA";
    }
}
