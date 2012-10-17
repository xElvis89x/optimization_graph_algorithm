package com.elvis.optimizationtask.algorithm.maxcut;

/**
 * Created by User: el
 * Date: 02.10.12
 * Time: 17:50
 *
 */
public interface MaxCut {
    public long getTimeExec();

    public float getMaxCut();

    public boolean[] getMask();

    public void solve();

    public String getHumanID();

    public String getID();

}
