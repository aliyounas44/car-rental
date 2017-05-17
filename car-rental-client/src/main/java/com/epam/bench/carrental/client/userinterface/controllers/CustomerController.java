package com.epam.bench.carrental.client.userinterface.controllers;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.client.bl.CustomerInformationRequestHandler;
import com.epam.bench.carrental.client.userinterface.CommonUI;
import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.commons.beans.CustomerDTO;

@Component
public class CustomerController {
    @Autowired
    private CustomerInformationRequestHandler customerInformationRequestHndlr;
    @Autowired
    private CustomTableModel<CustomerDTO> customerDataModel;

    public void refreshCustomerButtonlistener(ActionEvent arg0) {
	customerInformationRequestHndlr.getCustomers(this::setDataInModel);
    }

    public void addCustomerButtonlistener(ActionEvent arg0) {
	JTextField name = CommonUI.createTextField();
	JTextField email = CommonUI.createTextField();
	CustomerDTO customer;

	int result = JOptionPane.showConfirmDialog(null, CommonUI.createPanel(CommonUI.createLabel("Name: "), name, CommonUI.createLabel("Email: "), email), "Please Enter the Detail of Customers", JOptionPane.OK_CANCEL_OPTION);

	if (result == JOptionPane.OK_OPTION && !StringUtils.isBlank(name.getText()) && !StringUtils.isBlank(email.getText())) {
	    customer = new CustomerDTO(name.getText(), email.getText());
	    customerInformationRequestHndlr.addCustomer(customer);
	}
    }

    private void setDataInModel(List<CustomerDTO> list) {
	customerDataModel.setData(list);
    }
}