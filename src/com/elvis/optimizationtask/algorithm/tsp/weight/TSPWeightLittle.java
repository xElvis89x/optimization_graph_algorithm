package com.elvis.optimizationtask.algorithm.tsp.weight;

import com.elvis.optimizationtask.algorithm.tsp.weight.little.HungarianAlgorithm;
import com.elvis.optimizationtask.ui.control.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User: el
 * Date: 15.11.12
 * Time: 12:05
 */
public class TSPWeightLittle extends TSPWeightAbstract {

    public static void main(String[] args) {
        TSPWeightLittle little = new TSPWeightLittle();
        try {
            little.setGraph(Utils.getGraph(new File("E:\\Projects\\AlgorithmOnGraph\\resources\\LitlleTest.graph")));
            little.calc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<curve> X = new ArrayList<curve>();

    @Override
    public void calc() {
        STEP_0_RESULT step_0_result = step0();

        float[][] C_copy = getCopy(step_0_result.matrix);
        STEP_1_RESULT step_1_result = step1(C_copy, step_0_result);

//            X.add(step_1_result.Z[0][0]);
//
//        step2(step_1_result.C$, step_0_result.u);
    }

    class STEP_0_RESULT {
        int[][] permutation;
        float[][] matrix;
        float price;
        float castSum;

        STEP_0_RESULT(int[][] permutation, float[][] matrix, float price, float castSum) {
            this.permutation = permutation;
            this.matrix = matrix;
            this.price = price;
            this.castSum = castSum;
        }
    }

    private STEP_0_RESULT step0() {
        float[][] C1 = getCopy(graph.getMatrix());
        fixMatrix(C1);
        float castSum1 = 0;
        castSum1 += castByRow(C1);
        castSum1 += castByColumn(C1);

        float[][] C2 = getCopy(graph.getMatrix());
        fixMatrix(C2);
        float castSum2 = 0;
        castSum2 += castByColumn(C2);
        castSum2 += castByRow(C2);

        float[][] C = castSum1 < castSum2 ? C1 : C2;
        float castSum = castSum1 < castSum2 ? castSum1 : castSum2;
        int[][] permutation = new HungarianAlgorithm().computeAssignments(getCopy(C));

        float C_d = castSum;
        for (int i = 0; i < permutation.length; i++) {
            C_d += C[permutation[i][0]][permutation[i][1]];
        }

        return new STEP_0_RESULT(permutation, C, C_d, castSum);
    }

    class STEP_1_RESULT {
        int[][] Z;
        float[][] C$;
    }

    private STEP_1_RESULT step1(float[][] c_copy, STEP_0_RESULT step_0_result) {
        LANG(c_copy);
        return null;
    }

    private void step2(float[][] C$, float u) {
        C$[X.get(X.size() - 1).i][X.get(X.size() - 1).j] = Float.POSITIVE_INFINITY;
        int[][] permutation = new HungarianAlgorithm().computeAssignments(getCopy(C$));
        float C_d = 0;
        for (int i = 0; i < permutation.length; i++) {
            C_d += C$[permutation[i][0]][permutation[i][1]];
        }


    }


    float[][] getCopy(float[][] m) {
        float[][] result = new float[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            result[i] = Arrays.copyOf(m[i], m[i].length);
        }
        return result;
    }

    float castByRow(float[][] matrix) {
        float result = 0;
        for (int i = 0; i < matrix.length; i++) {
            float min = Float.POSITIVE_INFINITY;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
            if (min != Float.POSITIVE_INFINITY) {
                result += min;
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] -= min;
                }
            }
        }
        return result;
    }

    float castByColumn(float[][] matrix) {
        float result = 0;
        for (int i = 0; i < matrix.length; i++) {
            float min = Float.POSITIVE_INFINITY;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[j][i] < min) {
                    min = matrix[j][i];
                }
            }
            if (min != Float.POSITIVE_INFINITY) {
                result += min;
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[j][i] -= min;
                }
            }
        }
        return result;
    }

    void fixMatrix(float[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][j] = Float.POSITIVE_INFINITY;
                }
            }
        }
    }


    void LANG(float[][] C) {
        int r = 0;
        float M = 0;
        int k = 1;
        int l = 2;
        List<curve> R = new ArrayList<curve>();
        while (k <= C.length) {
            float c_k = minRow(k, l, C);
            float c_l = minCol(l, k, C);
            float g = c_k + c_l - C[k][l];
            if (g == 0) {
                R.add(new curve(k, l));
                r = r + 1;
                if (c_k != Float.POSITIVE_INFINITY) {
                    for (int j = 0; j < C.length; j++) {
                        if (j != l) {
                            C[k][j] = Float.POSITIVE_INFINITY;
                        }
                    }
                }
                if (c_l != Float.POSITIVE_INFINITY) {
                    for (int i = 0; i < C.length; i++) {
                        if (i == k) {
                            C[i][k] = Float.POSITIVE_INFINITY;
                        }
                    }
                }
            }
            if (g >= M) {
                M = g;
            }
            k++;
        }

//        if (r > C.length - K) {
//            M = Float.POSITIVE_INFINITY;
//        }
//
//        List<> Z_w
//
//        float K_min = 0;
//        if (K_min <= r) {
//            int w = 1;
//            while (w <= K) {
//                Z_w = Z_w - R(G);
//                if (Z_W ==) {
//                    M = Float.POSITIVE_INFINITY;
//                } else {
//                    w++;
//                }
//            }
//        }

        if (M != Float.POSITIVE_INFINITY) {

        }


    }

    class curve {
        curve(int i, int j) {
            this.i = i;
            this.j = j;
        }

        int i;
        int j;
    }

    float minRow(int i, int ex, float[][] matrix) {
        float result = Float.POSITIVE_INFINITY;
        for (int q = i + 1; q < matrix.length; q++) {
            if (q == ex) continue;
            result = Math.min(matrix[i][q], result);
        }
        return result;
    }

    float minCol(int j, int ex, float[][] matrix) {
        float result = Float.POSITIVE_INFINITY;
        for (int q = j + 1; q < matrix.length; q++) {
            if (q == ex) continue;
            result = Math.min(matrix[q][j], result);
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
