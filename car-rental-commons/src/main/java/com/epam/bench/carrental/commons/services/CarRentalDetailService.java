package com.epam.bench.carrental.commons.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;

public interface CarRentalDetailService {
    public void rentCar(int carId, int customerId, LocalDateTime plannedReturnDate);
    public void returnCar(int carId);
    public List<CarRentalDetailDTO> getAllRentalInformation();
    public List<CarDTO> getAvailableCars(String rentalClass, LocalDateTime selectedDateTime);
    public List<CarRentalDetailDTO> getRentedCarsWithCustomer();
    public List<CarRentalDetailDTO> getRentalHistory(LocalDate fromDate, LocalDate toDate);
}
