package com.epam.bench.carrental.server.report.calculater;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;
import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;
import com.epam.bench.carrental.server.car.entities.Car;
import com.epam.bench.carrental.server.carrentaldetail.entities.CarRentalDetail;
import com.epam.bench.carrental.server.carrentaldetail.service.implementations.RentalDetailsFetcher;
import com.epam.bench.carrental.server.rentalclass.entities.RentalClass;
import com.epam.bench.carrental.server.report.earnings.calculator.EarningsCalculator;

@RunWith(MockitoJUnitRunner.class)
public class EarningsCalculatorTest {
    @Mock
    RentalDetailsFetcher rentalDetailFetcher;
    @InjectMocks
    EarningsCalculator earningsCalculator;

    List<CarRentalDetailDTO> listCarRentalDetailDTO;
    CustomerDTO customerDTO;
    Car car;
    CarDTO carDTO;
    CarRentalDetailDTO carRentalDetailDTO;
    CarRentalDetail carRentalDetail;
    List<CarDTO> fleet;

    @Before
    public void setUp() {
        car = new Car("Mehran", "LEC", false, new RentalClass("Economy", 2));
        carDTO = new CarDTO("Mehran", "LEC", new RentalClassDTO("Economy", 2));
        customerDTO = new CustomerDTO("Ali", "aliyounas44@yahoo.com");
        listCarRentalDetailDTO = new ArrayList<>();
        fleet = new ArrayList<>();
    }

    @Test
    public void shouldCalculateEarning() {
        buildCarRentalDetail(LocalDateTime.of(2016, 06, 22, 9, 00), LocalDateTime.of(2016, 06, 23, 9, 00));
        listCarRentalDetailDTO.add(carRentalDetailDTO);
        Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 24), false)).thenReturn(listCarRentalDetailDTO);

        Assert.assertEquals(Double.valueOf(48), earningsCalculator.calculateEarning(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 24)).get("Economy"));
    }

    @Test
    public void shouldCalculateEarningFromHoursInRange() {
        buildCarRentalDetail(LocalDateTime.of(2016, 06, 20, 9, 00), LocalDateTime.of(2016, 06, 22, 9, 00));

        listCarRentalDetailDTO.add(carRentalDetailDTO);
        Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23), false)).thenReturn(listCarRentalDetailDTO);

        Assert.assertEquals(Double.valueOf(96), earningsCalculator.calculateEarning(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23)).get("Economy"));
    }

    @Test
    public void shouldCalculateEarningRentalClassWise() {
        buildCarRentalDetail(LocalDateTime.of(2016, 06, 20, 9, 00), LocalDateTime.of(2016, 06, 22, 9, 00));

        carDTO = new CarDTO("Mehran", "LEC", new RentalClassDTO("SUV", 4));
        CarRentalDetailDTO carRentalDetailDTO1 = new CarRentalDetailDTO(carDTO, customerDTO, LocalDateTime.of(2016, 06, 20, 9, 00), LocalDateTime.of(2016, 06, 22, 9, 00));

        listCarRentalDetailDTO.add(carRentalDetailDTO);
        listCarRentalDetailDTO.add(carRentalDetailDTO1);
        Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23), false)).thenReturn(listCarRentalDetailDTO);

        Assert.assertEquals(Double.valueOf(192), earningsCalculator.calculateEarning(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23)).get("SUV"));
    }

    @Test
    public void shouldCalculateTotalEarning() {
        Map<String, Double> rentalClassWiseEarning = new ConcurrentHashMap<>();
        rentalClassWiseEarning.put("Economy", 15.5);
        rentalClassWiseEarning.put("SUV", 0.5);

        Assert.assertEquals(new Double(16.0), earningsCalculator.calculateTotalEarning(rentalClassWiseEarning));
    }

    @Test
    public void shouldNotReturnNullOrThrowExceptionIfNoCarRentalIsSelectedInRange() {
        Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23), false)).thenReturn(listCarRentalDetailDTO);

        Assert.assertNotNull(earningsCalculator.calculateEarning(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23)));
    }

    @Test
    public void shouldNotReturnNullOrThrowExceptionIfNoCarRentalIsSelectedInRangeWhileCaluculatingTotalEarning() {
        Map<String, Double> rentalClassWiseEarning = new ConcurrentHashMap<>();

        Assert.assertNotNull(earningsCalculator.calculateTotalEarning(rentalClassWiseEarning));
    }

    private void buildCarRentalDetail(LocalDateTime rentalStart, LocalDateTime rentalEnd) {
        carRentalDetailDTO = new CarRentalDetailDTO(carDTO, customerDTO, rentalStart, rentalEnd);
        carRentalDetail = new CarRentalDetail(car, 1, rentalStart);
        carRentalDetail.setRentalEndDateTime(rentalEnd);
    }

}
