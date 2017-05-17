package com.epam.bench.carrental.commons.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public final class CarRentalDetailDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    private CarDTO car;
    private CustomerDTO customer;
    private LocalDateTime rentalStartDateTime;
    private LocalDateTime rentalEndDateTime;


    public CarRentalDetailDTO() {

    }

    public CarRentalDetailDTO(CarDTO car, CustomerDTO customer, LocalDateTime rentalStartDateTime, LocalDateTime rentalEndDateTime) {
        this.car = car;
        this.customer = customer;
        this.rentalStartDateTime = rentalStartDateTime;
        this.rentalEndDateTime = rentalEndDateTime;
    }

    public CarDTO getCar() {
        return car;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public LocalDateTime getRentalStartDateTime() {
        return rentalStartDateTime;
    }

    public LocalDateTime getRentalEndDateTime() {
        return rentalEndDateTime;
    }

    @Override
    public String toString() {
        return "CarRentalDetailDTO [car=" + car + ", customer=" + customer + ", rentalStartDateTime=" + rentalStartDateTime + "]";
    }

}
