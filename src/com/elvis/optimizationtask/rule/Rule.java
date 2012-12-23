package com.elvis.optimizationtask.rule;

import com.elvis.model.Graph;

/**
 * Created by User: el
 * Date: 08.11.12
 * Time: 13:58
 */
public interface Rule {
    public boolean check(Graph graph);

    public String id();
}
