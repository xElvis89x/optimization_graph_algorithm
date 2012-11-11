package com.elvis.optimizationtask.ui.view;

import com.elvis.optimizationtask.ui.model.MaxCutTableModel;
import com.elvis.optimizationtask.ui.model.TSPTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by User: el
 * Date: 06.10.12
 * Time: 10:48
 */
public class MaxCutTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (table.getModel() instanceof MaxCutTableModel) {
            setBackground(((MaxCutTableModel) table.getModel()).getColor(row));
        } else if (table.getModel() instanceof TSPTableModel) {
            setBackground(((TSPTableModel) table.getModel()).getColor(row));

        }
        return c;
    }
}
