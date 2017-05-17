package com.epam.bench.carrental.client.userinterface;

import com.epam.bench.carrental.client.bl.CarBookingDetailsRequestHandler;
import com.epam.bench.carrental.client.bl.CustomerInformationRequestHandler;
import com.epam.bench.carrental.client.bl.RentalClassRequestHandler;
import com.epam.bench.carrental.client.userinterface.customized.components.DatePicker;
import com.epam.bench.carrental.client.userinterface.customized.components.DatePickerImpl;
import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

@org.springframework.stereotype.Component
public class BookingInformationUI {
    @Autowired
    CustomTableModel<CarDTO> carBookingInformationModel;
    @Autowired
    CustomTableModel<CustomerDTO> customerDataModel;
    @Autowired
    CarBookingDetailsRequestHandler carBookingDetailRequestHandler;
    @Autowired
    CustomerInformationRequestHandler customerInformationRequestHndlr;
    @Autowired
    RentalClassRequestHandler rentalClassRequestHandler;
    @Autowired
    JComboBox<String> comboBox;
    private DatePicker dateFrom = null;
    private DatePicker dateTo = null;

    private JButton jButtonBookAvailableCar = null;
    private JButton jButtonRefreshAvailableToBookCars = null;
    private JToolBar carBookingInformationToolBar = null;
    private JTable carBookingInformationTable = null;
    private JScrollPane carBookingInformationTableScrollPane = null;
    private JPanel carBookingInformationPanel = null;
    private JTable customersTable = null;
    private JScrollPane customersTableScrollPane = null;

    public void addListeners() {
        jButtonBookAvailableCar.addActionListener(this::bookAvailableCarListener);
        jButtonRefreshAvailableToBookCars.addActionListener(this::refreshAvailableToBookCarsListener);
    }

    public void refreshAvailableToBookCarsListener(ActionEvent arg0) {
        if (!validateDates(dateFrom.getSelectedDate(), dateTo.getSelectedDate())) {
            return;
        }
        carBookingDetailRequestHandler.fetchAvailableToBookCars(this::setDataInAvailableCarsModel, comboBox.getSelectedItem().toString(), dateFrom.getSelectedDate(), dateTo.getSelectedDate());
    }

    private void setDataInAvailableCarsModel(List<CarDTO> list) {
        carBookingInformationModel.setData(list);
    }

    public Component getBookingInformationPanel() {
        carBookingInformationPanel.add(carBookingInformationTableScrollPane, BorderLayout.CENTER);
        carBookingInformationPanel.add(carBookingInformationToolBar, BorderLayout.NORTH);
        return carBookingInformationPanel;
    }

    private void bookAvailableCarListener(ActionEvent arg0) {
        if (-1 == carBookingInformationTable.getSelectedRow()) {
            JOptionPane.showMessageDialog(null, "No Row Selected");
            return;
        }
        if (!validateDates(dateFrom.getSelectedDate(), dateTo.getSelectedDate())) {
            return;
        }
        customerInformationRequestHndlr.getCustomers(this::setDataInModel);
        CarDTO car = carBookingInformationModel.getRow(carBookingInformationTable.getSelectedRow());
        int optionSelected = JOptionPane.showConfirmDialog(null, customersTableScrollPane, "Rent [" + car.getRegistrationNumber() + ", " + car.getModel() + "]", JOptionPane.OK_CANCEL_OPTION);
        if (optionSelected == 0 && customersTable.getSelectedRow() != -1) {
            carBookingDetailRequestHandler.bookCar(car, customerDataModel.getRow(customersTable.getSelectedRow()), dateFrom.getSelectedDate(), dateTo.getSelectedDate());
        }
    }

    private boolean validateDates(LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom == null || dateTo == null) {
            CommonUI.showMessage("You have to provide date to and date from for the Report.");
            return false;
        }
        if (dateFrom.isAfter(dateTo)) {
            CommonUI.showMessage("Date From can not be greater than date to.");
            return false;
        }
        if (dateTo.isBefore(LocalDate.now())) {
            CommonUI.showMessage("Booking can't be done in past dates.");
            return false;
        }
        return true;
    }


    private void setDataInModel(List<CustomerDTO> list) {
        customerDataModel.setData(list);
    }

    @PostConstruct
    private void initializeControl() {
        jButtonBookAvailableCar = CommonUI.createJButton("Book");
        jButtonRefreshAvailableToBookCars = CommonUI.createJButton("Refresh");
        dateFrom = new DatePickerImpl();
        dateTo = new DatePickerImpl();
        carBookingInformationToolBar = CommonUI.createToolbar(jButtonBookAvailableCar, jButtonRefreshAvailableToBookCars, comboBox, dateFrom.getComponent(), dateTo.getComponent());
        carBookingInformationTable = CommonUI.createTable(carBookingInformationModel);
        carBookingInformationTableScrollPane = CommonUI.createScrollPane(carBookingInformationTable);
        carBookingInformationPanel = CommonUI.createCustomPanel(new BorderLayout());
        customersTable = CommonUI.createTable(customerDataModel);
        customersTableScrollPane = CommonUI.createScrollPane(customersTable);
        rentalClassRequestHandler.getRentalClasses(this::populateComboBoxWithRentalClasses);
    }

    private void populateComboBoxWithRentalClasses(List<RentalClassDTO> rentalClasses) {
        rentalClasses.stream().map(rentalClass -> rentalClass.getName()).forEach(item -> comboBox.addItem(item));
    }

}
