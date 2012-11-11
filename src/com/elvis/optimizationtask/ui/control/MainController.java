package com.elvis.optimizationtask.ui.control;

import com.elvis.GUIShell;
import com.elvis.optimizationtask.ui.view.ContentProvider;
import com.elvis.optimizationtask.ui.view.MainView;
import org.springframework.beans.factory.InitializingBean;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by User: el
 * Date: 01.11.12
 * Time: 20:16
 */
public class MainController implements GUIShell, InitializingBean {
    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;
    private JFrame frame;
    private MainView view;

    private List<ContentProvider> tabPaneList;


    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setTabPaneList(List<ContentProvider> tabPaneList) {
        this.tabPaneList = tabPaneList;
    }

    public void setView(MainView view) {
        this.view = view;
    }

    @Override
    public void setVisible(boolean visible) {
        frame.pack();
        frame.setVisible(visible);
    }

    private void initFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(view.getContentPane());
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(new Rectangle((int) (screenSize.getWidth() / 2 - WIDTH / 2),
                (int) (screenSize.getHeight() / 2 - HEIGHT / 2), WIDTH, HEIGHT));
    }

    private void init() {
        view.getTabbedPane().removeAll();
        for (ContentProvider contentProvider : tabPaneList) {
            view.getTabbedPane().addTab(contentProvider.getTitle(), contentProvider.getContentPane());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initFrame();
        init();
    }
}
