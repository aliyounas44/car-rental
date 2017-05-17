package com.epam.bench.carrental.commons.services;

import com.epam.bench.carrental.commons.beans.CarDTO;

import java.time.LocalDate;
import java.util.List;

public interface CarBookingDetailService {
    public void bookCar(int carId, int customerId, LocalDate fromDate, LocalDate toDate);
    public List<CarDTO> getAvailableToBookCars(String rentalClass, LocalDate fromDate, LocalDate toDate);
}
