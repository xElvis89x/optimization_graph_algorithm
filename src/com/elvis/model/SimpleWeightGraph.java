package com.elvis.model;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 15:17
 */
public class SimpleWeightGraph implements Graph {
    float[][] matrix;


    public float[][] getMatrix() {
        return matrix;
    }

    public void create(int size) {
        matrix = new float[size][size];
    }

    public float getCell(int i, int j) {
        return matrix[i][j];
    }

    public void setCell(int i, int j, float value) {
        matrix[i][j] = value;
    }

    public int getSize() {
        return matrix.length;
    }

}
