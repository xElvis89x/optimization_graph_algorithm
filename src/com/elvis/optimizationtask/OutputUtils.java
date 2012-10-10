package com.elvis.optimizationtask;

/**
 * Created by User: el
 * Date: 04.10.12
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public class OutputUtils {
    public static String getBoolMaskPresentation(boolean[] mask) {
        StringBuilder result = new StringBuilder(mask.length * 2);
        for (boolean b : mask) {
            result.append(b ? 1 : 0).append(" ");
        }
        return result.toString();
    }
}
