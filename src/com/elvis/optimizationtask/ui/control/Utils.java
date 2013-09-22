package com.elvis.optimizationtask.ui.control;

import com.elvis.model.SimpleWeightFloatGraph;
import com.elvis.optimizationtask.parser.ParserManager;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class Utils {
    static Random random = new Random();

    public static SimpleWeightFloatGraph getGraph(File file) {
        SimpleWeightFloatGraph result = null;
        if (file != null) {
            FileInputStream stream = null;
            try {
                stream = new FileInputStream(file);
                result = (SimpleWeightFloatGraph) ParserManager.getGraphReader(stream).getGraphFromStream();
                result.setFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }
        return result;
    }


    public static Color getRandomColor() {
        return Color.getHSBColor(random.nextFloat(), 0.3f, 1.0f);
    }
}