package com.epam.bench.carrental.client.userinterface.controllers;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.epam.bench.carrental.client.bl.RentalClassRequestHandler;
import com.epam.bench.carrental.client.userinterface.CommonUI;
import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;

@Controller
public class RentalClassController {
    @Autowired
    CustomTableModel<RentalClassDTO> rentalClassDataModel;
    @Autowired
    RentalClassRequestHandler rentalClassRequestHandler;
    @Autowired
    JComboBox<String> comboBox;

    public void refreshRentalClasses(ActionEvent arg0) {
	rentalClassRequestHandler.getRentalClasses(this::updateModel);
    }

    public void addRentalClass(ActionEvent arg0) {
	JTextField name = CommonUI.createTextField();
	JTextField hourlyRate = CommonUI.createTextField();

	int result = JOptionPane.showConfirmDialog(null, CommonUI.createPanel(CommonUI.createLabel("Name: "), name, CommonUI.createLabel("Hourly Rate: "), hourlyRate), "Please Enter the Detail of Rental Class", JOptionPane.OK_CANCEL_OPTION);

	if (result == JOptionPane.OK_OPTION && !StringUtils.isBlank(name.getText()) && !StringUtils.isBlank(hourlyRate.getText())) {
	    double hRate = 0.0;
	    try {
		hRate = Double.parseDouble(hourlyRate.getText());
	    } catch(NumberFormatException e) {
		CommonUI.showMessage("Please enter correct hourly rate.");
		return;
	    }
	    rentalClassRequestHandler.addRentalClass(new RentalClassDTO(name.getText(), hRate));
	    comboBox.addItem(name.getText());
	}
	
    }

    private void updateModel(List<RentalClassDTO> list) {
	rentalClassDataModel.setData(list);
    }
}
