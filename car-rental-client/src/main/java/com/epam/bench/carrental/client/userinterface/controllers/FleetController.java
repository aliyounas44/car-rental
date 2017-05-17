package com.epam.bench.carrental.client.userinterface.controllers;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.client.bl.FleetRequestHandler;
import com.epam.bench.carrental.client.bl.RentalClassRequestHandler;
import com.epam.bench.carrental.client.userinterface.CommonUI;
import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;

@Component
public class FleetController {
    @Autowired
    FleetRequestHandler fleetInformationRequestHndlr;
    @Autowired
    CustomTableModel<CarDTO> fleetTableModel;
    @Autowired
    CustomTableModel<RentalClassDTO> rentalClassDataModel;
    @Autowired
    RentalClassRequestHandler rentalClassRequestHandler;

    JTable rentalClassTable;
    JScrollPane rentalClassTableScrollPane;

    @PostConstruct
    public void initialize() {
	rentalClassTable = CommonUI.createTable(rentalClassDataModel);
	rentalClassTableScrollPane = CommonUI.createScrollPane(rentalClassTable);
    }

    public void refreshFleet(ActionEvent arg0) {
	fleetInformationRequestHndlr.getFleets(this::updateModel);
    }

    public void addFleet(ActionEvent arg0) {
	JTextField name = CommonUI.createTextField();
	JTextField email = CommonUI.createTextField();
	CarDTO fleet;
	rentalClassRequestHandler.getRentalClasses(this::updateRentalClassModel);

	int result = JOptionPane.showConfirmDialog(null, CommonUI.createPanel(CommonUI.createLabel("Model: "), name, CommonUI.createLabel("Registration no: "), email, rentalClassTableScrollPane), "Please Enter the Detail of Car", JOptionPane.OK_CANCEL_OPTION);

	if (result == JOptionPane.OK_OPTION && !StringUtils.isBlank(name.getText()) && !StringUtils.isBlank(email.getText())) {
	    if (rentalClassTable.getSelectedRow() == -1) {
		CommonUI.showMessage("Please select car's class too.");
		return;
	    }
	    fleet = new CarDTO(name.getText(), email.getText(), rentalClassDataModel.getRow(rentalClassTable.getSelectedRow()));
	    fleetInformationRequestHndlr.addFleet(fleet);
	}
    }

    private void updateModel(List<CarDTO> list) {
	fleetTableModel.setData(list);
    }
    
    private void updateRentalClassModel(List<RentalClassDTO> list) {
	rentalClassDataModel.setData(list);
    }
}
