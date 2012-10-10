package com.elvis.optimizationtask.parser;

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
    private static Map<String, Class<? extends AbstractGraphReader>> mapGraphReader = new HashMap<String, Class<? extends AbstractGraphReader>>() {
        {
            put("mb", SimpleMatrixGraphReader.class);
            put("eb", SimpleEdgeGraphReader.class);
        }
    };


    public static AbstractGraphReader getGraphReader(InputStream inputStream) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        byte[] typeByte = new byte[2];
        if (inputStream.read(typeByte) == -1)
            return null;
        return mapGraphReader.get(new String(typeByte)).getConstructor(InputStream.class).newInstance(inputStream);
    }
}
