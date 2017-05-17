package com.epam.bench.carrental.server.carrentaldetail.data.generator;

import java.sql.Date;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.commons.services.CarRentalDetailService;
import com.epam.bench.carrental.server.time.CustomizedClock;

@Component
public class RentalDetailsGenerator {

	@Autowired
	CarRentalDetailService carRentalInformationImpl;
	@Autowired
	DataFactory dataFactory;
	@Autowired
	CustomizedClock clock;

	private final int MIN_NUMBER_OF_RENTS_PER_CAR = 1;
	private final int MAX_NUMBER_OF_RENTS_PER_CAR = 10;
	private final int MIN_CAR_ID = 1;
	private final int MAX_CAR_ID = 100;
	private final int MIN_CUSTOMER_ID = 1;
	private final int MAX_CUSTOMER_ID = 100;
	private final int HALF_NO_OF_DAYS_IN_MONTH = 6;
	private final int TOTAL_RENTALS_PER_CAR = 10;

	int counter = 0;

	public void generateRentalDetails() {
		for (int j = 2; j < TOTAL_RENTALS_PER_CAR; j++) {
			int totalNoOfCarsPerIteration = dataFactory.getNumberBetween(MIN_CAR_ID, MAX_CAR_ID);
			for (int i = 1; i <= totalNoOfCarsPerIteration; i++) {
				clock.setClock(Clock.fixed(dataFactory.getDateBetween(
						Date.from(LocalDate.now().minusDays(HALF_NO_OF_DAYS_IN_MONTH * (j + 1)).atStartOfDay(ZoneId.systemDefault()).toInstant()),
						Date.from(LocalDate.now().minusDays(HALF_NO_OF_DAYS_IN_MONTH * j).atStartOfDay(ZoneId.systemDefault()).toInstant())).toInstant(), ZoneId.systemDefault()));
				counter++;
				carRentalInformationImpl.rentCar(i, dataFactory.getNumberBetween(MIN_CUSTOMER_ID, MAX_CUSTOMER_ID), (j == 1) ? LocalDateTime.now().plusDays(dataFactory.getNumberBetween(1, 5)) : clock.now().plusDays(2));
				returnCar(i, j);
			}
		}

		generateCurrentRentalDetails();
		System.out.println("Number of rental details "+counter);
	}

	private void generateCurrentRentalDetails(){
		int currentRental = 0;
		int totalNoOfCarsPerIteration = dataFactory.getNumberBetween(MIN_CAR_ID, MAX_CAR_ID);
		for (int i = 1; i <= totalNoOfCarsPerIteration; i++) {
			clock.setClock(Clock.fixed(dataFactory.getDateBetween(
					Date.from(LocalDate.now().minusDays(HALF_NO_OF_DAYS_IN_MONTH * 2).atStartOfDay(ZoneId.systemDefault()).toInstant()),
					Date.from(LocalDate.now().minusDays(HALF_NO_OF_DAYS_IN_MONTH ).atStartOfDay(ZoneId.systemDefault()).toInstant())).toInstant(), ZoneId.systemDefault()));
			carRentalInformationImpl.rentCar(i, dataFactory.getNumberBetween(MIN_CUSTOMER_ID, MAX_CUSTOMER_ID), LocalDateTime.now().plusDays(dataFactory.getNumberBetween(1, 5)));
			currentRental++;
		}
		System.out.println("Current Rentals "+currentRental);
	}

	private void returnCar(int carId, int gapInDays) {
		clock.reset();
		clock.setClock(Clock.fixed(dataFactory.getDateBetween(
				Date.from(LocalDate.now().minusDays(HALF_NO_OF_DAYS_IN_MONTH * gapInDays).atStartOfDay(ZoneId.systemDefault()).toInstant()),
				Date.from(LocalDate.now().minusDays(HALF_NO_OF_DAYS_IN_MONTH * (gapInDays - 1)).atStartOfDay(ZoneId.systemDefault()).toInstant())).toInstant(), ZoneId.systemDefault()));
		carRentalInformationImpl.returnCar(carId);
		clock.reset();
	}
}