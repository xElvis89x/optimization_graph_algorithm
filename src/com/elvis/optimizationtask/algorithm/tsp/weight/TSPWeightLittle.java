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
    int m;

    @Override
    public void calc() {
        int[][] permutation;
        STEP_0_RESULT step_0_result = step0();
        float[][] C_copy = getCopy(step_0_result.matrix);
        if (isCircle(step_0_result.permutation)) {
            permutation = step_0_result.permutation;
            return;
        }
        while (true) {


            STEP_1_RESULT step_1_result = step1(C_copy, step_0_result);
            //   X.add(step_1_result.Z[0][0]);
            STEP_2_RESULT step_2_result = step2(step_1_result, step_0_result.castSum);

            STEP_3_RESULT step_3_result = step3(step_1_result, step_2_result);

            STEP_4_RESULT step_4_result = step4(step_1_result, step_3_result, step_2_result.permutation, step_0_result.castSum);

            STEP_5_RESULT step_5_result = step5(step_0_result, step_1_result, step_2_result, step_4_result);

            if (step_5_result.endAlg) {
                break;
            }
            C_copy = step_5_result.C;
            step_0_result.permutation = step_5_result.permutation;
        }
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

        m = 1;
        return new STEP_0_RESULT(permutation, C, C_d, castSum);
    }

    class STEP_1_RESULT {
        float[][] C$;
        float[][] Y;
        float M;
        int n;
        int k;
        int l;

        STEP_1_RESULT(float[][] c$, float[][] y, float m, int n, int k, int l) {
            C$ = c$;
            Y = y;
            M = m;
            this.n = n;
            this.k = k;
            this.l = l;
        }
    }

    private STEP_1_RESULT step1(float[][] c_copy, STEP_0_RESULT step_0_result) {
        return LANG(c_copy, step_0_result.permutation);
    }

    class STEP_2_RESULT {
        float fi;
        int[][] permutation;
        float[][] C;

        STEP_2_RESULT(float fi, int[][] permutation, float[][] c) {
            this.fi = fi;
            this.permutation = permutation;
            C = c;
        }
    }

    private STEP_2_RESULT step2(STEP_1_RESULT step_1_result, float u) {
        float[][] C = getCopy(step_1_result.C$);
        C[step_1_result.k][step_1_result.l] = Float.POSITIVE_INFINITY;
        int[][] permutation = new HungarianAlgorithm().computeAssignments(C);
        float C_d = u;
        for (int i = 0; i < permutation.length; i++) {
            C_d += step_1_result.C$[permutation[i][0]][permutation[i][1]];
        }
        return new STEP_2_RESULT(C_d, permutation, C);
    }

    class STEP_3_RESULT {
        float[][] C;

        STEP_3_RESULT(float[][] c) {
            C = c;
        }
    }

    STEP_3_RESULT step3(STEP_1_RESULT step_1_result, STEP_2_RESULT step_2_result) {
        float[][] C = getCopy(step_1_result.C$);
        C[step_1_result.k][step_1_result.l] = step_2_result.C[step_1_result.l][step_1_result.k];
        return new STEP_3_RESULT(C);
    }

    class STEP_4_RESULT {
        int[][] permutation;
        float fi;

        STEP_4_RESULT(int[][] permutation, float fi) {
            this.permutation = permutation;
            this.fi = fi;
        }
    }

    STEP_4_RESULT step4(STEP_1_RESULT s1, STEP_3_RESULT s3, int[][] permutation, float u) {
        int n_ = 0;
        int k = s1.k;
        while (true) {
            int ind = getIndexPermutation(k, permutation);
            k = permutation[ind][1];
            n_ += s1.Y[permutation[ind][0]][k] == Float.POSITIVE_INFINITY ? 1 : 0;
            if (k == s1.k) break;
        }

        int[][] perm = new int[0][0];
        if (n_ == s1.n - 1) {


        } else if (n_ == s1.n - 2) {
            float[][] C = getCopy(s3.C);
            C[s1.l][s1.k] = Float.POSITIVE_INFINITY;
            for (int i = 0; i < C.length; i++) {
                if (i != s1.l) {
                    C[s1.k][i] = Float.POSITIVE_INFINITY;
                }
                if (i != s1.k) {
                    C[i][s1.l] = Float.POSITIVE_INFINITY;
                }
            }
            perm = new HungarianAlgorithm().computeAssignments(C);


        } else if (n_ < s1.n - 2) {

        }
        float C_d = u + s1.C$[s1.l][s1.k];
        return new STEP_4_RESULT(perm, C_d);
    }

    class STEP_5_RESULT {
        float C[][];
        int[][] permutation;
        boolean endAlg = false;


        STEP_5_RESULT(boolean endAlg) {
            this.endAlg = endAlg;
        }

        STEP_5_RESULT(float[][] c, int[][] permutation) {
            C = c;
            this.permutation = permutation;
        }
    }

    private STEP_5_RESULT step5(STEP_0_RESULT s0, STEP_1_RESULT s1, STEP_2_RESULT s2, STEP_4_RESULT s4) {
        m++;
        if (s0.castSum == Float.POSITIVE_INFINITY) {
            return new STEP_5_RESULT(true);
        }
        if (isCircle(s0.permutation)) {
            return new STEP_5_RESULT(true);
        }
        if (s4.fi < s2.fi) {
            float[][] C = getCopy(s0.matrix);
            for (int i = 0; i < C.length; i++) {
                C[s1.l][s1.k] = Float.POSITIVE_INFINITY;
                if (i != s1.l) {
                    C[s1.k][i] = Float.POSITIVE_INFINITY;
                }
                if (i != s1.k) {
                    C[i][s1.l] = Float.POSITIVE_INFINITY;
                }

            }


            return new STEP_5_RESULT(C, s4.permutation);
        }

        return null;

    }

    boolean isCircle(int[][] permutation) {
        int count = 1;
        int startPoint = permutation[0][0];
        int endPoint = permutation[0][1];
        while (endPoint != startPoint) {
            for (int i = 1; i < permutation.length; i++) {
                if (permutation[i][0] == endPoint) {
                    endPoint = permutation[i][1];
                    count++;
                }
            }
        }

        return count == permutation.length;
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

    int getIndexPermutation(int first, int[][] permutation) {
        for (int i = 0; i < permutation.length; i++) {
            if (permutation[i][0] == first) {
                return i;
            }
        }
        return -1;
    }

    int[] getCircles(int[][] permutation) {
        int flag[] = new int[permutation.length];
        int count = 0;

        while (true) {
            int c = -1;
            for (int i = 0; i < permutation.length; i++) {
                if (flag[i] == 0) {
                    c = i;
                    count++;
                    flag[c] = count;
                    break;
                }
            }
            if (c == -1) {
                break;
            }
            int startPoint = permutation[c][0];
            int endPoint = permutation[c][1];
            while (endPoint != startPoint) {
                for (int i = 0; i < permutation.length; i++) {
                    if (permutation[i][0] == endPoint) {
                        endPoint = permutation[i][1];
                        flag[i] = count;
                    }
                }
            }

        }
        return flag;
    }

    STEP_1_RESULT LANG(float[][] C, int[][] permutation) {
        float[][] y = new float[C.length][C.length];
        int r = 0, k = 0;
        float M = 0;
        int k_M = -1, l_M = -1;
        List<curve> R = new ArrayList<curve>();

        while (k < C.length) {
            int index = getIndexPermutation(k, permutation);
            int l = permutation[index][1];
            float c_k = minRow(k, l, C);
            float c_l = minCol(l, k, C);
            y[k][l] = c_k + c_l - C[k][l];
            if (y[k][l] == 0) {
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
            if (y[k][l] >= M && y[k][l] != Float.POSITIVE_INFINITY) {
                M = y[k][l];
                k_M = k;
                l_M = l;
            }
            k++;
        }
        // mark circles
        int circles[] = getCircles(permutation);
        // calc count of circles
        int K = 0;
        for (int i = 0; i < circles.length; i++) {
            K = Math.max(K, circles[i]);
        }

        if (r > C.length - K) {
            M = Float.POSITIVE_INFINITY;
        }

        List<curve> Z_w = new ArrayList<curve>();

        int[] tmp = new int[K + 1];
        for (int i = 0; i < circles.length; i++) {
            tmp[circles[i]]++;
        }
        int K_min = 0;
        for (int i = 1; i < circles.length; i++) {
            if (K_min < tmp[circles[i]]) {
                K_min = tmp[circles[i]];
            }
        }

        if (K_min <= r) {
            int w = 1;
            while (w <= K) {
                // Z_w = Z_w.remove(R(G));

                if (Z_w.isEmpty()) {
                    M = Float.POSITIVE_INFINITY;
                } else {
                    w++;
                }
            }
        }

        if (M != Float.POSITIVE_INFINITY) {

        }


        return new STEP_1_RESULT(C, y, M, K_min, k_M, l_M);
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
        for (int q = 0; q < matrix.length; q++) {
            if (q == ex) continue;
            result = Math.min(matrix[i][q], result);
        }
        return result;
    }

    float minCol(int j, int ex, float[][] matrix) {
        float result = Float.POSITIVE_INFINITY;
        for (int q = 0; q < matrix.length; q++) {
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
