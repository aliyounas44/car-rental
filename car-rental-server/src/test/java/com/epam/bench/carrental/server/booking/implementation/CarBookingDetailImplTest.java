package com.epam.bench.carrental.server.booking.implementation;

import com.epam.bench.carrental.server.booking.entites.BookingDetail;
import com.epam.bench.carrental.server.car.entities.Car;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class CarBookingDetailImplTest {
    CarBookingDetailImpl carBookingDetailImpl = new CarBookingDetailImpl();

    private BookingDetail getBookingDetails(LocalDate fromDate, LocalDate toDate) {
        return new BookingDetail(new Car(), fromDate, toDate);
    }

    @Test
    @Parameters(method = "testSet")
    public void isBookingAvailable(LocalDate fromDate, LocalDate toDate, boolean result) throws Exception {
        BookingDetail bookingDetail = new BookingDetail(new Car(), LocalDate.of(2016, 9, 22), LocalDate.of(2016, 9, 25));

        assertEquals(result, carBookingDetailImpl.isBookingAvailable(fromDate, toDate, bookingDetail));
    }

    public Object[][] testSet() {
        return new Object[][]{{LocalDate.of(2016, 9, 23), LocalDate.of(2016, 9, 27), false},
                {LocalDate.of(2016, 9, 20), LocalDate.of(2016, 9, 23), false},
                {LocalDate.of(2016, 9, 20), LocalDate.of(2016, 9, 27), false},
                {LocalDate.of(2016, 9, 23), LocalDate.of(2016, 9, 24), false},
                {LocalDate.of(2016, 9, 20), LocalDate.of(2016, 9, 21), true},
                {LocalDate.of(2016, 9, 26), LocalDate.of(2016, 9, 28), true}};
    }


}