package com.elvis.optimizationtask.parser;

import com.elvis.model.SimpleWeightGraph;

import java.util.Scanner;

/**
 * Created by User: el
 * Date: 12.10.12
 * Time: 1:25
 */
public class SimpleWeightMatrixGraphReader extends AbstractGraphReader<SimpleWeightGraph> {
    @Override
    public SimpleWeightGraph getGraphFromStream() {
        SimpleWeightGraph simpleWeightGraph = new SimpleWeightGraph();

        Scanner scanner = new Scanner(inputStream);

        int size = scanner.nextInt(); //read matrix size from stream
        simpleWeightGraph.create(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                simpleWeightGraph.setCell(i, j, Float.parseFloat(scanner.next()));
            }
        }
        return simpleWeightGraph;
    }
}
