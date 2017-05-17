package com.epam.bench.carrental.commons.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public final class CarDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id = 0;
    private String model;
    private String registrationNumber;
    private RentalClassDTO rentalClass;
    private LocalDateTime plannedReturnedDateTime;

    public CarDTO() {

    }

    public CarDTO(String model, String registrationNumber, RentalClassDTO rentalClassDto) {
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.rentalClass = rentalClassDto;
    }

    public CarDTO(String model, String registrationNumber, RentalClassDTO rentalClassDto, int id) {
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.rentalClass = rentalClassDto;
        this.id = id;
    }
    public String getModel() {
        return model;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public int getId() {
        return id;
    }
    public RentalClassDTO getRentalClassDto() {
        return rentalClass;
    }
    public LocalDateTime getPlannedReturnedDateTime() {
        return plannedReturnedDateTime;
    }

    public void setPlannedReturnedDateTime(LocalDateTime plannedReturnedDateTime) {
        this.plannedReturnedDateTime = plannedReturnedDateTime;
    }

    @Override
    public String toString() {
        return "CarDTO [id=" + id + ", model=" + model + ", registrationNumber=" + registrationNumber
                + ", rentalClassDto=" + rentalClass + "]";
    }
}
