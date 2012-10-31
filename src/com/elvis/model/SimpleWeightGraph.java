package com.elvis.model;

import java.io.File;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 15:17
 */
public class SimpleWeightGraph implements Graph {

    File file;

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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleWeightGraph that = (SimpleWeightGraph) o;

        return !(file != null ? !file.getAbsolutePath().equals(that.file.getAbsolutePath()) : that.file != null);

    }

    @Override
    public int hashCode() {
        return file != null ? file.hashCode() : 0;
    }
}
