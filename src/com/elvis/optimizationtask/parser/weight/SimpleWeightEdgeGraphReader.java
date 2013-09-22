package com.elvis.optimizationtask.parser.weight;

import com.elvis.model.SimpleWeightFloatGraph;
import com.elvis.optimizationtask.parser.AbstractGraphReader;

import java.util.Scanner;

/**
 * Created by User: el
 * Date: 16.10.12
 * Time: 18:35
 */
public class SimpleWeightEdgeGraphReader extends AbstractGraphReader<SimpleWeightFloatGraph> {

    @Override
    public SimpleWeightFloatGraph getGraphFromStream() {
        SimpleWeightFloatGraph floatGraph = new SimpleWeightFloatGraph();

        Scanner scanner = new Scanner(inputStream);

        int size = scanner.nextInt(); //read matrix size from stream
        floatGraph.create(size);
        int countEdge = scanner.nextInt();

        for (int i = 0; i < countEdge; i++) {
            floatGraph.setCell(scanner.nextInt() - 1, scanner.nextInt() - 1, Float.parseFloat(scanner.next()));
        }
        return floatGraph;
    }
}
