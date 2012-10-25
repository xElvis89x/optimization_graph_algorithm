package com.elvis.optimizationtask.ui.control;

import com.elvis.GUIShell;
import com.elvis.graph.generator.GraphGenerator;
import com.elvis.graph.visualizer.GraphVisualizator;
import com.elvis.optimizationtask.export.ExcelExporter;
import com.elvis.optimizationtask.ui.model.FileListModel;
import com.elvis.optimizationtask.ui.model.MaxCutTableModel;
import com.elvis.optimizationtask.ui.view.MaxCutTableCellRenderer;
import com.elvis.optimizationtask.ui.view.MaxCutView;
import org.springframework.beans.factory.InitializingBean;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by User: el
 * Date: 03.10.12
 * Time: 9:31
 * To change this template use File | Settings | File Templates.
 */
public class MaxCutController implements InitializingBean, GUIShell {
    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;
    private MaxCutTableModel tableModel;
    private FileListModel listModel;
    private File[] filesForCalc;

    private MaxCutView maxCutView;
    private JFrame frame;
    private JMenuBar menuBar;
    private JFileChooser fileChooser;
    private AlgorithmCalculation algorithmCalculation;


    public MaxCutController() {
    }

    public void setMaxCutView(MaxCutView maxCutView) {
        this.maxCutView = maxCutView;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public void setFileChooser(JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public void setTableModel(MaxCutTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void setAlgorithmCalculation(AlgorithmCalculation algorithmCalculation) {
        this.algorithmCalculation = algorithmCalculation;
    }

    public void setFilesForCalc(File[] filesForCalc) {
        this.filesForCalc = filesForCalc;
        for (File file : filesForCalc) {
            listModel.addFile(file);
        }
    }

    public void init() {
        initFrame();
        maxCutView.getResultsTable().setModel(tableModel);
        MaxCutTableCellRenderer cellRenderer = new MaxCutTableCellRenderer();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            maxCutView.getResultsTable().getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        listModel = new FileListModel();
        maxCutView.getFileList().setModel(listModel);

        maxCutView.getSelectFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
                setFilesForCalc(chooseFileWithGraph());
            }
        });
        maxCutView.getStartCalcButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmCalculation.setFilesForCalc(filesForCalc);
                new Thread(algorithmCalculation).start();
            }
        });

        maxCutView.getExcelExport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = getFileForSaveXLS();
                if (file == null) return;
                OutputStream fileStream = null;
                try {
                    fileStream = new FileOutputStream(file);
                    new ExcelExporter(tableModel.getMaxCuts()).writeData(fileStream);
                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(maxCutView.getContentPane(),
                            "Can not write to file.\n No access to file!!!");
                    e1.printStackTrace();
                } finally {
                    try {
                        assert fileStream != null;
                        fileStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        maxCutView.getVisualizeGraphButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = maxCutView.getFileList().getSelectedIndex();
                if (index >= 0 && index < listModel.getSize()) {
                    new GraphVisualizator(Utils.getGraph(listModel.get(index))).start();
                }
            }
        });


        JMenu menuFile = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuFile.add(exitItem);


        JMenu menuTools = new JMenu("Tools");
        JMenuItem item = new JMenuItem("Graph Generator");
        item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GraphGenerator().start();
            }
        });
        menuTools.add(item);

        menuBar.add(menuFile);
        menuBar.add(menuTools);
    }


    private void initFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(maxCutView.getContentPane());
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(new Rectangle((int) (screenSize.getWidth() / 2 - WIDTH / 2),
                (int) (screenSize.getHeight() / 2 - HEIGHT / 2), WIDTH, HEIGHT));
    }

    public void setVisible(boolean visible) {
        frame.pack();
        frame.setVisible(visible);
    }

    private FileNameExtensionFilter xlsFilter = new FileNameExtensionFilter("Excel File", "xls");
    private FileNameExtensionFilter graphFilter = new FileNameExtensionFilter("Graph file", "graph");

    File getFileForSaveXLS() {
        initFileChooser(xlsFilter, graphFilter, false);
        fileChooser.showSaveDialog(frame);
        File f = fileChooser.getSelectedFile();
        if (f == null) return null;
        if (!f.getName().endsWith(".xls"))
            f = new File(f.getPath() + ".xls");
        return f;
    }

    private File[] chooseFileWithGraph() {
        initFileChooser(graphFilter, xlsFilter, true);
        fileChooser.showOpenDialog(frame);
        return fileChooser.getSelectedFiles();
    }

    private void initFileChooser(FileNameExtensionFilter addFilter, FileNameExtensionFilter removeFilter, Boolean multi) {
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setMultiSelectionEnabled(multi);
        fileChooser.removeChoosableFileFilter(removeFilter);
        fileChooser.addChoosableFileFilter(addFilter);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
