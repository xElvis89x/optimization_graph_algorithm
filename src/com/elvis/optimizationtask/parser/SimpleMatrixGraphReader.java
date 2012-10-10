package com.elvis.optimizationtask.parser;

import com.elvis.model.SimpleBooleanGraph;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by User: el
 * Date: 02.10.12
 * Time: 18:28
 * <p>
 * Simple reader for boolean graph from input stream
 * First integer param define size of matrix which present graph
 * Next quad of integer define matrix
 * </p>
 * example input data:
 * <table>
 * <tr><td>4</td></tr>
 * <tr><td>1</td><td>1</td><td>0</td><td>0</td><td width="80%"></td></tr>
 * <tr><td>1</td><td>1</td><td>1</td><td>1</td><td></td></tr>
 * <tr><td>0</td><td>1</td><td>1</td><td>1</td><td></td></tr>
 * <tr><td>0</td><td>1</td><td>1</td><td>1</td><td></td></tr>
 * </table>
 */
public class SimpleMatrixGraphReader extends AbstractGraphReader {

    public SimpleMatrixGraphReader(InputStream inputStream) {
        super(inputStream);
    }


    public SimpleBooleanGraph getGraphFromStream() {
        SimpleBooleanGraph simpleBooleanGraph = new SimpleBooleanGraph();

        Scanner scanner = new Scanner(inputStream);

        int size = scanner.nextInt(); //read matrix size from stream
        simpleBooleanGraph.create(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                simpleBooleanGraph.setCell(i, j, scanner.nextInt() != 0);
            }
        }
        return simpleBooleanGraph;
    }
}
