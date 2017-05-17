package com.epam.bench.carrental.server.rentalclass.data.generator;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.commons.beans.RentalClassDTO;
import com.epam.bench.carrental.commons.services.RentalClassService;

@Component
public class RentalClassesGenerator {
    @Autowired
    DataFactory dataFactory;
    @Autowired
    RentalClassService rentalClassServiceImpl;
    String[] classes = {"Economy", "Compact", "Intermediate", "Fullsize", "SUV", "Premium", "Elite"};
    
    public void generateRentalClasses() {
	for (String rentalClass : classes) {
	    rentalClassServiceImpl.addRentalClass(new RentalClassDTO(rentalClass, dataFactory.getNumberBetween(1, 20)));
	}
    }
}
