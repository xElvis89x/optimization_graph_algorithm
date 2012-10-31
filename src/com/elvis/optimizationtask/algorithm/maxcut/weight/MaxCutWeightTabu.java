package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by User: el
 * Date: 28.10.12
 * Time: 20:41
 */
public class MaxCutWeightTabu extends MaxCutWeightAbstract {
    public MaxCutWeightTabu(SimpleWeightGraph graph, int tabuListSize, int cycleCount) {
        super(graph);
        this.cycleCount = cycleCount;
        this.maxSizeTabuList = tabuListSize;
    }

    int maxSizeTabuList = 20;
    int cycleCount;
    boolean[] mask_best;
    boolean[] mask;

    public void setMask_best(boolean[] mask_best) {
        this.mask_best = mask_best;
    }

    public void setMask(boolean[] mask) {
        this.mask = mask;
    }

    @Override
    public void calc() {
        float bestValue = cutValue(mask_best);
        res_mask = mask;
        LinkedList<boolean[]> tabuSet = new LinkedList<boolean[]>();
        Set<boolean[]> candidateSet = new HashSet<boolean[]>();
        for (int i = 0; i < cycleCount; i++) {
            candidateSet.clear();

            Set<boolean[]> neighborhoods = getNeighborhood(res_mask);
            for (boolean sCandidate[] : neighborhoods) {
                if (!tabuSet.contains(sCandidate)) {
                    candidateSet.add(sCandidate);
                }
            }
            boolean[] sCandidate = LocateBestCandidate(candidateSet);

            float sCandValue = cutValue(sCandidate);
            if (sCandValue > bestValue) {
                bestValue = sCandValue;
                res_mask = sCandidate;
                tabuSet.add(res_mask);
                while (tabuSet.size() > maxSizeTabuList) {
                    tabuSet.removeFirst();
                }
            }
        }
    }

    boolean[] LocateBestCandidate(Set<boolean[]> set) {
        boolean[] result = null;
        float val = 0;
        for (boolean[] mask : set) {
            float c = cutValue(mask);
            if (c > val) {
                result = mask;
                val = c;
            }
        }
        return result;
    }

    Set<boolean[]> neighborhoodSet = new HashSet<boolean[]>();

    Set<boolean[]> getNeighborhood(boolean[] mask) {
        neighborhoodSet.clear();
        for (int i = 0; i < mask.length; i++) {
            boolean[] next = mask.clone();
            next[i] = !next[i];
            neighborhoodSet.add(next);
        }
        return neighborhoodSet;
    }

    @Override
    public String getHumanID() {
        return "Max Cut Weight Tabu";
    }

    @Override
    public String getID() {
        return "MCWT";
    }
}
