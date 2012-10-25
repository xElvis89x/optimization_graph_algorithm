package com.elvis.model;

/**
 * Created by User: el
 * Date: 02.10.12
 * Time: 17:46
 * To change this template use File | Settings | File Templates.
 */
public class SimpleBooleanGraph implements Graph {
    boolean[][] matrix;


    public void create(int size) {
        matrix = new boolean[size][size];
    }

    public boolean getCell(int i, int j) {
        return matrix[i][j];
    }

    public void setCell(int i, int j, boolean value) {
        matrix[i][j] = value;
    }

    public int getSize() {
        return matrix.length;
    }
}
