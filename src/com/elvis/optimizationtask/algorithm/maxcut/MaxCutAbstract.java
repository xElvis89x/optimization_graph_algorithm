package com.elvis.optimizationtask.algorithm.maxcut;

/**
 * Created by User: el
 * Date: 09.10.12
 * Time: 15:22
 */
public abstract class MaxCutAbstract implements MaxCut {
    public abstract void calc();

    long timeExec;

    public long getTimeExec() {
        return timeExec;
    }

    @Override
    public void solve() {
        long startTime = System.nanoTime();
        calc();
        timeExec = System.nanoTime() - startTime;
    }
}
