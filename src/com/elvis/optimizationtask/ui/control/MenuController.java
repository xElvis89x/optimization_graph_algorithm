package com.elvis.optimizationtask.ui.control;

import com.elvis.graph.converter.GraphMLConverter;
import com.elvis.graph.generator.GraphGenerator;
import com.elvis.graph.generator.planar.PlanarGraphGenerator;
import com.elvis.graph.generator.planar.PlanarGraphGeneratorController;
import org.springframework.beans.factory.InitializingBean;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User: el
 * Date: 25.10.12
 * Time: 8:53
 */
public class MenuController implements InitializingBean {

    private JMenuBar menuBar;
    private List<JMenuItem> menuItems = new ArrayList<JMenuItem>();

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    private void init() {
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
        item = new JMenuItem("Planar Graph Generator");
        item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlanarGraphGenerator().start();
            }
        });
        menuTools.add(item);
        item = new JMenuItem("yEd Graph Convertor");
        item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GraphMLConverter().start();
            }
        });
        menuTools.add(item);


        menuBar.add(menuFile);
        menuBar.add(menuTools);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
