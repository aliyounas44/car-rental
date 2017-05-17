package com.epam.bench.carrental.server.car.data.generator;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.services.FleetService;
import com.epam.bench.carrental.commons.services.RentalClassService;

@Component
public class CarsGenerator {
    @Autowired
    DataFactory dataFactory;
    @Autowired
    FleetService fleetServiceImpl;
    @Autowired
    RentalClassService rentalClassServiceImpl;
    String[] models = {"Mehran", "Swift", "Ravi", "Cultus", "Liana", "Jimny", "CRV", "Corolla", "City", "Accord", "Civic", "Kizashi"};
    public void generateCars() {
	for (int i = 0; i < 100; i++) {
	    fleetServiceImpl.addCar(new CarDTO(dataFactory.getItem(models), dataFactory.getCity()+"-"+dataFactory.getRandomText(3)+"-"+dataFactory.getNumberText(4), rentalClassServiceImpl.getRentalClasses().get(dataFactory.getNumberBetween(1, 7))));
	}
    }
}
