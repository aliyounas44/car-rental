package com.epam.bench.carrental.client.userinterface.controllers;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JComboBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.client.bl.CarRentalDetailsRequestHandler;
import com.epam.bench.carrental.client.userinterface.models.CustomTableModel;
import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;

@Component
public class CarRentalDetailsController {
    @Autowired
    CarRentalDetailsRequestHandler carRentalDetailRequestHandler;
    @Autowired
    CustomTableModel<CarDTO> carRentalInformationModel;
    @Autowired
    CustomTableModel<CarRentalDetailDTO> currentlyRentalInformationModel;
    @Autowired
    JComboBox<String> comboBox;

    public void refreshRentedCarsListener(ActionEvent arg0) {
        carRentalDetailRequestHandler.fetchCurrentlyRentedCarsWithCustomer(this::setDataInRentedCarsModel);
    }



    private void setDataInRentedCarsModel(List<CarRentalDetailDTO> list) {
        currentlyRentalInformationModel.setData(list);
    }
}
