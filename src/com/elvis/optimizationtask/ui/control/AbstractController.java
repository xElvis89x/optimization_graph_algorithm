package com.elvis.optimizationtask.ui.control;

import com.elvis.optimizationtask.ui.model.FileListModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Created by User: el
 * Date: 01.11.12
 * Time: 15:46
 */
public abstract class AbstractController {
    protected FileListModel listModel;
    protected File[] filesForCalc;
    protected JFileChooser fileChooser;
    protected FileNameExtensionFilter xlsFilter = new FileNameExtensionFilter("Excel File", "xls");
    protected FileNameExtensionFilter graphFilter = new FileNameExtensionFilter("Graph file", "graph");
    protected AlgorithmCalculation algorithmCalculation;

    protected void initFileChooser(FileNameExtensionFilter addFilter, Boolean multi) {
        fileChooser.cancelSelection();
        fileChooser.setSelectedFiles(null);
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setMultiSelectionEnabled(multi);
        fileChooser.resetChoosableFileFilters();
        fileChooser.addChoosableFileFilter(addFilter);
    }

    public void setFilesForCalc(File[] filesForCalc) {
        this.filesForCalc = filesForCalc;
        for (File file : filesForCalc) {
            listModel.addFile(file);
        }
    }

    protected abstract Component getComponent();

    File getFileForSaveXLS() {
        initFileChooser(xlsFilter, false);
        fileChooser.showSaveDialog(getComponent());
        File f = fileChooser.getSelectedFile();
        if (f == null) return null;
        if (!f.getName().endsWith(".xls"))
            f = new File(f.getPath() + ".xls");
        return f;
    }

    protected File[] chooseFileWithGraph() {
        initFileChooser(graphFilter, true);
        fileChooser.showOpenDialog(getComponent());
        return fileChooser.getSelectedFiles();
    }

    public void setFileChooser(JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public void setAlgorithmCalculation(AlgorithmCalculation algorithmCalculation) {
        this.algorithmCalculation = algorithmCalculation;
    }
}
