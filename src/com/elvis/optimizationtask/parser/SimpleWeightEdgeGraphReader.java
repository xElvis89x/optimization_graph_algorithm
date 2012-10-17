package com.elvis.optimizationtask.parser;

import com.elvis.model.SimpleWeightGraph;

import java.util.Scanner;

/**
 * Created by User: el
 * Date: 16.10.12
 * Time: 18:35
 */
public class SimpleWeightEdgeGraphReader extends AbstractGraphReader<SimpleWeightGraph> {

    @Override
    public SimpleWeightGraph getGraphFromStream() {
        SimpleWeightGraph graph = new SimpleWeightGraph();

        Scanner scanner = new Scanner(inputStream);

        int size = scanner.nextInt(); //read matrix size from stream
        graph.create(size);
        int countEdge = scanner.nextInt();

        for (int i = 0; i < countEdge; i++) {
            graph.setCell(scanner.nextInt() - 1, scanner.nextInt() - 1, Float.parseFloat(scanner.next()));
        }
        return graph;
    }
}
