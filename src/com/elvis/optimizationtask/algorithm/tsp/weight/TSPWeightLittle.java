package com.elvis.optimizationtask.algorithm.tsp.weight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User: el
 * Date: 15.11.12
 * Time: 12:05
 */
public class TSPWeightLittle extends TSPWeightAbstract {

    @Override
    public void calc() {
    }


    void Lang() {
//        int r = 0;
//        float M = 0;
//        int k = 1;
//        int l = 2;
//        List<curve> R = new ArrayList<curve>();
//        while (k <= graph.size()) {
//            float c_k = minRow(k, l);
//            float c_l = minCol(l, k);
//            float g = c_k + c_l - graph.getCell(k, l);
//            if (g == 0) {
//                R.add(new curve(k, l));
//                r = r + 1;
//                if (c_k != Float.POSITIVE_INFINITY) {
//                    for (int j = 0; j < graph.size(); j++) {
//                        if (j == l) continue;
//                        graph.setCell(k, j, Float.POSITIVE_INFINITY);
//                    }
//                }
//                if (c_l != Float.POSITIVE_INFINITY) {
//                    for (int i = 0; i < graph.size(); i++) {
//                        if (i == k) continue;
//                        graph.setCell(i, k, Float.POSITIVE_INFINITY);
//                    }
//                }
//            }
//            if (g >= M)
//                M = g;
//            k++;
//        }
//
//        if (r > graph.size() - K)
//            M = Float.POSITIVE_INFINITY;
//
//
//        float K_min = 0;
//        if (K_min <= r) {
//            int w = 1;
//            while (w <= K) {
//                Z_w = Z_w - R(G);
//                if (Z_W ==) M = Float.POSITIVE_INFINITY;
//                else w++;
//            }
//        }
//
//        if(M != Float.POSITIVE_INFINITY) {
//
//        }


    }

    class curve {
        curve(int i, int j) {
            this.i = i;
            this.j = j;
        }

        int i;
        int j;
    }

    float minRow(int i, int ex) {
        float result = Float.POSITIVE_INFINITY;
        for (int q = i + 1; q < graph.size(); q++) {
            if (q == ex) continue;
            result = Math.min(graph.getCell(i, q), result);
        }
        return result;
    }

    float minCol(int j, int ex) {
        float result = Float.POSITIVE_INFINITY;
        for (int q = j + 1; q < graph.size(); q++) {
            if (q == ex) continue;
            result = Math.min(graph.getCell(q, j), result);
        }
        return result;
    }


    @Override
    public String getHumanID() {
        return "TSP Weight Little";
    }

    @Override
    public String getID() {
        return "TSPWL";
    }
}
