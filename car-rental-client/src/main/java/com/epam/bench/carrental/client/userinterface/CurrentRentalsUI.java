package com.epam.bench.carrental.client.userinterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.bench.carrental.client.bl.CarRentalDetailsRequestHandler;
import com.epam.bench.carrental.client.userinterface.controllers.CarRentalDetailsController;
import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;

@org.springframework.stereotype.Component
public class CurrentRentalsUI {
    @Autowired
    CarRentalDetailsController carRentalDetailsController;
    @Autowired
    CustomTableModel<CarRentalDetailDTO> currentlyRentalInformationModel;
    @Autowired
    CarRentalDetailsRequestHandler carRentalDetailRequestHandler;

    private JButton refreshCurrentRentalsButton = null;
    private JButton returnCarButton = null;
    private JToolBar currentRentalsToolBar = null;
    private JTable currentRentalsTable = null;
    private JScrollPane currentRentalsTableScrollPane = null;
    private JPanel currentRentalsPanel = null;

    public void addListeners() {
        refreshCurrentRentalsButton.addActionListener(carRentalDetailsController::refreshRentedCarsListener);
        returnCarButton.addActionListener(this::returnCar);
    }

    private void returnCar(ActionEvent arg0) {
        if (-1 == currentRentalsTable.getSelectedRow()) {
            JOptionPane.showMessageDialog(null, "No Row Selected");
            return;
        }
        CarRentalDetailDTO carRentalDetailDto = currentlyRentalInformationModel.getRow(currentRentalsTable.getSelectedRow());
        carRentalDetailRequestHandler.returnCarToFleet(carRentalDetailDto.getCar());
    }

    @PostConstruct
    private void initializeControls() {
        refreshCurrentRentalsButton = CommonUI.createJButton("Refresh");
        returnCarButton = CommonUI.createJButton("Return");
        currentRentalsToolBar = CommonUI.createToolbar(refreshCurrentRentalsButton, returnCarButton);
        currentRentalsTable = CommonUI.createTable(currentlyRentalInformationModel);
        currentRentalsTableScrollPane = CommonUI.createScrollPane(currentRentalsTable);
        currentRentalsPanel = CommonUI.createCustomPanel(new BorderLayout());
    }

    public Component getCurrentRentalInformationPanel() {
        currentRentalsPanel.add(currentRentalsTableScrollPane, BorderLayout.CENTER);
        currentRentalsPanel.add(currentRentalsToolBar, BorderLayout.NORTH);
        return currentRentalsPanel;
    }
}
