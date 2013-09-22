package com.elvis.graph.generator.planar;

import com.elvis.graph.generator.GeneratorController;
import com.elvis.graph.generator.GeneratorView;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: el
 * Date: 29.06.13
 * Time: 12:10
 * To change this template use File | Settings | File Templates.
 */
public class PlanarGraphGenerator {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;

    public void start() {
        JFrame frame = new JFrame("PlanarGraphGenerator");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        PlanarGeneratorView view = new PlanarGeneratorView();
        new PlanarGraphGeneratorController(view).init();
        frame.setContentPane(view.getContentPane());
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(new Rectangle((int) (screenSize.getWidth() / 2 - WIDTH / 2),
                (int) (screenSize.getHeight() / 2 - HEIGHT / 2), WIDTH, HEIGHT));
        frame.pack();
        frame.setVisible(true);
    }

}
