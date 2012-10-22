package com.elvis.graph.generator;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User: el
 * Date: 12.10.12
 * Time: 0:16
 */
public class GraphGenerator {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 100;

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
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(new Rectangle((int) (screenSize.getWidth() / 2 - WIDTH / 2),
                (int) (screenSize.getHeight() / 2 - HEIGHT / 2), WIDTH, HEIGHT));
        frame.pack();
        frame.setVisible(true);
    }
}
