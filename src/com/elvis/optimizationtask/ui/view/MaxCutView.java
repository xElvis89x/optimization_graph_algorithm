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
    private JButton excelExport;
    private JButton visualizeGraphButton;
    private JList fileList;
    private JCheckBox brutForceCheckBox;
    private JCheckBox geneticAlgorithmCheckBox;
    private JCheckBox lorenaCheckBox;
    private JCheckBox randomCheckBox;
    private JCheckBox GESCheckBox;

    public JCheckBox getBrutForceCheckBox() {
        return brutForceCheckBox;
    }

    public JCheckBox getGeneticAlgorithmCheckBox() {
        return geneticAlgorithmCheckBox;
    }

    public JCheckBox getLorenaCheckBox() {
        return lorenaCheckBox;
    }

    public JCheckBox getRandomCheckBox() {
        return randomCheckBox;
    }

    public JCheckBox getGESCheckBox() {
        return GESCheckBox;
    }

    public JButton getVisualizeGraphButton() {
        return visualizeGraphButton;
    }

    public JList getFileList() {
        return fileList;
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
