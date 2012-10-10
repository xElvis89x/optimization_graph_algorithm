package com.elvis.optimizationtask;

import com.elvis.optimizationtask.ui.control.MaxCutController;
import com.elvis.optimizationtask.ui.view.MaxCutView;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by User: el
 * Date: 02.10.12
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new MaxCutController(new MaxCutView()).start();
    }


}
