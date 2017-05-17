package com.epam.bench.carrental.server.report.service.implementations;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.bench.carrental.commons.beans.ReportDTO;
import com.epam.bench.carrental.commons.services.FleetService;
import com.epam.bench.carrental.commons.services.ReportingService;
import com.epam.bench.carrental.server.carrentaldetail.service.implementations.RentalDetailsFetcher;
import com.epam.bench.carrental.server.report.beans.Report;
import com.epam.bench.carrental.server.report.earnings.calculator.EarningsCalculator;
import com.epam.bench.carrental.server.report.utilization.calculator.UtilizationCalculator;

public class ReportingServiceImpl implements ReportingService {
    @Autowired
    FleetService fleetServiceImpl;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RentalDetailsFetcher rentalDetailsFetcher;
    @Autowired
    UtilizationCalculator utilizationCalculator;
    @Autowired
    EarningsCalculator earningsCalculator;

    @Override
    public ReportDTO fetchReport(LocalDate fromDate, LocalDate toDate) {
	Map<String, Double> rentalClassWiseEarning = earningsCalculator.calculateEarning(fromDate, toDate);
	double totalEarning = earningsCalculator.calculateTotalEarning(rentalClassWiseEarning);
	Map<String, Double> rentalClassWiseUtilization = utilizationCalculator.calculateUtilization(fromDate, toDate);
	double totalUtilization = utilizationCalculator.calculateTotalUtilization(rentalClassWiseUtilization);
	Map<DayOfWeek, Map<String, Double>> dayWiseUtilization = mapListOfWeekOfDayWiseUtilization(utilizationCalculator.calculateUtilizationForDaysOfWeek(fromDate, toDate));
	
	return modelMapper.map(new Report(rentalClassWiseEarning, rentalClassWiseUtilization, totalEarning, totalUtilization, dayWiseUtilization), ReportDTO.class);
    }
    
    private Map<DayOfWeek, Map<String, Double>> mapListOfWeekOfDayWiseUtilization(List<Map<String, Double>> rentalClassWiseUtilizationPerDayOfWeek) {
	Map<DayOfWeek, Map<String, Double>> result = new HashMap<>();
	int dayOfWeek = 1;
	for (Map<String, Double> utilizationOfSingleDayOfWeek : rentalClassWiseUtilizationPerDayOfWeek) {
	    result.put(DayOfWeek.of(dayOfWeek), utilizationOfSingleDayOfWeek);
	    dayOfWeek++;
	}
	return result;
    }

}
