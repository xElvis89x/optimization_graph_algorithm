package com.elvis.optimizationtask.parser;

import com.elvis.optimizationtask.parser.bool.SimpleBoolEdgeGraphReader;
import com.elvis.optimizationtask.parser.bool.SimpleBoolMatrixGraphReader;
import com.elvis.optimizationtask.parser.weight.SimpleWeightEdgeGraphReader;
import com.elvis.optimizationtask.parser.weight.SimpleWeightMatrixGraphReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User: el
 * Date: 05.10.12
 * Time: 19:36
 * mb - matrix boolean graph presentation
 * eb - edge boolean graph presentation
 */
public class ParserManager {
    private static Map<String, AbstractGraphReader> mapGraphReader = new HashMap<String, AbstractGraphReader>() {
        {
            put("mb", new SimpleBoolMatrixGraphReader());
            put("eb", new SimpleBoolEdgeGraphReader());
            put("mw", new SimpleWeightMatrixGraphReader());
            put("ew", new SimpleWeightEdgeGraphReader());
        }
    };


    public static AbstractGraphReader getGraphReader(InputStream inputStream) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        byte[] typeByte = new byte[2];
        if (inputStream.read(typeByte) == -1)
            return null;
        AbstractGraphReader reader = mapGraphReader.get(new String(typeByte));
        reader.setInputStream(inputStream);
        return reader;
    }
}
