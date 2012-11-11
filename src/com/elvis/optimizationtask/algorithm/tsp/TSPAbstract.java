package com.elvis.optimizationtask.algorithm.tsp;

/**
 * Created by User: el
 * Date: 30.10.12
 * Time: 15:28
 */
public abstract class TSPAbstract implements TSP {

    public abstract void calc();

    protected long timeExec;
    protected int[] result;    // result[i]  - i town index

    public long getTimeExec() {
        return timeExec;
    }

    public int[] getResult() {
        return result;
    }

    @Override
    public void solve() {
        long startTime = System.nanoTime();
        calc();
        timeExec = System.nanoTime() - startTime;
    }
}
