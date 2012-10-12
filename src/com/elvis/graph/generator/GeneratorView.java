package com.elvis.graph.generator;

import javax.swing.*;

/**
 * Created by User: el
 * Date: 12.10.12
 * Time: 0:20
 */
public class GeneratorView {
    private JButton generateButton;
    private JSpinner spinnerSize;
    private JPanel contentPane;
    private JSpinner spinnerCount;

    public JButton getGenerateButton() {
        return generateButton;
    }

    public void setGenerateButton(JButton generateButton) {
        this.generateButton = generateButton;
    }

    public JSpinner getSpinnerSize() {
        return spinnerSize;
    }

    public void setSpinnerSize(JSpinner spinnerSize) {
        this.spinnerSize = spinnerSize;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

    public JSpinner getSpinnerCount() {
        return spinnerCount;
    }

    public void setSpinnerCount(JSpinner spinnerCount) {
        this.spinnerCount = spinnerCount;
    }
}
