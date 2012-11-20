package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by User: el
 * Date: 17.11.12
 * Time: 11:39
 * Жадный алгоритм в качестве оценки набора вершин использует
 * сумму весов ребер исходящих из этой вершины
 */
public class MaxCutWeightNodeGreedy extends MaxCutWeightAbstract {
    public MaxCutWeightNodeGreedy(SimpleWeightGraph graph) {
        super(graph);
    }

    public MaxCutWeightNodeGreedy() {
    }

    @Override
    public void calc() {
        List<Integer> nodeList = new ArrayList<Integer>(graph.size());
        for (int i = 0; i < graph.size(); i++) {
            nodeList.add(i);
        }
        Collections.sort(nodeList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return (int) (markNode(o1) - markNode(o2));
            }
        });
        resolveResult(nodeList);
    }

    protected void resolveResult(List<Integer> nodeList) {
        res_mask = new boolean[graph.size()];

        boolean currentFlag = nodeList.indexOf(0) % 2 != 0;
        for (Integer integer : nodeList) {
            res_mask[integer] = currentFlag;
            currentFlag = !currentFlag;
        }
    }

    float markNode(int index) {
        float result = 0;
        for (int i = 0; i < graph.size(); i++) {
            if (i != index) {
                result += graph.getCell(i, index);
            }
        }
        return result;
    }


    @Override
    public String getHumanID() {
        return "Max Cut Weight Node Greedy";
    }

    @Override
    public String getID() {
        return "MCWNG";
    }
}
