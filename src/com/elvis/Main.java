package com.elvis;

import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        GUIShell shell = ((GUIShell) context.getBean("main_controller"));
        shell.setVisible(true);
    }


}
