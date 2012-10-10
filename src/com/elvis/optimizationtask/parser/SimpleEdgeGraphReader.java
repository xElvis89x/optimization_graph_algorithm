package com.elvis.optimizationtask.parser;

import com.elvis.model.SimpleBooleanGraph;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by User: el
 * Date: 05.10.12
 * Time: 10:47
 * <p>
 * Simple reader for boolean graph from input stream.
 * First integer define count of input string.
 * Next every two integer define existing edge in graph
 * </p>
 * <p>
 * example input data:
 * </p>
 * <table>
 * <tr><td>4</td><td></td><td>- count of graph edge</td></tr>
 * <tr><td>1</td><td>3</td><td>edge exist between 1 and 3 point of graph</td></tr>
 * <tr><td>4</td><td>3</td></tr>
 * <tr><td>2</td><td>1</td></tr>
 * <tr><td>2</td><td>3</td></tr>
 * </table>
 */
public class SimpleEdgeGraphReader extends AbstractGraphReader {
    public SimpleEdgeGraphReader(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public SimpleBooleanGraph getGraphFromStream() {
        SimpleBooleanGraph simpleBooleanGraph = new SimpleBooleanGraph();
        Scanner scanner = new Scanner(inputStream);
        int size = scanner.nextInt(); //read input edge count from stream
        for (int i = 0; i < size; i++) {
            simpleBooleanGraph.setCell(scanner.nextInt() - 1, scanner.nextInt() - 1, true);
        }
        return simpleBooleanGraph;
    }
}
