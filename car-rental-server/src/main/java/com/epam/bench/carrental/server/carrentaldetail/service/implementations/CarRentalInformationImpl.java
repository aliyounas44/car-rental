package com.epam.bench.carrental.server.carrentaldetail.service.implementations;

import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;
import com.epam.bench.carrental.commons.services.CarBookingDetailService;
import com.epam.bench.carrental.commons.services.CarRentalDetailService;
import com.epam.bench.carrental.server.car.entities.Car;
import com.epam.bench.carrental.server.car.repositories.FleetRepository;
import com.epam.bench.carrental.server.car.service.implementations.FleetServiceImpl;
import com.epam.bench.carrental.server.carrentaldetail.entities.CarRentalDetail;
import com.epam.bench.carrental.server.carrentaldetail.repositories.CarRentalDetailRepository;
import com.epam.bench.carrental.server.time.CustomizedClock;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CarRentalInformationImpl implements CarRentalDetailService {

    @Autowired
    CarRentalDetailRepository carRentalInformationRepository;
    @Autowired
    FleetRepository fleetRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FleetServiceImpl fleetServiceImpl;
    @Autowired
    RentalDetailsFetcher rentalDetailsFetcher;
    @Autowired
    CustomizedClock clock;
    @Autowired
    CarBookingDetailService carBookingDetailImpl;

    @Override
    @Transactional
    public void rentCar(int carId, int customerId, LocalDateTime plannedReturnDate) {
        Car car = findAvailableCarById(carId, plannedReturnDate);
        carRentalInformationRepository.save(new CarRentalDetail(car, customerId, clock.now()));
        car.setRented(true);
        car.setPlannedReturnedDateTime(plannedReturnDate);
    }

    private Car findAvailableCarById(int carId, LocalDateTime plannedReturnDate) {
        Optional<Car> searchedCarWithIdAndIsRented = StreamSupport.stream(fleetRepository.findAll().spliterator(), true)
                .filter(car -> this.isCarAvailable(car, carId)).findFirst();
        if (!searchedCarWithIdAndIsRented.isPresent()) {
            throw new RuntimeException("Car: " + carId + " not available for this operation.");
        }
        return searchedCarWithIdAndIsRented.get();
    }

    private boolean isCarAvailable(Car car, int carId) {
        return car.getId() == carId && car.isRented() == false;
    }

    @Override
    @Transactional
    public void returnCar(int carId) {
        Car car = findRentedCarById(carId);

        Optional<CarRentalDetail> searchedRentalInformationofGivenCar = StreamSupport
                .stream(carRentalInformationRepository.findAll().spliterator(), true)
                .filter(carRentalInformation -> carRentalInformation.getCar().getId() == carId
                        && carRentalInformation.getRentalEndDateTime() == null)
                .findFirst();

        if (!searchedRentalInformationofGivenCar.isPresent()) {
            throw new RuntimeException("Car: " + carId + " Already Available/returned!");
        }

        CarRentalDetail carRentalDetail = searchedRentalInformationofGivenCar.get();
        carRentalDetail.setRentalEndDateTime(clock.now());
        car.setRented(false);
        car.setPlannedReturnedDateTime(null);
    }

    private Car findRentedCarById(int carId) {
        Optional<Car> searchedCarWithIdAndIsRented = StreamSupport.stream(fleetRepository.findAll().spliterator(), false)
                .filter(car -> car.getId() == carId && car.isRented() == true).findFirst();
        if (!searchedCarWithIdAndIsRented.isPresent()) {
            throw new RuntimeException("Car: " + carId + " not available for this operation.");
        }
        return searchedCarWithIdAndIsRented.get();
    }

    @Override
    public List<CarRentalDetailDTO> getAllRentalInformation() {
        return StreamSupport.stream(carRentalInformationRepository.findAll().spliterator(), false)
                .map(s -> modelMapper.map(s, CarRentalDetailDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<CarDTO> getAvailableCars(String rentalClass, LocalDateTime plannedReturnDateTime) {
        long startTime = System.currentTimeMillis();
        List<CarDTO> availableCars =  carBookingDetailImpl.getAvailableToBookCars(rentalClass, LocalDate.now(), plannedReturnDateTime.toLocalDate());

        System.out.println("Time Consumed by Rental Service - " + (System.currentTimeMillis() - startTime));

        return availableCars;
    }

    @Override
    public List<CarRentalDetailDTO> getRentedCarsWithCustomer() {
        return this.getAllRentalInformation().stream().filter(s -> s.getRentalEndDateTime() == null)
                .collect(Collectors.toList());
    }

    public List<CarRentalDetailDTO> getRentalHistory(LocalDate dateFrom, LocalDate dateTo) {
        return rentalDetailsFetcher.fetchRentalDetailsInSpecifiedDateRange(dateFrom, dateTo, true);
    }

}
