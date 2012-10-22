package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

/**
 * Created by User: el
 * Date: 10.10.12
 * Time: 13:44
 */
public class MaxCutWeightGlobalEquilibriumSearch extends MaxCutWeightAbstract {
    public MaxCutWeightGlobalEquilibriumSearch(SimpleWeightGraph graph) {
        super(graph);
    }

    int eliteSize;
    int Q;


    Set<boolean[]> eliteSet;
    float u = 0;
    float t;

    @Override
    public void calc() {
        float a1 = 0.2f, a2 = 0.4f;
        eliteSize = graph.getSize() * 2;

        t = graph.getSize() / 2;

        int countTemperatureFaze = 50;
        Q = graph.getSize();


        eliteSet = eliteAssign();
        boolean[] p_best = new boolean[graph.getSize()];
        u = 0;
        float m1 = a1 * graph.getSize();
        float m2 = a2 * graph.getSize();
        float m = m1;

        int maxn = graph.getSize() / 2;
        for (int i = 0; i < countTemperatureFaze; i++) {
            for (int j = 0; j < maxn; j++) {
                boolean[] p = chooseCandidate();
                //mutation(p, m);
                p = tabuSearch(p, p_best, t);
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
        res_maxcut = cutValue(res_mask);
    }

    double Z;

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


    int maxSizeTabuList = 20;

    private boolean[] tabuSearch(boolean[] mask, boolean[] mask_best, float t) {
        float bestValue = cutValue(mask_best);
        boolean[] sBest = mask;
        LinkedList<boolean[]> tabuSet = new LinkedList<boolean[]>();
        for (int i = 0; i < t; i++) {
            Set<boolean[]> candidateSet = new HashSet<boolean[]>();

            Set<boolean[]> neighborhoods = getNeighborhood(sBest);
            for (boolean sCandidate[] : neighborhoods) {
                if (!tabuSet.contains(sCandidate)) {
                    candidateSet.add(sCandidate);
                }
            }
            boolean[] sCandidate = LocateBestCandidate(candidateSet);

            float sCandValue = cutValue(sCandidate);
            if (sCandValue > bestValue) {
                //tabuSet.remove(sBest);
                bestValue = sCandValue;
                sBest = sCandidate;
                tabuSet.add(sBest);
                while (tabuSet.size() > maxSizeTabuList) {
                    tabuSet.removeFirst();
                }
            }
        }
        return sBest;
    }

    boolean[] LocateBestCandidate(Set<boolean[]> set) {
        boolean[] result = (boolean[]) set.toArray()[0];
        float val = 0;
        for (boolean[] booleans : set) {
            float c = cutValue(booleans);
            if (c > val) {
                result = booleans;
                val = c;
            }
        }
        return result;
    }

    Set<boolean[]> getNeighborhood(boolean[] mask) {
        Set<boolean[]> result = new HashSet<boolean[]>();
        for (int i = 0; i < mask.length; i++) {
            boolean[] next = mask.clone();
            next[i] = !next[i];
            result.add(next);
        }
        return result;
    }

    private void tempRecalculation(int step) {
        double coef = 1.8 * 100000000 / graph.getSize();
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
        boolean[] p_best = new boolean[graph.getSize()];
        for (int i = 0; i < eliteSize; i++) {
            boolean[] randSol = generateRandomSolution(graph.getSize());
            tabuSearch(randSol, p_best, t);
            if (cutValue(randSol) > cutValue(p_best)) {
                p_best = randSol;
            }
            result.add(randSol);
            Z = Z + Math.exp(-u * cutValue(randSol));
        }
        return result;
    }

    Random rand = new Random(System.nanoTime());

    boolean[] generateRandomSolution(int n) {
        boolean[] result = new boolean[n];
        for (int i = 0; i < n; i++) {
            result[i] = rand.nextBoolean();
        }
        return result;
    }

    public float cutValue(boolean[] mask) {
        float cut = 0;
        for (int i = 0; i < graph.getSize(); i++) {
            for (int j = i + 1; j < graph.getSize(); j++) {
                cut += graph.getCell(i, j) * (mask[i] != mask[j] ? 1 : 0);
            }
        }
        return cut;
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
