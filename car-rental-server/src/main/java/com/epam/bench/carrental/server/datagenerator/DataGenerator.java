package com.epam.bench.carrental.server.datagenerator;

import com.epam.bench.carrental.server.booking.data.generator.BookingGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.server.car.data.generator.CarsGenerator;
import com.epam.bench.carrental.server.carrentaldetail.data.generator.RentalDetailsGenerator;
import com.epam.bench.carrental.server.customer.data.generator.CustomersGenerator;
import com.epam.bench.carrental.server.rentalclass.data.generator.RentalClassesGenerator;

import javax.annotation.PostConstruct;

@Component
public class DataGenerator {
    @Autowired
    CustomersGenerator customersGenerator;
    @Autowired
    CarsGenerator carsGenerator;
    @Autowired
    RentalClassesGenerator rentalClassesGenerator;
    @Autowired
    RentalDetailsGenerator rentalDetailsGenerator;
    @Autowired
    BookingGenerator bookingGenerator;

    @PostConstruct
    public void generateData() {
        customersGenerator.generateCustomers();
        rentalClassesGenerator.generateRentalClasses();
        carsGenerator.generateCars();
        rentalDetailsGenerator.generateRentalDetails();
        bookingGenerator.generateBookings();
    }
}
