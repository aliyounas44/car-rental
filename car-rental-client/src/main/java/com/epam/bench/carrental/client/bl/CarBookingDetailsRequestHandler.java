package com.epam.bench.carrental.client.bl;

import com.epam.bench.carrental.client.userinterface.CommonUI;
import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.services.CarBookingDetailService;
import com.epam.bench.carrental.thread.service.RemoteCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

@Component
public class CarBookingDetailsRequestHandler {

    @Autowired
    CarBookingDetailService carBookingDetailService;

    public void fetchAvailableToBookCars(Consumer<List<CarDTO>> consumer, String rentalClass, LocalDate fromDate, LocalDate toDate) {
        RemoteCaller remoteCaller = new RemoteCaller();
        remoteCaller.execute(() -> fetchAvailableToBookCars(rentalClass, fromDate, toDate), consumer, exception -> CommonUI.showMessage("Unable to fetch Available to book Cars."));
    }

    public void bookCar(CarDTO carDTO, CustomerDTO customerDTO, LocalDate fromDate, LocalDate toDate) {
        RemoteCaller remoteCaller = new RemoteCaller();
        remoteCaller.execute(() -> this.book(carDTO, customerDTO, fromDate, toDate), result -> CommonUI.showMessage("Car Booked "), exception -> CommonUI.showMessage("Unable to book car."));
    }

    private List<CarDTO> fetchAvailableToBookCars(String rentalClass, LocalDate fromDate, LocalDate toDate) {
        return carBookingDetailService.getAvailableToBookCars(rentalClass, fromDate, toDate);
    }

    private CarDTO book(CarDTO carDTO, CustomerDTO customerDTO, LocalDate fromDate, LocalDate toDate) {
        carBookingDetailService.bookCar(carDTO.getId(), customerDTO.getId(), fromDate, toDate);
        return carDTO;
    }

}
