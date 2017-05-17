package com.epam.bench.carrental.server.booking.data.generator;

import com.epam.bench.carrental.commons.services.CarBookingDetailService;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class BookingGenerator {
    @Autowired
    DataFactory dataFactory;
    @Autowired
    CarBookingDetailService carBookingDetailImpl;

    private final int MIN_CAR_ID = 1;
    private final int MAX_CAR_ID = 100;
    private final int MIN_CUSTOMER_ID = 1;
    private final int MAX_CUSTOMER_ID = 100;
    private final int HALF_NO_OF_DAYS_IN_MONTH = 6;
    private final int GAP_PERIOD = 1;
    private final int TOTAL_BOOKINGS_PER_CAR = 10;

    int counter = 0;
    public void generateBookings() {
        LocalDate startDate = LocalDate.now().plusDays(GAP_PERIOD * 6);
        LocalDate endDate;

        for (int j = 1; j < TOTAL_BOOKINGS_PER_CAR; j++) {
            int totalNoOfCarsPerIteration = dataFactory.getNumberBetween(MIN_CAR_ID, MAX_CAR_ID);

            for (int i = 1; i <= totalNoOfCarsPerIteration; i++) {
                startDate = toLocalDate(dataFactory.getDateBetween(toDate(startDate.plusDays(HALF_NO_OF_DAYS_IN_MONTH * (j-1) )), toDate(startDate.plusDays(HALF_NO_OF_DAYS_IN_MONTH * (j) ))));
                endDate = toLocalDate(dataFactory.getDateBetween(toDate(startDate), toDate(startDate.plusDays(HALF_NO_OF_DAYS_IN_MONTH * (j+1) ))));
                carBookingDetailImpl.bookCar(i, dataFactory.getNumberBetween(MIN_CUSTOMER_ID, MAX_CUSTOMER_ID), startDate, endDate);
                counter++;
            }
            startDate = startDate.plusDays(HALF_NO_OF_DAYS_IN_MONTH * (j+1)).plusDays(GAP_PERIOD);
        }
        System.out.println("Number of bookings : "+ counter);
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
