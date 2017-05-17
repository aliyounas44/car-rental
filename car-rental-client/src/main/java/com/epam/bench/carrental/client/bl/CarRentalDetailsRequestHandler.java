package com.epam.bench.carrental.client.bl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.client.userinterface.CommonUI;
import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;
import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.services.CarRentalDetailService;
import com.epam.bench.carrental.thread.service.RemoteCaller;

@Component
public class CarRentalDetailsRequestHandler {

    @Autowired
    CarRentalDetailService carRentalDetailService;

    public void fetchAvailableCars(Consumer<List<CarDTO>> consumer, String rentalClass, LocalDateTime selectedDateTime) {
        RemoteCaller remoteCaller = new RemoteCaller();
        remoteCaller.execute(() -> fetchAvailableCars(rentalClass, selectedDateTime), consumer, exception -> CommonUI.showMessage("Unable to fetch Available Cars."));
    }

    public void rentCar(CarDTO carDTO, CustomerDTO customerDTO, LocalDateTime plannedReturnDate) {
        RemoteCaller remoteCaller = new RemoteCaller();
        remoteCaller.execute(() -> this.rent(carDTO, customerDTO, plannedReturnDate), result -> CommonUI.showMessage("Car Rented "), exception -> CommonUI.showMessage("Unable to rent car."));
    }

    public void returnCarToFleet(CarDTO carDTO) {
        RemoteCaller remoteCaller = new RemoteCaller();
        remoteCaller.execute(() -> this.returnCar(carDTO), result -> CommonUI.showMessage("Car returned "), exception -> CommonUI.showMessage("Unable to return car."));
    }

    public void fetchCurrentlyRentedCarsWithCustomer(Consumer<List<CarRentalDetailDTO>> consumer) {
        RemoteCaller remoteCaller = new RemoteCaller();
        remoteCaller.execute(carRentalDetailService::getRentedCarsWithCustomer, consumer, exception -> CommonUI.showMessage("Unable to fetch rented cars."));
    }

    public void fetchHistory(Consumer<List<CarRentalDetailDTO>> consumer, LocalDate fromDate, LocalDate toDate) {
        RemoteCaller remoteCaller = new RemoteCaller();
        remoteCaller.execute(() -> this.fetchRentalHistory(fromDate, toDate), consumer, exception -> CommonUI.showMessage("Unable to fetch Rental History."));
    }

    private List<CarRentalDetailDTO> fetchRentalHistory(LocalDate fromDate, LocalDate toDate) {
        return carRentalDetailService.getRentalHistory(fromDate, toDate);
    }

    private List<CarDTO> fetchAvailableCars(String rentalClass, LocalDateTime selectedDateTime) {
        return carRentalDetailService.getAvailableCars(rentalClass, selectedDateTime);
    }

    private CarDTO returnCar(CarDTO carDTO) {
        carRentalDetailService.returnCar(carDTO.getId());
        return carDTO;
    }

    private CarDTO rent(CarDTO carDTO, CustomerDTO customerDTO, LocalDateTime plannedReturnDate) {
        carRentalDetailService.rentCar(carDTO.getId(), customerDTO.getId(), plannedReturnDate);
        return carDTO;
    }

}
