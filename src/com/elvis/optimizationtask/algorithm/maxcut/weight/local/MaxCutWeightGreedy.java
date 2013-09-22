package com.elvis.optimizationtask.algorithm.maxcut.weight.local;

import com.elvis.model.SimpleWeightFloatGraph;
import com.elvis.optimizationtask.algorithm.maxcut.weight.MaxCutWeightAbstract;

/**
 * Created by User: el
 * Date: 17.11.12
 * Time: 1:57
 * Жадный алгоритм локального поиска
 * Сложность O(n)
 * Перебирая последовательно вершины
 * на каждом шаге пытаемся улучшить результат
 */
public class MaxCutWeightGreedy extends MaxCutWeightAbstract {
    public MaxCutWeightGreedy(SimpleWeightFloatGraph floatGraph) {
        super(floatGraph);
    }

    public MaxCutWeightGreedy() {
    }

    boolean[] mask_best;

    public void setMask_best(boolean[] mask_best) {
        this.mask_best = mask_best;
    }

    @Override
    public void calc() {
        res_mask = mask_best.clone();
        float best = cutValue(res_mask);
        System.out.println("GA =" + best);
        for (int i = 0; i < res_mask.length; i++) {
            float markTrue = markNode(i, res_mask[i], res_mask);
            float markFalse = markNode(i, !res_mask[i], res_mask);

            if (best < best - markTrue + markFalse) {
                best = best - markTrue + markFalse;
                res_mask[i] = !res_mask[i];
            }
        }
        System.out.println("GA + Greedy = " + cutValue(res_mask));
    }

    float markNode(int index, boolean subset, boolean[] mask) {
        float result = 0;
        for (int i = 0; i < graph.size(); i++) {
            if (mask[i] != subset && i != index) {
                result += graph.getCell(i, index);
            }
        }
        return result;
    }

    @Override
    public String getHumanID() {
        return "Max Cur Greedy";
    }

    @Override
    public String getID() {
        return "MCG";
    }
}
