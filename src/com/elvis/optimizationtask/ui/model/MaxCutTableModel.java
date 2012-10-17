package com.elvis.optimizationtask.ui.model;

import com.elvis.optimizationtask.OutputUtils;
import com.elvis.optimizationtask.algorithm.maxcut.MaxCut;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by User: el
 * Date: 03.10.12
 * Time: 9:48
 */
public class MaxCutTableModel extends DefaultTableModel {

    static String[] columnName = {"Algorithm name", "Time execution", "result", "mask"};

    List<MaxCut> maxCuts = new ArrayList<MaxCut>();
    Map<MaxCut, Color> colorMap = new HashMap<MaxCut, Color>();

    public List<MaxCut> getMaxCuts() {
        return maxCuts;
    }

    public Color getColor(int row) {
        return colorMap.get(maxCuts.get(row));
    }

    public boolean add(MaxCut maxCut, Color color) {
        boolean res = maxCuts.add(maxCut);
        colorMap.put(maxCut, color);
        fireTableDataChanged();
        return res;
    }

    public boolean remove(Object o) {
        boolean res = maxCuts.remove(o);
        fireTableDataChanged();
        return res;
    }

    public void clear() {
        maxCuts.clear();
    }

    @Override
    public int getRowCount() {
        if (maxCuts == null) return 0;
        return maxCuts.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
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
                result = maxCuts.get(row).getHumanID();
                break;
            case 1:
                long nanosecond = maxCuts.get(row).getTimeExec();
                long sec = TimeUnit.NANOSECONDS.toSeconds(nanosecond);
                nanosecond -= TimeUnit.SECONDS.toNanos(sec);

                long mls = TimeUnit.NANOSECONDS.toMillis(nanosecond);
                nanosecond -= TimeUnit.MILLISECONDS.toNanos(mls);

                long mcs = TimeUnit.NANOSECONDS.toMicros(nanosecond);
                nanosecond -= TimeUnit.MICROSECONDS.toNanos(mcs);
                result = sec + "s " + mls + "mls " + mcs + "mcs ";
                break;
            case 2:
                result = maxCuts.get(row).getMaxCut();
                break;
            case 3:
                result = OutputUtils.getBoolMaskPresentation(maxCuts.get(row).getMask());
                break;

        }
        return result;
    }
}
