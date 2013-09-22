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
    List<String> algList = new ArrayList<String>();
    HashMap<String, Boolean> checkBoxMap = new HashMap<String, Boolean>();
    HashMap<String, Integer> repeatCountMap = new HashMap<String, Integer>();


    public List<String> getAlgorithmsList() {
        List<String> maxCutTypes = new ArrayList<String>();
        for (String algName : algList) {
            if (checkBoxMap.get(algName)) {
                maxCutTypes.add(algName);
            }
        }
        return maxCutTypes;
    }

    public void SelectNone() {
        for (Map.Entry<String, Boolean> stringBooleanEntry : checkBoxMap.entrySet()) {
            stringBooleanEntry.setValue(false);
        }
        fireTableDataChanged();
    }

    public void SelectAll() {
        for (Map.Entry<String, Boolean> stringBooleanEntry : checkBoxMap.entrySet()) {
            stringBooleanEntry.setValue(true);
        }
        fireTableDataChanged();
    }

    public void Inverse() {
        for (Map.Entry<String, Boolean> stringBooleanEntry : checkBoxMap.entrySet()) {
            stringBooleanEntry.setValue(!stringBooleanEntry.getValue());
        }
        fireTableDataChanged();
    }

    public void addMaxCutAlgorithm(String alg) {
        algList.add(alg);
        checkBoxMap.put(alg, false);
        repeatCountMap.put(alg, 1);
        fireTableDataChanged();
    }

    Class<?>[] columnClass = {Boolean.class, String.class, Integer.class};

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 0 || column == 2 || super.isCellEditable(row, column);
    }

    String[] columnName = {"Check", "Algorithm Name", "Repeat Count"};

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }

    @Override
    public int getColumnCount() {
        return columnName.length;
    }

    @Override
    public int getRowCount() {
        return checkBoxMap != null ? checkBoxMap.size() : 0;
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (column == 0) {
            return checkBoxMap.get(algList.get(row));
        } else if (column == 1) {
            return algList.get(row);
        } else {
            return repeatCountMap.get(algList.get(row));
        }
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (column == 0) {
            checkBoxMap.put(algList.get(row), (Boolean) aValue);
            fireTableCellUpdated(row, column);
        }
        if (column == 2) {
            repeatCountMap.put(algList.get(row), (Integer) aValue);
            fireTableCellUpdated(row, column);
        }
    }
}

