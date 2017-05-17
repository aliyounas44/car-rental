package com.epam.bench.carrental.server.car.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.epam.bench.carrental.server.rentalclass.entities.RentalClass;

import java.time.LocalDateTime;


@Entity
public class Car {
    private String model;

    @Column(unique=true)
    private String registrationNumber;

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isRented;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private RentalClass rentalClass;

    @Version
    private Integer version;

    private LocalDateTime plannedReturnedDateTime;

    public Car() {

    }

    public Car(String model, String registrationNumber, boolean isRented, RentalClass rentalClass) {
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.isRented = isRented;
        this.rentalClass = rentalClass;
    }

    public Car(String model, String registrationNumber, boolean isRented, RentalClass rentalClass, int id) {
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.isRented = isRented;
        this.rentalClass = rentalClass;
        this.id = id;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public int getId() {
        return id;
    }
    public boolean isRented() {
        return isRented;
    }
    public void setRented(boolean isRented) {
        this.isRented = isRented;
    }
    public RentalClass getRentalClass() {
        return rentalClass;
    }
    public void setRentalClass(RentalClass rentalClass) {
        this.rentalClass = rentalClass;
    }
    public LocalDateTime getPlannedReturnedDateTime() {
        return plannedReturnedDateTime;
    }

    public void setPlannedReturnedDateTime(LocalDateTime plannedReturnedDateTime) {
        this.plannedReturnedDateTime = plannedReturnedDateTime;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", id=" + id +
                ", isRented=" + isRented +
                ", rentalClass=" + rentalClass +
                ", version=" + version +
                ", plannedReturnedDateTime=" + plannedReturnedDateTime +
                '}';
    }
}
