package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by User: el
 * Date: 10.10.12
 * Time: 13:44
 */
public class MaxCutWeightGlobalEquilibriumSearch extends MaxCutWeightAbstract {

    public MaxCutWeightGlobalEquilibriumSearch() {
    }

    public MaxCutWeightGlobalEquilibriumSearch(SimpleWeightGraph graph) {
        super(graph);
    }

    int eliteSize;
    int Q;

    Set<boolean[]> eliteSet;
    float u = 0;
    double Z;

    MaxCutWeightTabu localSearch;

    @Override
    public void calc() {
        float a1 = 0.2f, a2 = 0.4f;
        eliteSize = graph.size() * 2;

        int countTemperatureFaze = 50;
        Q = graph.size();

        localSearch = new MaxCutWeightTabu(graph, 20, graph.size() / 2);

        eliteSet = eliteAssign();
        boolean[] p_best = new boolean[graph.size()];
        u = 0;
        float m1 = a1 * graph.size();
        float m2 = a2 * graph.size();
        float m = m1;

        float prevResult = 0;
        int maxn = graph.size() / 2;
        for (int i = 0; i < countTemperatureFaze; i++) {
            float c_best = cutValue(p_best);
            if (prevResult == c_best && i != 0) {
                restart();
            }
            prevResult = c_best;
            for (int j = 0; j < maxn; j++) {
                boolean[] p = chooseCandidate();
                //mutation(p, m);
                //p = tabuSearch(p, p_best, t);
                eliteAdd(p);
                if (cutValue(p) > cutValue(p_best)) {
                    p_best = p;
                    m = m1;
                }
            }
            tempRecalculation(i);
            m = m >= m2 ? m1 : m + 1;
        }
        res_mask = p_best;
    }

    private void restart() {
        int index = rand.nextInt(eliteSet.size());
        boolean[] maskFromSet = (boolean[]) eliteSet.toArray()[index];
        float c = cutValue(maskFromSet);

        for (Object o : eliteSet.toArray()) {
            boolean[] mask = (boolean[]) o;
            if (c > cutValue(mask)) {
                eliteSet.remove(mask);
            }
        }
    }


    private void eliteAdd(boolean[] mask/*, boolean[] mask_old*/) {
        //float cv_old = cutValue(mask_old);
        float cv = cutValue(mask);
        if (!eliteSet.contains(mask) /*&& cv > cv_old*/) {
//            eliteSet.remove(mask_old);
            eliteSet.add(mask);
        }
        Z = Z /*- Math.exp(-u * cv_old)*/ + Math.exp(-u * cv);
    }

    private void mutation(boolean[] mask, float m) {

    }

    private boolean[] tabuSearch(boolean[] mask, boolean[] mask_best) {
        localSearch.setMask(mask);
        localSearch.setMask_best(mask_best);
        localSearch.calc();
        return localSearch.getMask();
    }

    private void tempRecalculation(int step) {
        double coef = 1.8 * 100000000 / graph.size();
        if (step == 0) {
            u = (float) (0.0000007 / coef);
        } else {
            u = (float) (u * (Math.log(10 / coef) - Math.log(0.0000007 / coef)) / 48);
        }
    }

    private boolean[] chooseCandidate() {
        double random = rand.nextDouble() * Z;
        double Zn = 0;
        boolean[] result = null;
        for (boolean[] p : eliteSet) {
            result = p;
            Zn += Math.exp(-u * cutValue(p));
            if (random <= Zn) {
                break;
            }
        }
        return result;
    }

    private Set<boolean[]> eliteAssign() {
        Set<boolean[]> result = new HashSet<boolean[]>();
        Z = 0;
        boolean[] p_best = new boolean[graph.size()];
        for (int i = 0; i < eliteSize; i++) {
            boolean[] randSol = getRandomSolution(graph.size());
//            randSol = tabuSearch(randSol, p_best, t);
            if (cutValue(randSol) > cutValue(p_best)) {
                p_best = randSol;
            }
            result.add(randSol);
            Z = Z + Math.exp(-u * cutValue(randSol));
        }
        return result;
    }


    @Override
    public String getHumanID() {
        return "Max Cut Weight Global Equilibrium Search";
    }

    @Override
    public String getID() {
        return "MCWGES";
    }
}
//        int n_u = 50;
//        int n_p = graph.getSize();
//
//        float u[] = new float[n_u];
//        float a = 1;
//        for (int i = 2; i < n_u; i++) {
//            u[i] = a * u[i - 1];
//        }
//
//        double[][] p_u = new double[n_p][n_u];
//
//
//        for (int k = 0; k < n_u; k++) {
//            for (int j = 0; j < n_p; j++) {
//
////                p_u[j][k] = 1 / (1 + (1 - p_u[j][0]) / p_u[j][0]
////                        * Math.exp(u[k] - u[0]) * (getCutValue(u[k]) - getCutValue(u[k])));
//            }
//        }
