package com.elvis.optimizationtask.ui.view;

import javax.swing.*;

/**
 * Created by User: el
 * Date: 03.10.12
 * Time: 9:28
 * To change this template use File | Settings | File Templates.
 */
public class MaxCutView {
    private JTable table1;
    private JButton selectFileButton;
    private JPanel contentPane;
    private JButton startCalcButton;
    private JProgressBar progressBar;
    private JLabel fileNames;
    private JButton excelExport;
    private JButton visualizeGraphButton;

    public JButton getVisualizeGraphButton() {
        return visualizeGraphButton;
    }

    public JLabel getFileNames() {
        return fileNames;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JTable getTable1() {
        return table1;
    }

    public JButton getStartCalcButton() {
        return startCalcButton;
    }

    public JButton getSelectFileButton() {
        return selectFileButton;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JButton getExcelExport() {
        return excelExport;
    }
}
