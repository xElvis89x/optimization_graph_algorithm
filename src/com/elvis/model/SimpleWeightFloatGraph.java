package com.elvis.model;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 15:17
 */
public class SimpleWeightFloatGraph extends SimpleAbstractGraph {
    float[][] matrix;

    public void create(int size) {
        matrix = new float[size][size];
    }

    public float[][] getMatrix() {
        return matrix;
    }

    public float getCell(int i, int j) {
        return matrix[i][j];
    }

    public void setCell(int i, int j, float value) {
        matrix[i][j] = value;
    }

    public int size() {
        return matrix.length;
    }


}
