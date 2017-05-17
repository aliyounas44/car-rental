package com.epam.bench.carrental.commons.services;

import java.util.List;

import com.epam.bench.carrental.commons.beans.CarDTO;

public interface FleetService {
    public void addCar(CarDTO fleet);
    public List<CarDTO> getFleet();
}
