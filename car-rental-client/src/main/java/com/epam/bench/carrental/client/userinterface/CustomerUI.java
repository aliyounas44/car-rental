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

import com.epam.bench.carrental.client.userinterface.controllers.CustomerController;
import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.commons.beans.CustomerDTO;

@org.springframework.stereotype.Component
public class CustomerUI {

    @Autowired
    CustomerController customerController;
    @Autowired
    CustomTableModel<CustomerDTO> customerDataModel;

    private JButton addCustomerButton = null;
    private JButton refreshCustomerButton = null;
    private JToolBar customerToolBar = null;
    private JTable customersTable = null;
    private JScrollPane customerTableScrollPane = null;
    private JPanel customerPanel = null;

    public void addListeners() {
	addCustomerButton.addActionListener(customerController::addCustomerButtonlistener);
	refreshCustomerButton.addActionListener(customerController::refreshCustomerButtonlistener);
    }

    @PostConstruct
    private void initializeControls() {
	addCustomerButton = CommonUI.createJButton("Add Customer");
	refreshCustomerButton = CommonUI.createJButton("Refresh Customer");
	customerToolBar = CommonUI.createToolbar(addCustomerButton, refreshCustomerButton);
	customersTable = CommonUI.createTable(customerDataModel);
	customerTableScrollPane = CommonUI.createScrollPane(customersTable);
	customerPanel = CommonUI.createCustomPanel(new BorderLayout());	
    }

    public Component getCustomerPanel() {
	customerPanel.add(customerTableScrollPane, BorderLayout.CENTER);
	customerPanel.add(customerToolBar, BorderLayout.NORTH);
	return customerPanel;
    }
}
