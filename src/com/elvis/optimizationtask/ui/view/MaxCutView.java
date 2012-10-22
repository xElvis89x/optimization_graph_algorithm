package com.elvis.optimizationtask.ui.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
    private JLabel progressText;
    private JButton selectAllButton;
    private JButton inverseButton;

    private List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>() {
        {
            add(brutForceCheckBox);
            add(geneticAlgorithmCheckBox);
            add(lorenaCheckBox);
            add(randomCheckBox);
            add(GESCheckBox);
        }
    };

    public MaxCutView() {
        selectAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JCheckBox jCheckBox : checkBoxList) {
                    jCheckBox.setSelected(true);
                }
            }
        });
        inverseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JCheckBox jCheckBox : checkBoxList) {
                    jCheckBox.setSelected(!jCheckBox.isSelected());
                }
            }
        });
    }

    public JLabel getProgressText() {
        return progressText;
    }

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
