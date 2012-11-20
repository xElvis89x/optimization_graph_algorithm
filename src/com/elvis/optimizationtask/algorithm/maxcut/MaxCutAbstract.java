package com.elvis.optimizationtask.algorithm.maxcut;

import java.util.Random;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 15:22
 */
public abstract class MaxCutAbstract implements MaxCut {
    protected static Random rand = new Random(System.nanoTime());

    protected MaxCutAbstract() {
    }

    protected static boolean[] getRandomSolution(int n) {
        boolean[] result = new boolean[n];
        for (int i = 0; i < result.length; i++) {
            result[i] = rand.nextBoolean();
        }
        return result;
    }

    protected boolean[] res_mask;

    protected long timeExec;

    public long getTimeExec() {
        return timeExec;
    }

    @Override
    public boolean[] getResult() {
        return res_mask;
    }

    public abstract void calc();

    @Override
    public void solve() {
        long startTime = System.nanoTime();
        calc();
        timeExec = System.nanoTime() - startTime;
    }
}
