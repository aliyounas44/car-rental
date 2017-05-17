package com.epam.bench.carrental.client.userinterface;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.bench.carrental.client.userinterface.controllers.RentalClassController;
import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;

@org.springframework.stereotype.Component
public class RentalClassUI {
    @Autowired
    CustomTableModel<RentalClassDTO> rentalClassDataModel;
    @Autowired
    RentalClassController rentalClassController;

    private JButton jButtonAddRentalClass = null;
    private JButton jButtonRefreshRentalClass = null;
    private JToolBar rentalClassToolBar = null;
    private JTable rentalClassTable = null;
    private JScrollPane rentalClassTableScrollPane = null;
    private JPanel rentalClassPanel = null;

    @PostConstruct
    private void initializeControls() {
	jButtonAddRentalClass = CommonUI.createJButton("Add Rental Class");
	jButtonRefreshRentalClass = CommonUI.createJButton("Refresh Rental Class");
	rentalClassToolBar = CommonUI.createToolbar(jButtonAddRentalClass, jButtonRefreshRentalClass);
	rentalClassTable = CommonUI.createTable(rentalClassDataModel);
	rentalClassTableScrollPane = CommonUI.createScrollPane(rentalClassTable);
	rentalClassPanel = CommonUI.createCustomPanel(new BorderLayout());
    }

    public void addListeners() {
	jButtonAddRentalClass.addActionListener(rentalClassController::addRentalClass);
	jButtonRefreshRentalClass.addActionListener(rentalClassController::refreshRentalClasses);
    }

    public Component getRentalClassPanel() {
	rentalClassPanel.add(rentalClassTableScrollPane, BorderLayout.CENTER);
	rentalClassPanel.add(rentalClassToolBar, BorderLayout.NORTH);
	return rentalClassPanel;
    }
}
