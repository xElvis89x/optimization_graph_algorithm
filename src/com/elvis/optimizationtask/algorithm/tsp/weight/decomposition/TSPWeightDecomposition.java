package com.elvis.optimizationtask.algorithm.tsp.weight.decomposition;

import com.elvis.model.SimpleWeightFloatGraph;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;
import com.elvis.optimizationtask.algorithm.tsp.TSP;
import com.elvis.optimizationtask.algorithm.tsp.weight.TSPWeightAbstract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User: el
 * Date: 02.11.12
 * Time: 13:56
 */
public class TSPWeightDecomposition extends TSPWeightAbstract {
    private MaxCut decompositionAlgorithm;
    private TSP tsp;

    public TSPWeightDecomposition(SimpleWeightFloatGraph floatGraph) {
        super(floatGraph);
    }

    public void setDecompositionAlgorithm(MaxCut decompositionAlgorithm) {
        this.decompositionAlgorithm = decompositionAlgorithm;
    }

    public void setTsp(TSP tsp) {
        this.tsp = tsp;
    }

    @Override
    public void calc() {

        decompositionAlgorithm.setGraph(graph);
        decompositionAlgorithm.solve();
        boolean[] mask = decompositionAlgorithm.getResult();
        List<Integer> graphDec1 = new ArrayList<Integer>(); // array node index
        List<Integer> graphDec2 = new ArrayList<Integer>();
        for (int i = 0; i < mask.length; i++) {
            (mask[i] ? graphDec1 : graphDec2).add(i);
        }
        result = new int[graph.size()];
        int resultIndex = 0;
        Arrays.fill(result, -1);

        resultIndex = SolveDecompositionGraph(graphDec1, resultIndex);
        resultIndex = SolveDecompositionGraph(graphDec2, resultIndex);

    }

    private int SolveDecompositionGraph(List<Integer> graphDec, int resultIndex) {
        tsp.setGraph(getDecompositeGraph(graphDec));
        tsp.solve();
        for (int i = 0; i < graphDec.size(); i++) {
            result[resultIndex++] = graphDec.get(tsp.getResult()[i]);
        }
        return resultIndex;
    }

    private SimpleWeightFloatGraph getDecompositeGraph(List<Integer> graphDec) {
        SimpleWeightFloatGraph result = new SimpleWeightFloatGraph();
        result.create(graphDec.size());
        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.size(); j++) {
                float edge = graph.getCell(graphDec.get(i), graphDec.get(j));
                result.setCell(i, j, edge);
                result.setCell(j, i, edge);
            }
        }
        return result;
    }

    @Override

    public String getHumanID() {
        return "TSP Weight Decomposition Algorithm" +
                "(decomposition algoritm = " + decompositionAlgorithm.getHumanID() + "," +
                "tsp solve algorithm = " + tsp.getHumanID() + ")";
    }

    @Override
    public String getID() {
        return "TSPWDA(" + decompositionAlgorithm.getID() + "," + tsp.getID() + ")";
    }
}
