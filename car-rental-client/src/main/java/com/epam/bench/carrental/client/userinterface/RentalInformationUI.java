package com.epam.bench.carrental.client.userinterface;

import com.epam.bench.carrental.client.bl.CarRentalDetailsRequestHandler;
import com.epam.bench.carrental.client.bl.CustomerInformationRequestHandler;
import com.epam.bench.carrental.client.bl.RentalClassRequestHandler;
import com.epam.bench.carrental.client.userinterface.controllers.CarRentalDetailsController;
import com.epam.bench.carrental.client.userinterface.customized.components.DateTimePicker;
import com.epam.bench.carrental.client.userinterface.customized.components.DateTimePickerImpl;
import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Component
public class RentalInformationUI {
    @Autowired
    CustomTableModel<CarDTO> carRentalInformationModel;
    @Autowired
    CarRentalDetailsController carRentalDetailsController;
    @Autowired
    CustomTableModel<CustomerDTO> customerDataModel;
    @Autowired
    CarRentalDetailsRequestHandler carRentalDetailRequestHandler;
    @Autowired
    CustomerInformationRequestHandler customerInformationRequestHndlr;
    @Autowired
    RentalClassRequestHandler rentalClassRequestHandler;
    @Autowired
    JComboBox<String> comboBox;

    private JButton jButtonRentAvailableCar = null;
    private JButton jButtonRefreshAvailableCars = null;
    private JToolBar carRentalInformationToolBar = null;
    private JTable carRentalInformationTable = null;
    private JScrollPane carRentalInformationTableScrollPane = null;
    private JPanel carRentalInformationPanel = null;
    private JTable customersTable = null;
    private JScrollPane customersTableScrollPane = null;
    private DateTimePicker dateTimePicker;

    public void addListeners() {
        jButtonRentAvailableCar.addActionListener(this::rentAvailableCarListener);
        jButtonRefreshAvailableCars.addActionListener(this::refreshAvailableCarsListener);
    }

    public void refreshAvailableCarsListener(ActionEvent arg0) {
        if (!isPlannedDateValid(dateTimePicker.getSelectedDateTime())) {
            return;
        }
        carRentalDetailRequestHandler.fetchAvailableCars(this::setDataInAvailableCarsModel, comboBox.getSelectedItem().toString(), dateTimePicker.getSelectedDateTime());
    }

    private void setDataInAvailableCarsModel(List<CarDTO> list) {
        carRentalInformationModel.setData(list);
    }

    public Component getRentalInformationPanel() {
        carRentalInformationPanel.add(carRentalInformationTableScrollPane, BorderLayout.CENTER);
        carRentalInformationPanel.add(carRentalInformationToolBar, BorderLayout.NORTH);
        return carRentalInformationPanel;
    }

    private void rentAvailableCarListener(ActionEvent arg0) {
        if (-1 == carRentalInformationTable.getSelectedRow()) {
            JOptionPane.showMessageDialog(null, "No Row Selected");
            return;
        }
        if (!isPlannedDateValid(dateTimePicker.getSelectedDateTime())) {
            return;
        }
        customerInformationRequestHndlr.getCustomers(this::setDataInModel);
        CarDTO car = carRentalInformationModel.getRow(carRentalInformationTable.getSelectedRow());
        int optionSelected = JOptionPane.showConfirmDialog(null, customersTableScrollPane, "Rent [" + car.getRegistrationNumber() + ", " + car.getModel() + "]", JOptionPane.OK_CANCEL_OPTION);
        if (optionSelected == 0 && customersTable.getSelectedRow() != -1) {
            carRentalDetailRequestHandler.rentCar(car, customerDataModel.getRow(customersTable.getSelectedRow()), dateTimePicker.getSelectedDateTime());
        }
    }

    private boolean isPlannedDateValid(LocalDateTime plannedReturnDateTime) {
        if (dateTimePicker == null || dateTimePicker.getSelectedDateTime() == null) {
            CommonUI.showMessage("You have to provide planned return date.");
            return false;
        }
        if (dateTimePicker.getSelectedDateTime().isBefore(LocalDateTime.now())) {
            CommonUI.showMessage("Planned return date should be future date.");
            return false;
        }
        return true;
    }

    private void setDataInModel(List<CustomerDTO> list) {
        customerDataModel.setData(list);
    }

    @PostConstruct
    private void initializeControl() {
        jButtonRentAvailableCar = CommonUI.createJButton("Rent");
        jButtonRefreshAvailableCars = CommonUI.createJButton("Refresh");
        dateTimePicker = new DateTimePickerImpl();
        carRentalInformationToolBar = CommonUI.createToolbar(jButtonRentAvailableCar, jButtonRefreshAvailableCars, dateTimePicker.getComponent());
        carRentalInformationTable = CommonUI.createTable(carRentalInformationModel);
        carRentalInformationTableScrollPane = CommonUI.createScrollPane(carRentalInformationTable);
        carRentalInformationPanel = CommonUI.createCustomPanel(new BorderLayout());
        customersTable = CommonUI.createTable(customerDataModel);
        customersTableScrollPane = CommonUI.createScrollPane(customersTable);
        carRentalInformationToolBar.add(comboBox);

        rentalClassRequestHandler.getRentalClasses(this::populateComboBoxWithRentalClasses);
    }

    private void populateComboBoxWithRentalClasses(List<RentalClassDTO> rentalClasses) {
        rentalClasses.stream().map(rentalClass -> rentalClass.getName()).forEach(item -> comboBox.addItem(item));
    }

}
