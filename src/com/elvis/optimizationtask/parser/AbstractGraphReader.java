package com.elvis.optimizationtask.parser;

import com.elvis.model.SimpleBooleanGraph;

import java.io.InputStream;

/**
 * Created by User: el
 * Date: 05.10.12
 * Time: 10:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractGraphReader {


    protected InputStream inputStream;

    public AbstractGraphReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public abstract SimpleBooleanGraph getGraphFromStream();
}
