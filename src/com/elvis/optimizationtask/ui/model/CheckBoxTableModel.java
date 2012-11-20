package com.elvis.optimizationtask.ui.model;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User: el
 * Date: 17.11.12
 * Time: 13:33
 */
public class CheckBoxTableModel extends DefaultTableModel {

    HashMap<String, Boolean> checkBoxMap = new HashMap<String, java.lang.Boolean>();

    public List<String> getAlgorithmsList() {
        List<String> maxCutTypes = new ArrayList<String>();
        for (Map.Entry<String, Boolean> stringBooleanEntry : checkBoxMap.entrySet()) {
            if (stringBooleanEntry.getValue()) {
                maxCutTypes.add(stringBooleanEntry.getKey());
            }
        }
        return maxCutTypes;
    }

    public void SelectAll() {
        for (Map.Entry<String, Boolean> stringBooleanEntry : checkBoxMap.entrySet()) {
            stringBooleanEntry.setValue(true);
        }
    }

    public void Inverse() {
        for (Map.Entry<String, Boolean> stringBooleanEntry : checkBoxMap.entrySet()) {
            stringBooleanEntry.setValue(!stringBooleanEntry.getValue());
        }
    }

    public void addMaxCutAlgorithm(String alg) {
        checkBoxMap.put(alg, false);
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Boolean.class : super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 0 || super.isCellEditable(row, column);
    }

    String[] columnName = {"Check", "Algorithm Name"};

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return checkBoxMap != null ? checkBoxMap.size() : 0;
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (column == 0) {
            return checkBoxMap.get(checkBoxMap.keySet().toArray()[row]);
        } else {
            return checkBoxMap.keySet().toArray()[row];
        }
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (column == 0) {
            checkBoxMap.put(checkBoxMap.keySet().toArray()[row].toString(), (Boolean) aValue);
            fireTableCellUpdated(row, column);
        }

    }
}

