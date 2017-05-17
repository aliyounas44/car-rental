package com.epam.bench.carrental.client.userinterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.TableModel;

public class CommonUI {

    public static void showMessage(String message) {
	JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.PLAIN_MESSAGE);
    }

    public static JTextField createTextField() {
	return new JTextField(50);
    }
    
    public static JLabel createLabel(String text) {
	return new JLabel(text);
    }

    public static JPanel createPanel(Component... components) {
	JPanel panel = new JPanel();

	for (Component component : components) {
	    panel.add(component, BorderLayout.CENTER);	
	}
	return panel;
    }

    public static JButton createJButton(String buttonText) {
	return new JButton(buttonText);
    }

    public static JPanel createCustomPanel(LayoutManager layout) {
	JPanel panel = new JPanel();
	panel.setLayout(layout);
	return panel;
    }

    public static JScrollPane createScrollPane(JComponent table) {
	return new JScrollPane(table);
    }

    public static JTable createTable(TableModel tableMobel) {
	return new JTable(tableMobel);
    }
    
    public static JTextArea createTextArea() {
	return new JTextArea();
    }

    public static JToolBar createToolbar(Component... components) {
	JToolBar toolbar = new JToolBar();
	toolbar.setRollover(true);

	for (Component component : components) {
	    toolbar.add(component);
	    toolbar.addSeparator();
	}

	return toolbar;
    }

    public static JTabbedPane createTabbedPane(int orientation) {
	return new JTabbedPane(orientation);	
    }

}
