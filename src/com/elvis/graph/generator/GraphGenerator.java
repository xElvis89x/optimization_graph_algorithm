package com.elvis.graph.generator;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User: el
 * Date: 12.10.12
 * Time: 0:16
 */
public class GraphGenerator {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new GraphGenerator().start();
    }

    public void start() {
        JFrame frame = new JFrame("GraphGenerator");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        GeneratorView view = new GeneratorView();
        new GeneratorController(view).init();
        frame.setContentPane(view.getContentPane());
        frame.setPreferredSize(new Dimension(300, 100));
        frame.pack();
        frame.setVisible(true);
    }
}
