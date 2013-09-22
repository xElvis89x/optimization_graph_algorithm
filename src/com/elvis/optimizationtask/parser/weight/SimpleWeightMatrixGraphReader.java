package com.elvis.optimizationtask.parser.weight;

import com.elvis.model.SimpleWeightFloatGraph;
import com.elvis.optimizationtask.parser.AbstractGraphReader;

import java.util.Scanner;

/**
 * Created by User: el
 * Date: 12.10.12
 * Time: 1:25
 */
public class SimpleWeightMatrixGraphReader extends AbstractGraphReader<SimpleWeightFloatGraph> {
    @Override
    public SimpleWeightFloatGraph getGraphFromStream() {
        SimpleWeightFloatGraph simpleWeightFloatGraph = new SimpleWeightFloatGraph();

        Scanner scanner = new Scanner(inputStream);

        int size = scanner.nextInt(); //read matrix size from stream
        simpleWeightFloatGraph.create(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //bug with scanner.nextFloat, not understand number with point (example: 10.0)
                simpleWeightFloatGraph.setCell(i, j, Float.parseFloat(scanner.next()));
            }
        }
        return simpleWeightFloatGraph;
    }
}
