package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.Graph;
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
    public void setGraph(Graph graph) {
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
        Genotype genotype;
        try {
            IChromosome sampleChromosome = new CustomChromosome(gaConf, new BooleanGene(gaConf), chromeSize);
            gaConf.setSampleChromosome(sampleChromosome);
            genotype = Genotype.randomInitialGenotype(gaConf);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return;
        }

        for (int i = 0; i < chromeSize; i++) {
            genotype.evolve();                // 1 step evolution
            afterEvolveAction(genotype);
        }
        endEvolveAction(genotype);
    }

    protected boolean[] convertChromosomeToArray(IChromosome chromosome) {
        int i = 0;
        boolean[] result = new boolean[chromosome.getGenes().length];
        for (Gene gene : chromosome.getGenes()) {
            result[i++] = ((BooleanGene) gene).booleanValue();
        }
        return result;
    }

    protected void afterEvolveAction(Genotype genotype) {
    }

    protected void endEvolveAction(Genotype genotype) {
        res_mask = convertChromosomeToArray(genotype.getFittestChromosome());
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
