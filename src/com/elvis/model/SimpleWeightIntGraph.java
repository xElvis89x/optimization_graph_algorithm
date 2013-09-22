package com.elvis.model;

/**
 * Created by User: el
 * Date: 27.12.12
 * Time: 13:17
 */
public class SimpleWeightIntGraph extends SimpleAbstractGraph {
    int[][] matrix;

    public SimpleWeightIntGraph() {
    }

    public SimpleWeightIntGraph(SimpleWeightFloatGraph g) {
        create(g.size());
        setFile(g.getFile());
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                setCell(i, j, (int) g.getCell(i, j));
            }
        }
    }

    public void create(int size) {
        matrix = new int[size][size];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getCell(int i, int j) {
        return matrix[i][j];
    }

    public void setCell(int i, int j, int value) {
        matrix[i][j] = value;
    }

    public int size() {
        return matrix.length;
    }


}
