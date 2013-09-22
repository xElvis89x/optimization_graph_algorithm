package com.elvis.optimizationtask.algorithm.maxcut.weight;

import com.elvis.model.SimpleWeightFloatGraph;

/**
 * Created by User: el
 * Date: 17.12.12
 * Time: 0:46
 */
public class MaxCutWeightKaporisKirousisStavropoulos extends MaxCutWeightAbstract {

    public MaxCutWeightKaporisKirousisStavropoulos(SimpleWeightFloatGraph floatGraph) {
        super(floatGraph);
    }

    public MaxCutWeightKaporisKirousisStavropoulos() {
    }

    @Override
    public void calc() {
        short[] result = new short[graph.size()];
        result[0] = -1;
        for (int p = 0; p < graph.size() - 1; p++) {

            int v = -1;
            float d = Float.POSITIVE_INFINITY;

            for (int i = 0; i < graph.size(); i++) {
                if (result[i] == 0) {
                    float tmp_d = 0;
                    boolean f = false;
                    for (int j = 0; j < graph.size(); j++) {
                        if (result[j] != 0 && j != i && graph.getCell(i, j) != 0) {
                            tmp_d += result[j] * graph.getCell(i, j);
                            f = true;
                        }
                    }
                    if (Math.abs(tmp_d) < Math.abs(d) && f) {
                        v = i;
                        d = tmp_d;
                    }
                }
            }
            if (v != -1 && v < result.length) {
                result[v] = (short) (d > 0 ? -1 : 1);
            }

        }
        res_mask = new boolean[graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            res_mask[i] = result[i] > 0;
        }
    }

    @Override
    public String getHumanID() {
        return "Max Cut Weight Kaporis Kirousis Stavropoulos";
    }

    @Override
    public String getID() {
        return "MCWKKS";
    }
}
