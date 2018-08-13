package com.util;

import com.inspectionDiff.XmlDiff;
import com.intellij.ui.components.JBLabel;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileChecker {
    public static void setInfo(JTextField file, JBLabel label) {
        if (file.getText().isEmpty()) {
            setInfo((String) null, label);
        } else {
            setInfo(getFolderInfo(Paths.get(file.getText())), label);
        }
    }

    private static void setInfo(String info, JBLabel label) {
        if (info != null) {
            label.setText(info);
            if (!label.isVisible()) {
                label.setVisible(true);
            }
        } else if (label.isVisible()) {
            label.setVisible(false);
        }
    }

    private static String getFolderInfo(Path folder) {
        String info = null;
        if (checkFile(folder)) {
            try {
                long count = Files.list(folder).count() - 1;
                info = (count > 1) ? count + " .xml files found" : "one .xml file with " +
                        XmlDiff.getWarningsCount(Files.list(folder).filter(p -> p.getFileName().toString().toLowerCase().endsWith(".xml") && !p.getFileName().toString().equals(".descriptions.xml")).findAny().get()) + " warnings found";
            } catch (Exception e) {
                info = null;
            }
        }
        return info;
    }

    public static boolean checkFile(Path file) {
        boolean valid;
        try {
            valid = file != null && Files.exists(file) && Files.isDirectory(file) && Files.list(file).anyMatch(p -> p.getFileName().toString().equals(".descriptions.xml"));
        } catch (IOException e) {
            valid = false;
        }
        return valid;
    }
}