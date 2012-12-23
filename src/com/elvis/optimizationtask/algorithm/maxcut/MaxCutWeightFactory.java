package com.elvis.optimizationtask.algorithm.maxcut;

import com.elvis.optimizationtask.algorithm.maxcut.weight.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User: el
 * Date: 06.11.12
 * Time: 13:37
 */
public class MaxCutWeightFactory {

    private static MaxCutWeightFactory INSTANCE = new MaxCutWeightFactory();

    public static MaxCutWeightFactory getInstance() {
        return INSTANCE;
    }

    private Map<String, MaxCutCreator> maxCutMap = new HashMap<String, MaxCutCreator>() {
        {
            MaxCutCreator[] maxCut = {
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightBrutForce();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightGeneticAlgorithm();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightGlobalEquilibriumSearch();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightGlobalEquilibriumSearchWithTabu();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightLorena();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightRandom();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightKaporisKirousisStavropoulos();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightPathRelinking();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightGreedy();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightNodeGreedy();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightNodeGreedyMod1();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightGAWithCore();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightGAWithTabu();
                        }
                    },
                    new MaxCutCreator() {
                        @Override
                        public MaxCut create() {
                            return new MaxCutWeightGAWithGreedy();
                        }
                    }
            };

            for (MaxCutCreator cut : maxCut) {
                MaxCut c = cut.create();
                put(c.getHumanID(), cut);
            }
        }
    };

    public List<String> getMaxCutList() {
        return new ArrayList<String>(maxCutMap.keySet());
    }

    public MaxCut getMaxCutInstance(String type) {
        return maxCutMap.get(type).create();
    }

    interface MaxCutCreator {
        MaxCut create();
    }
}

