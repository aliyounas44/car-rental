package com.epam.bench.carrental.server.carrentaldetail.entities;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.epam.bench.carrental.server.car.entities.Car;
import com.epam.bench.carrental.server.customer.entities.Customer;

@Entity
public class CarRentalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Car car;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Customer customer;

    private LocalDateTime rentalStartDateTime;

    private LocalDateTime rentalEndDateTime;

    public CarRentalDetail() {

    }

    public CarRentalDetail(Car car, int customerId, LocalDateTime rentalStartDateTime) {
        this.car = car;
        this.customer = new Customer();
        this.customer.setId(customerId);
        this.rentalStartDateTime = rentalStartDateTime;
    }

    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getRentalEndDateTime() {
        return rentalEndDateTime;
    }

    public void setRentalEndDateTime(LocalDateTime rentalEndDateTime) {
        this.rentalEndDateTime = rentalEndDateTime;
    }

    public LocalDateTime getRentalStartDateTime() {
        return rentalStartDateTime;
    }

    public void setRentalStartDateTime(LocalDateTime rentalStartDateTime) {
        this.rentalStartDateTime = rentalStartDateTime;
    }

    @Override
    public String toString() {
        return "CarRentalDetail [id=" + id + ", car=" + car + ", customer=" + customer + ", rentalStartDateTime="
                + rentalStartDateTime + ", rentalEndDateTime=" + rentalEndDateTime + "]";
    }
}
