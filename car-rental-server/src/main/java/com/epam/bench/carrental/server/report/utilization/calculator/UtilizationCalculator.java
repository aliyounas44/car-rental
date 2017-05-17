package com.epam.bench.carrental.server.report.utilization.calculator;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;
import com.epam.bench.carrental.commons.services.FleetService;
import com.epam.bench.carrental.server.carrentaldetail.service.implementations.RentalDetailsFetcher;

@Component
public class UtilizationCalculator {
	@Autowired
	FleetService fleetServiceImpl;
	@Autowired
	RentalDetailsFetcher rentalDetailsFetcher;

	public Double calculateTotalUtilization(Map<String, Double> rentalClassWiseUtilization) {
		return rentalClassWiseUtilization.entrySet().stream().mapToDouble(entry -> entry.getValue()).sum() / rentalClassWiseUtilization.size();
	}

	public Map<String, Double> calculateUtilization(LocalDate fromDate, LocalDate toDate) {
		Map<String, List<CarDTO>> rentalClassWiseCars = fleetServiceImpl.getFleet().stream().collect(Collectors.groupingBy(s -> s.getRentalClassDto().getName()));

		Map<String, Double> rentalClassWiseUtilizationOfCarsPerDay = rentalDetailsFetcher.fetchRentalDetailsInSpecifiedDateRange(fromDate, toDate, false).stream().collect(
				Collectors.groupingBy(s -> s.getCar().getRentalClassDto().getName(),
						Collectors.summingDouble(s -> ((1.0) * Duration.between(s.getRentalStartDateTime(), s.getRentalEndDateTime()).toHours()) /
								(Duration.between(fromDate.atStartOfDay(), toDate.atStartOfDay()).toDays() == 0 ? 1 : Duration.between(fromDate.atStartOfDay(), toDate.atStartOfDay()).toDays()))));

		return rentalClassWiseUtilizationOfCarsPerDay.entrySet().parallelStream().collect(
				Collectors.toMap(s -> s.getKey(), s -> Double.parseDouble(s.getValue().toString()) / rentalClassWiseCars.get(s.getKey()).size()));
	}

	private List<LocalDate> getDatesForDayOfWeekInRange(LocalDate dateFrom, LocalDate dateTo, DayOfWeek dayOfWeek) {
		return Stream.iterate(dateFrom, date -> date.plusDays(1)).limit(ChronoUnit.DAYS.between(dateFrom, dateTo) + 1).filter(date -> date.getDayOfWeek().equals(dayOfWeek)).collect(Collectors.toCollection(ArrayList::new));
	}

	private List<List<LocalDate>> getDayOfWeekWiseDates(LocalDate dateFrom, LocalDate dateTo) {
		return Stream.iterate(1, dayOfWeek -> dayOfWeek + 1).limit(7).map(dayOfWeek -> getDatesForDayOfWeekInRange(dateFrom, dateTo, DayOfWeek.of(dayOfWeek))).collect(Collectors.toCollection(ArrayList::new));
	}

	public List<Map<String, Double>> calculateUtilizationForDaysOfWeek(LocalDate dateFrom, LocalDate dateTo) {
		List<List<LocalDate>> dayOfWeekWiseDates = getDayOfWeekWiseDates(dateFrom, dateTo);
		return dayOfWeekWiseDates.stream().map(
				this::calculateUtilizationRentalClassWiseForSingleDayOfWeek).collect(Collectors.toList());
	}

	private Map<String, Double> calculateUtilizationRentalClassWiseForSingleDayOfWeek(List<LocalDate> listOfDatesOfSingleDayOfWeek) {
		List<Map<String, Double>> rentalWiseUtilizationForSingleDayOfWeek = listOfDatesOfSingleDayOfWeek.stream().map(date -> calculateUtilization(date, date)).collect(Collectors.toList());
		Map<String, Double> baseMap = new HashMap<>();
		if (!rentalWiseUtilizationForSingleDayOfWeek.isEmpty()) {
			baseMap = rentalWiseUtilizationForSingleDayOfWeek.get(0);
			for (int i = 1; i < rentalWiseUtilizationForSingleDayOfWeek.size(); i++) {
				baseMap = accumulateUtilizations(baseMap, rentalWiseUtilizationForSingleDayOfWeek.get(i));
			}
		}
		return baseMap;
	}

	private Map<String, Double> accumulateUtilizations(Map<String, Double> utilization1, Map<String, Double> utilization2) {
		return Stream.concat(utilization1.entrySet().stream(), utilization2.entrySet().stream())
				.collect(Collectors.groupingBy(s -> ((Entry<String, Double>) s).getKey(), Collectors.summingDouble(s -> ((Entry<String, Double>) s).getValue())));
	}
}
