package com.epam.bench.carrental.server.car.service.implementations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.epam.bench.carrental.server.threadlocal.TenantId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.services.FleetService;
import com.epam.bench.carrental.server.car.entities.Car;
import com.epam.bench.carrental.server.car.repositories.FleetRepository;

public class FleetServiceImpl implements FleetService {
    @Autowired
    FleetRepository fleetRepository;
    @Autowired
    ModelMapper modelMapper;

    public void addCar(CarDTO fleet) {
	fleetRepository.save(modelMapper.map(fleet, Car.class));
    }

    public List<CarDTO> getFleet() {
	return StreamSupport.stream(fleetRepository.findAll().spliterator(), true)
		.map(s -> { CarDTO cardto = modelMapper.map(s, CarDTO.class); 
		    return cardto;})
		.collect(Collectors.toList());
    }

}
