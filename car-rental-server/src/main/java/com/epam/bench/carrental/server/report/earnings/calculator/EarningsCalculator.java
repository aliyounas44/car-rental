package com.epam.bench.carrental.server.report.earnings.calculator;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;
import com.epam.bench.carrental.server.carrentaldetail.service.implementations.RentalDetailsFetcher;

@Component
public class EarningsCalculator {
    @Autowired
    RentalDetailsFetcher rentalDetailsFetcher;

    public Double calculateTotalEarning(Map<String, Double> rentalClassWiseEarning) {
        return rentalClassWiseEarning.entrySet().stream().mapToDouble(entry -> entry.getValue()).sum();
    }

    public Map<String, Double> calculateEarning(LocalDate fromDate, LocalDate toDate) {
        return rentalDetailsFetcher.fetchRentalDetailsInSpecifiedDateRange(fromDate, toDate, false).stream().collect(Collectors.groupingBy(s -> s.getCar().getRentalClassDto().getName(), Collectors.summingDouble(s -> ((CarRentalDetailDTO) s).getCar().getRentalClassDto().getHourlyRate() * Duration.between(((CarRentalDetailDTO) s).getRentalStartDateTime(), ((CarRentalDetailDTO) s).getRentalEndDateTime()).toHours())));
    }
}
