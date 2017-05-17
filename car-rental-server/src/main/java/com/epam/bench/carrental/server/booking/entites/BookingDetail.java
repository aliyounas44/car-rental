package com.epam.bench.carrental.server.booking.entites;

import com.epam.bench.carrental.server.car.entities.Car;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Car car;

    private LocalDate bookingStartDateTime;
    private LocalDate bookingEndDateTime;

    public BookingDetail(Car car, LocalDate bookingStartDateTime, LocalDate bookingEndDateTime) {
        this.car = car;
        this.bookingStartDateTime = bookingStartDateTime;
        this.bookingEndDateTime = bookingEndDateTime;
    }

    public BookingDetail() {

    }

    public LocalDate getBookingEndDateTime() {
        return bookingEndDateTime;
    }

    public void setBookingEndDateTime(LocalDate bookingEndDateTime) {
        this.bookingEndDateTime = bookingEndDateTime;
    }

    public LocalDate getBookingStartDateTime() {
        return bookingStartDateTime;
    }

    public void setBookingStartDateTime(LocalDate bookingStartDateTime) {
        this.bookingStartDateTime = bookingStartDateTime;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "BookingDetail{" +
                "id=" + id +
                ", car=" + car +
                ", bookingStartDateTime=" + bookingStartDateTime +
                ", bookingEndDateTime=" + bookingEndDateTime +
                '}';
    }
}
