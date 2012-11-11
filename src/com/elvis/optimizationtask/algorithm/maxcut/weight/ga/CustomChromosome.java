package com.elvis.optimizationtask.algorithm.maxcut.weight.ga;

import org.jgap.*;


public class CustomChromosome extends Chromosome {

    public CustomChromosome() throws InvalidConfigurationException {
        super();
    }

    public CustomChromosome(Configuration a_configuration) throws InvalidConfigurationException {
        super(a_configuration);
    }

    public CustomChromosome(Configuration a_configuration, String a_persistentRepresentatuion) throws InvalidConfigurationException, UnsupportedRepresentationException {
        super(a_configuration, a_persistentRepresentatuion);
    }

    public CustomChromosome(Configuration a_configuration, int a_desiredSize) throws InvalidConfigurationException {
        super(a_configuration, a_desiredSize);
    }

    public CustomChromosome(Configuration a_configuration, Gene a_sampleGene, int a_desiredSize) throws InvalidConfigurationException {
        super(a_configuration, a_sampleGene, a_desiredSize);
    }

    public CustomChromosome(Configuration a_configuration, Gene a_sampleGene, int a_desiredSize, IGeneConstraintChecker a_constraintChecker) throws InvalidConfigurationException {
        super(a_configuration, a_sampleGene, a_desiredSize, a_constraintChecker);
    }

    public CustomChromosome(Configuration a_configuration, Gene[] a_initialGenes) throws InvalidConfigurationException {
        super(a_configuration, a_initialGenes);
    }

    public CustomChromosome(Configuration a_configuration, Gene[] a_initialGenes, IGeneConstraintChecker a_constraintChecker) throws InvalidConfigurationException {
        super(a_configuration, a_initialGenes, a_constraintChecker);
    }

    @Override
    public Object perform(Object a_obj, Class a_class, Object a_params) throws Exception {
        Chromosome c = (Chromosome) super.perform(a_obj, a_class, a_params);
        return new CustomChromosome(c.getConfiguration(), c.getGenes());
    }

    @Override
    public synchronized Gene[] getGenes() {
        Gene[] g = super.getGenes();
        g[0].setAllele(false);
        return g;
    }

    @Override
    public synchronized Gene getGene(int a_desiredLocus) {
        Gene g = super.getGene(a_desiredLocus);
        if (a_desiredLocus == 0) {
            g.setAllele(false);
        }
        return g;
    }
}

