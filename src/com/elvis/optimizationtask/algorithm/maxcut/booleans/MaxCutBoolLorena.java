package com.elvis.optimizationtask.algorithm.maxcut.booleans;

import com.elvis.model.SimpleBooleanGraph;

import java.util.Random;

/**
 * Created by User: el
 * Date: 02.10.12
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
public class MaxCutBoolLorena extends MaxCutBoolAbstract {
    public MaxCutBoolLorena(SimpleBooleanGraph simpleBooleanGraph) {
        super(simpleBooleanGraph);
    }

    private static final double EPSILON = 0.0001;
    private static final double INFINITY = 100000000;

    @Override
    public void calc() {
        int i, j, k;
        double g, x, sum, sum1, sum2, t;

        int n = simpleBooleanGraph.getSize();
        res_mask = new boolean[n];
        double q[] = new double[n];
        double y[] = new double[n]; //values [0..2*pi]

        //init q[] with random numbers from [0..2*pi]
        Random rand = new Random(System.nanoTime());
        for (k = 0; k < n; k++) {
            q[k] = rand.nextDouble() * Math.PI * 2;
        }

        do {
            //for (k = 0; k < n; k++) y[k] = q[k];
            for (k = 0; k < n; k++) {
                y[k] = q[k];
                //for (p = 0.0; p <= pi2; p+= 0.0001) { //increase precision
                for (j = 0, sum1 = sum2 = 0.0; j < n; j++) {
                    if (simpleBooleanGraph.getCell(k, j)) {
                        sum1 += Math.sin(y[j]);
                        sum2 += Math.cos(y[j]);
                    }
                }
                //}
                q[k] = Math.atan(sum1 / sum2); //argmin
            }
            for (k = 0, t = 0; k < n; k++) {
                t = Math.max(t, Math.min(Math.abs(y[k] - q[k]), Math.abs(Math.PI * 2 - (y[k] - q[k]))));
            }
        } while (t < EPSILON);

        for (k = 0, g = INFINITY, x = -1; k < n; k++) {
            for (i = 0, sum = 0.0; i < n; i++) {
                for (j = i + 1; j < n; j++) {
                    sum += simpleBooleanGraph.getCell(i, j) ? 1 : 0
                            * Math.signum(Math.sin(q[j] - q[k]) * Math.sin(q[i] - q[k]));
                }
            }
            if (g > sum) {
                g = sum;
                x = q[k];
            }
        }
        for (k = 0; k < n; k++) {
            res_mask[k] = Math.signum(Math.sin(q[k] - x)) != 1;
        }

        for (i = 0, res_maxcut = 0; i < n; i++) {
            for (j = i + 1; j < n; j++) {
                res_maxcut += (simpleBooleanGraph.getCell(i, j) && res_mask[i] != res_mask[j]) ? 1 : 0;
            }
        }
    }

    @Override
    public String getHumanID() {
        return "MaxCut Lorena Algorithm";
    }

    @Override
    public String getID() {
        return "MCLA";
    }
}
