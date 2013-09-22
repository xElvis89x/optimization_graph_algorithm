package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.optimizationtask.algorithm.maxcut.weight.local.MaxCutWeightTabu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User: el
 * Date: 20.12.12
 * Time: 9:51
 */
public class MaxCutWeightPathRelinking extends MaxCutWeightAbstract {
    @Override
    public void calc() {
        int b = 10;

        List<boolean[]> refSet = new ArrayList<boolean[]>();
        for (int i = 0; i < b; ) {
            boolean[] randomSolution = getRandomSolution(graph.size());
            if (!refSet.contains(randomSolution)) {
                refSet.add(randomSolution);
                i++;
            }
        }

        boolean[] best = findBest(refSet);
        boolean[] worst = findWorst(refSet);
        float best_value = cutValue(best);

        List<Pair> pairSet = new ArrayList<Pair>();
        for (int i = 0; i < b; i++) {
            for (int j = i + 1; j < b; j++) {
                pairSet.add(new Pair(i, j));
            }
        }

        boolean[] tags = new boolean[refSet.size()];

        while (!pairSet.isEmpty()) {
            int i = pairSet.get(pairSet.size() - 1).i,
                    j = pairSet.get(pairSet.size() - 1).j;

            for (int l = 0; l < 2; l++) {
                int tmp = i;
                i = j;
                j = tmp;

                List<boolean[]> diffSet = pathRelinking(refSet.get(i), refSet.get(j));
                if (diffSet.size() < 2) {
                    continue;
                }

                boolean[] x_m = diffSet.get(rand.nextInt(diffSet.size() - 1));
                MaxCutWeightTabu tabu = new MaxCutWeightTabu(graph, 10, graph.size() / 2);
                tabu.setMask_best(best);
                tabu.setMask(x_m);
                tabu.calc();
                x_m = tabu.getResult();

                float x_m_value = cutValue(x_m);
                if (x_m_value > best_value) {
                    best = x_m;
                    best_value = x_m_value;
                }
                if (refSet.indexOf(x_m) == -1) {
                    int index = refSet.indexOf(worst);
                    refSet.set(index, x_m);
                    tags[index] = true;
                    worst = findWorst(refSet);
                }
            }

            pairSet.remove(pairSet.size() - 1);
        }

        res_mask = best;
    }

    class Pair {

        Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }

        int i;
        int j;
    }

    boolean[] findWorst(List<boolean[]> set) {
        boolean[] result = set.get(0);
        float result_value = cutValue(result);
        for (boolean[] booleans : set) {
            float v = cutValue(booleans);
            if (v < result_value) {
                result = booleans;
                result_value = v;
            }
        }
        return result;
    }

    boolean[] findBest(List<boolean[]> set) {
        boolean[] result = set.get(0);
        float result_value = cutValue(result);
        for (boolean[] booleans : set) {
            float v = cutValue(booleans);
            if (v > result_value) {
                result = booleans;
                result_value = v;
            }
        }
        return result;
    }


    List<boolean[]> pathRelinking(boolean[] x_i, boolean x_j[]) {
        List<boolean[]> x = new ArrayList<boolean[]>();

        List<Integer> set = diffSet(x_i, x_j);
        int r = set.size() - 1;
        List<Integer> PV = new ArrayList<Integer>();

        float FI = 0;
        for (int k = 0; k < r; k++) {
            Integer t = set.get(rand.nextInt(set.size()));
            PV.add(t);
            x.add(Arrays.copyOf(x_i, x_i.length));
            int v = x.size() - 1;
            for (int i = 0; i < x_i.length; i++) {
                x.get(v)[i] = PV.contains(i) ? x_j[i] : x_i[i];
            }
            //  FI = cutValue(x_i) - cutValue(x.get(x.size() - 1));
            set.remove(t);
        }
        return x;
    }


    List<Integer> diffSet(boolean[] x_i, boolean[] x_j) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < x_i.length; i++) {
            if (x_i[i] != x_j[i]) {
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public String getHumanID() {
        return "Max Cut Weight Path Relinking";
    }

    @Override
    public String getID() {
        return "MCWPR";
    }
}
