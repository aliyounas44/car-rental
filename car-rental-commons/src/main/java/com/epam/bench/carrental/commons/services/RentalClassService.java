package com.epam.bench.carrental.commons.services;

import java.util.List;

import com.epam.bench.carrental.commons.beans.RentalClassDTO;

public interface RentalClassService {
    public void addRentalClass(RentalClassDTO rentalClassDTO);
    public List<RentalClassDTO> getRentalClasses();
}
