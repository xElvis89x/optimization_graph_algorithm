package com.elvis.optimizationtask.ui.control;

import com.elvis.graph.generator.GraphGenerator;
import org.springframework.beans.factory.InitializingBean;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by User: el
 * Date: 25.10.12
 * Time: 8:53
 */
public class MenuController implements InitializingBean {

    private JMenuBar menuBar;

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

        menuBar.add(menuFile);
        menuBar.add(menuTools);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
