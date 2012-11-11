package com.elvis.optimizationtask.ui.model;

import com.elvis.model.SimpleWeightGraph;
import com.elvis.optimizationtask.algorithm.tsp.TSP;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by User: el
 * Date: 02.11.12
 * Time: 1:34
 */
public class TSPTableModel extends AbstractTableModel {

    static String[] columnName = {"Algorithm name", "Time execution", "result", "mask", "File Name"};

    List<TSP> tspList = new ArrayList<TSP>();
    Map<TSP, Color> colorMap = new HashMap<TSP, Color>();


    public Color getColor(int row) {
        return colorMap.get(tspList.get(row));
    }

    public boolean add(TSP maxCut, Color color) {
        boolean res = tspList.add(maxCut);
        colorMap.put(maxCut, color);
        fireTableDataChanged();
        return res;
    }

    public boolean remove(Object o) {
        boolean res = tspList.remove(o);
        fireTableDataChanged();
        return res;
    }

    public void clear() {
        tspList.clear();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        if (tspList == null) return 0;
        return tspList.size();
    }

    @Override
    public int getColumnCount() {
        return columnName.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }


    @Override
    public Object getValueAt(int row, int column) {
        Object result = null;
        switch (column) {
            case 0:
                result = tspList.get(row).getHumanID();
                break;
            case 1:
                long nanosecond = tspList.get(row).getTimeExec();
                long sec = TimeUnit.NANOSECONDS.toSeconds(nanosecond);
                nanosecond -= TimeUnit.SECONDS.toNanos(sec);

                long mls = TimeUnit.NANOSECONDS.toMillis(nanosecond);
                nanosecond -= TimeUnit.MILLISECONDS.toNanos(mls);

                long mcs = TimeUnit.NANOSECONDS.toMicros(nanosecond);
                nanosecond -= TimeUnit.MICROSECONDS.toNanos(mcs);
                result = sec + "s " + mls + "mls " + mcs + "mcs ";
                break;
            case 2:
                result = tspList.get(row).getValue();
                break;
            case 3:
                result = Arrays.toString(tspList.get(row).getResult());
                break;
            case 4:
                result = ((SimpleWeightGraph) tspList.get(row).getGraph()).getFile().getName();

        }
        return result;
    }

}
