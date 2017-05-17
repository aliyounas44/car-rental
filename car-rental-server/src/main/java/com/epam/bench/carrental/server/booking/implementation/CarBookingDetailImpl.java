package com.epam.bench.carrental.server.booking.implementation;

import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.services.CarBookingDetailService;
import com.epam.bench.carrental.server.booking.entites.BookingDetail;
import com.epam.bench.carrental.server.booking.repositories.BookingRepository;
import com.epam.bench.carrental.server.car.entities.Car;
import com.epam.bench.carrental.server.car.repositories.FleetRepository;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CarBookingDetailImpl implements CarBookingDetailService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FleetRepository fleetRepository;
    @Autowired
    LocalContainerEntityManagerFactoryBean entityManagerFactory;


    @Override
    @Transactional
    public void bookCar(int carId, int customerId, LocalDate fromDate, LocalDate toDate) {
        Map<Integer, List<BookingDetail>>  bookingDetailsCache = StreamSupport.stream(bookingRepository.findAll().spliterator(), true).collect(Collectors.groupingBy(bookingDetail -> bookingDetail.getCar().getId()));
        if (!isCarAvailableForBooking(fromDate, toDate, bookingDetailsCache.get(carId))) {
            throw new RuntimeException("Car with id  --> " + carId + " is not available for booking in provided dates.");
        }
        BookingDetail bookingDetail = new BookingDetail(fleetRepository.findOne(carId), fromDate, toDate);
        bookingRepository.save(bookingDetail);
    }

    private boolean isCarAvailableForBooking(LocalDate fromDate, LocalDate toDate, List<BookingDetail> bookingDetails) {
        return bookingDetails == null || bookingDetails
                .stream()
                .parallel()
                .filter(bookingDetail -> this.isBookingAvailable(fromDate, toDate, bookingDetail))
                .count() ==  bookingDetails.size();

    }

    public boolean isBookingAvailable(LocalDate fromDate, LocalDate toDate, BookingDetail bookingDetail) {
        return ((null == bookingDetail.getCar().getPlannedReturnedDateTime() || bookingDetail.getCar().getPlannedReturnedDateTime().toLocalDate().isBefore(fromDate)) &&
                (toDate.isBefore(bookingDetail.getBookingStartDateTime()) || fromDate.isAfter(bookingDetail.getBookingEndDateTime()) ));
    }

    @Override
    public List<CarDTO> getAvailableToBookCars(String rentalClass, LocalDate fromDate, LocalDate toDate) {
        long startTime = System.currentTimeMillis();
        Map<Integer, List<BookingDetail>>  bookingDetailsCache = StreamSupport.stream(bookingRepository.findAll().spliterator(), true).collect(Collectors.groupingBy(bookingDetail -> bookingDetail.getCar().getId()));
        List<CarDTO> carsDTO = StreamSupport.stream(fleetRepository.findAll().spliterator(), false)
                .filter(car -> isRentalClass(rentalClass, car) && isCarAvailableForBooking(fromDate, toDate, bookingDetailsCache.get(car.getId())))
                .map(car -> modelMapper.map(car, CarDTO.class))
                .collect(Collectors.toList());

        System.out.println("Booking carTime  = "+(System.currentTimeMillis() - startTime));
        return carsDTO;
    }

    private boolean isRentalClass(String rentalClass, Car car) {
        return StringUtils.isBlank(rentalClass) || car.getRentalClass().getName().equals(rentalClass);
    }
}
