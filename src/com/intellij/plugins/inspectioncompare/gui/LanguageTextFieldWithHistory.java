package com.intellij.plugins.inspectioncompare.gui;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.LanguageTextField;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class LanguageTextFieldWithHistory extends LanguageTextField {
    private final String myProperty;
    private final int myHistorySize;
    private String [] myHistory;
    private int current = 0;
    private final JBPanel wrapper;

    public LanguageTextFieldWithHistory(int historySize, String property, Project project, Language language, JBPanel wrapper) {
        super(language, project, "");
        this.wrapper = wrapper;
        myHistorySize = historySize;
        myProperty = property;
        setOneLineMode(true);
        load();
        addKeyStrokes();
    }

    public void setPreviousTextFromHistory() {
        if (myHistory != null) {
            if (!getText().equals(myHistory[current])) {
                myHistory[myHistory.length - 1] = getText();
                setText(myHistory[myHistory.length - 2]);
                current = myHistory.length - 2;
            } else {
                current = (current - 1 + myHistory.length) % myHistory.length;
                setText(myHistory[current]);
            }
        }
    }

    public void setNextTextFromHistory() {
        if (myHistory != null) {
            if (!getText().equals(myHistory[current])) {
                myHistory[myHistory.length - 1] = getText();
                setText(myHistory[myHistory.length - 2]);
                current = myHistory.length - 2;
            } else {
                current = (current + 1) % myHistory.length;
                setText(myHistory[current]);
            }
        }
    }

    private void load() {
        String value = PropertiesComponent.getInstance().getValue(myProperty);
        if (value != null) {
            myHistory = (value + "\n" + " ").split("\n");
            myHistory[myHistory.length - 1] = "";
            current = myHistory.length - 2;
        }
    }

    public void addTextAndSave() {
        if (!getText().isEmpty() && (myHistory == null || !getText().equals(myHistory[myHistory.length - 2]))) {
            String valueToStore;
            if (myHistory != null) {
                myHistory[myHistory.length - 1] = getText();
                if (myHistory.length > myHistorySize) {
                    valueToStore = StringUtil.join(Arrays.copyOfRange(myHistory, 1, myHistory.length), "\n");
                } else {
                    valueToStore = StringUtil.join(myHistory, "\n");
                }
            } else {
                valueToStore = getText();
            }
            PropertiesComponent.getInstance().setValue(myProperty, valueToStore);
        }
    }

    public JPanel getWrapper() {
        return wrapper;
    }
    public void addToWrapper() {
        wrapper.add(this);
    }

    void addKeyStrokes() {
        wrapper.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("UP"), "previous");
        wrapper.getActionMap().put("previous", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPreviousTextFromHistory();
            }
        });
        wrapper.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("DOWN"), "next");
        wrapper.getActionMap().put("next", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setNextTextFromHistory();
            }
        });
    }
}
