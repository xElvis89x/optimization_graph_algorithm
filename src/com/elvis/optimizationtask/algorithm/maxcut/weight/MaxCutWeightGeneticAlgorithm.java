package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.maxcut.weight.ga.CustomChromosome;
import org.jgap.*;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.DefaultConfiguration;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 18:59
 */
public class MaxCutWeightGeneticAlgorithm extends MaxCutWeightAbstract {
    public final static int populationSize = 20;
    protected Configuration gaConf;


    public MaxCutWeightGeneticAlgorithm() {
    }

    public MaxCutWeightGeneticAlgorithm(SimpleWeightGraph graph) {
        super(graph);
    }

    @Override
    public void setGraph(SimpleWeightGraph graph) {
        super.setGraph(graph);
        if (gaConf == null) {
            gaConf = new DefaultConfiguration();
        }
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
        res_mask = new boolean[graph.size()];
        int chromeSize = graph.size();
        int numEvolutions = chromeSize;
        double maxFitness = Math.pow(2.0, chromeSize) - 1;

        Genotype genotype;
        try {
            IChromosome sampleChromosome = new CustomChromosome(gaConf, new BooleanGene(gaConf), chromeSize);
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
        endEvolveAction(genotype);

        IChromosome fittest = genotype.getFittestChromosome();
        int i = 0;
        for (Gene gene : fittest.getGenes()) {
            res_mask[i++] = ((BooleanGene) gene).booleanValue();
        }

    }

    protected void afterEvolveAction(Genotype genotype) {
    }

    protected void endEvolveAction(Genotype genotype) {
    }

    private class MaxCutFitness extends FitnessFunction {
        @Override
        protected double evaluate(IChromosome iChromosome) {
            float cut = cutValue(iChromosome);
            //TODO: crutch, fix it
            if (cut < 0) {
                cut = 0;
            }
            return cut;
        }
    }

    public float cutValue(IChromosome chromosome) {
        float cut = 0;
        for (int i = 0; i < graph.size(); i++) { //calculate cut value
            for (int j = i + 1; j < graph.size(); j++) {
                if (((BooleanGene) chromosome.getGene(i)).booleanValue() != ((BooleanGene) chromosome.getGene(j)).booleanValue()) {
                    cut += graph.getCell(i, j);
                }
            }
        }
        return cut;
    }

    @Override
    public String getHumanID() {
        return "Max Cut Weight Genetic Algorithm";
    }

    @Override
    public String getID() {
        return "MCWGA";
    }
}
