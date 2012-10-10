package com.elvis.optimizationtask.algorithm.maxcut.booleans;

import com.elvis.model.SimpleBooleanGraph;
import org.jgap.*;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.DefaultConfiguration;

/**
 * Created by User: el
 * Date: 06.10.12
 * Time: 14:43
 */
public class MaxCutBoolGeneticAlgorithm extends MaxCutBoolAbstract {

    final static int populationSize = 20;
    Configuration gaConf = new DefaultConfiguration();


    public MaxCutBoolGeneticAlgorithm(SimpleBooleanGraph simpleBooleanGraph) {
        super(simpleBooleanGraph);
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
        res_mask = new boolean[simpleBooleanGraph.getSize()];
        int chromeSize = simpleBooleanGraph.getSize();
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
            if (percentEvolution > 0 && i % percentEvolution == 0) {
                IChromosome fittest = genotype.getFittestChromosome();
                double fitness = fittest.getFitnessValue();
                if (fitness >= maxFitness) {
                    break;
                }
            }
        }

        IChromosome fittest = genotype.getFittestChromosome();

        res_maxcut = (long) fittest.getFitnessValue();
        int i = 0;
        for (Gene gene : fittest.getGenes()) {
            res_mask[i++] = ((BooleanGene) gene).booleanValue();
        }
    }

    private class MaxCutFitness extends FitnessFunction {
        @Override
        protected double evaluate(IChromosome iChromosome) {
            int i, j, cut = 0, n = simpleBooleanGraph.getSize();
            for (i = 0; i < n; i++) { //calculate cut value
                for (j = i + 1; j < n; j++) {
                    cut += (simpleBooleanGraph.getCell(i, j) &&
                            ((BooleanGene) iChromosome.getGene(i)).booleanValue() != ((BooleanGene) iChromosome.getGene(j)).booleanValue()) ? 1 : 0;
                }
            }
            return cut;
        }
    }
//
//    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//
//        AbstractGraphReader reader = ParserManager.getGraphReader(new FileInputStream("E:\\Projects\\AlgorithmOnGraph\\resources\\test2.graph"));
//        MaxCutBoolGeneticAlgorithm algorithm = new MaxCutBoolGeneticAlgorithm(reader.getGraphFromStream());
//        algorithm.calc();
//
//        System.out.println("algorithm.getMaxCut() = " + algorithm.getMaxCut());
//    }

    @Override
    public String getHumanID() {
        return "MaxCut Genetic algorithm";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getID() {
        return "MCGA";
    }
}
