package com.elvis.optimizationtask.ui.model;

import javax.swing.*;
import java.io.File;

/**
 * Created by User: el
 * Date: 12.10.12
 * Time: 10:47
 */
public class FileListModel extends DefaultListModel<File> {
    public void addFile(File file) {
        addElement(file);
    }

}
