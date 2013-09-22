package com.elvis.model;

import java.io.File;

/**
 * Created by User: el
 * Date: 27.12.12
 * Time: 13:44
 */
public class SimpleAbstractGraph implements Graph {
    File file;

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

        SimpleWeightFloatGraph that = (SimpleWeightFloatGraph) o;

        return !(file != null ? !file.getAbsolutePath().equals(that.file.getAbsolutePath()) : that.file != null);

    }

    @Override
    public int hashCode() {
        return file != null ? file.hashCode() : 0;
    }


}
