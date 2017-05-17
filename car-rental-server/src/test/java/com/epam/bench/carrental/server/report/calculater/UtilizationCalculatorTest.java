package com.epam.bench.carrental.server.report.calculater;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.modelmapper.ModelMapper;
import org.powermock.reflect.Whitebox;

import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;
import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;
import com.epam.bench.carrental.server.car.entities.Car;
import com.epam.bench.carrental.server.car.service.implementations.FleetServiceImpl;
import com.epam.bench.carrental.server.carrentaldetail.entities.CarRentalDetail;
import com.epam.bench.carrental.server.carrentaldetail.service.implementations.RentalDetailsFetcher;
import com.epam.bench.carrental.server.rentalclass.entities.RentalClass;
import com.epam.bench.carrental.server.report.utilization.calculator.UtilizationCalculator;

@RunWith(MockitoJUnitRunner.class)
public class UtilizationCalculatorTest {

    @Mock
    RentalDetailsFetcher rentalDetailFetcher;
    @Mock
    ModelMapper modelMapper;
    @Mock
    FleetServiceImpl fleetServiceImpl;
    @InjectMocks
    UtilizationCalculator utilizationCalculator;

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
    public void shouldCalculateUtilization() {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 22, 9, 00), LocalDateTime.of(2016, 06, 23, 9, 00));

	mapObjects(carRentalDetailDTO, carRentalDetail);

	fleet.add(carDTO);
	Mockito.when(fleetServiceImpl.getFleet()).thenReturn(fleet);

	listCarRentalDetailDTO.add(carRentalDetailDTO);
	Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23), false)).thenReturn(listCarRentalDetailDTO);

	Assert.assertEquals(Double.valueOf(12), utilizationCalculator.calculateUtilization(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23)).get("Economy"));
    }
    
    @Test
    public void shouldNotThrowExceptionInCaseIfNoRentalIsInRangeWhileCalculatingUtilization() {
	fleet.add(carDTO);
	Mockito.when(fleetServiceImpl.getFleet()).thenReturn(fleet);
	Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23), false)).thenReturn(listCarRentalDetailDTO);
	
	Assert.assertEquals(0, utilizationCalculator.calculateUtilization(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23)).size());
    }
    
    @Test
    public void shouldNotReturnNullInCaseIfNoRentalIsInRangeWhileCalculatingUtilization() {
	fleet.add(carDTO);
	Mockito.when(fleetServiceImpl.getFleet()).thenReturn(fleet);
	Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23), false)).thenReturn(listCarRentalDetailDTO);
	
	Assert.assertNotNull(utilizationCalculator.calculateUtilization(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23)).size());
    }
    
    @Test
    public void shouldReturnMapWithDaysOfWeekIfNoRentalIsInRange() {
	Assert.assertEquals(7, utilizationCalculator.calculateUtilizationForDaysOfWeek(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23)).size());
    }

    @Test
    public void shouldCalculateUtilizationOfOneDay() {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 21, 9, 00), LocalDateTime.of(2016, 06, 21, 22, 00));

	mapObjects(carRentalDetailDTO, carRentalDetail);

	fleet.add(carDTO);
	Mockito.when(fleetServiceImpl.getFleet()).thenReturn(fleet);

	listCarRentalDetailDTO.add(carRentalDetailDTO);
	Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 21), false)).thenReturn(listCarRentalDetailDTO);

	Assert.assertEquals(Double.valueOf(13), utilizationCalculator.calculateUtilization(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 21)).get("Economy"));
    }

    @Test
    public void shouldCalculateUtilizationRentalClassWise() {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 22, 9, 00), LocalDateTime.of(2016, 06, 22, 13, 00));

	fleet.add(carDTO);

	carDTO = new CarDTO("Mehran", "LEF", new RentalClassDTO("SUV", 2));
	car = new Car("Mehran", "LEF", false, new RentalClass("SUV", 2));
	CarRentalDetailDTO carRentalDetailDTO1 = new CarRentalDetailDTO(carDTO, customerDTO, LocalDateTime.of(2016, 06, 22, 9, 00), LocalDateTime.of(2016, 06, 22, 12, 00));
	CarRentalDetail carRentalDetail1 = new CarRentalDetail(car, 1, LocalDateTime.of(2016, 06, 22, 9, 00));
	carRentalDetail1.setRentalEndDateTime(LocalDateTime.of(2016, 06, 22, 12, 00));

	mapObjects(carRentalDetailDTO, carRentalDetail);
	mapObjects(carRentalDetailDTO1, carRentalDetail1);

	fleet.add(carDTO);
	Mockito.when(fleetServiceImpl.getFleet()).thenReturn(fleet);

	listCarRentalDetailDTO.add(carRentalDetailDTO);
	listCarRentalDetailDTO.add(carRentalDetailDTO1);
	Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 24), false)).thenReturn(listCarRentalDetailDTO);

	Assert.assertEquals(Double.valueOf(1), utilizationCalculator.calculateUtilization(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 24)).get("SUV"));
    }

    private void buildCarRentalDetail(LocalDateTime rentalStart, LocalDateTime rentalEnd) {
	carRentalDetailDTO = new CarRentalDetailDTO(carDTO, customerDTO, rentalStart, rentalEnd);
	carRentalDetail = new CarRentalDetail(car, 1, rentalStart);
	carRentalDetail.setRentalEndDateTime(rentalEnd);
    }

    @Test
    public void shouldCalculateTotalUtilization() {
	Map<String, Double> rentalClassWiseUtilization = new ConcurrentHashMap<>();
	rentalClassWiseUtilization.put("Economy", 15.5);
	rentalClassWiseUtilization.put("SUV", 0.5);
	rentalClassWiseUtilization.put("Elite", 5.0);

	Assert.assertEquals(new Double(7), utilizationCalculator.calculateTotalUtilization(rentalClassWiseUtilization));
    }

    @Test
    public void shouldCalculateUtilizationForDaysOfWeek() {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 22, 9, 00), LocalDateTime.of(2016, 06, 22, 12, 00));

	CarDTO carDto = new CarDTO("cultus", "Lef", new RentalClassDTO("SUV", 4));
	Car car1 = new Car("cultus", "Lef", false, new RentalClass("SUV", 4));
	CarRentalDetailDTO carRentalDetailDTO1 = new CarRentalDetailDTO(carDto, customerDTO, LocalDateTime.of(2016, 06, 23, 9, 00), LocalDateTime.of(2016, 06, 23, 12, 00));
	CarRentalDetail carRentalDetail1 = new CarRentalDetail(car1, 1, LocalDateTime.of(2016, 06, 23, 9, 00));
	carRentalDetail1.setRentalEndDateTime(LocalDateTime.of(2016, 06, 23, 12, 00));

	mapObjects(carRentalDetailDTO, carRentalDetail);
	mapObjects(carRentalDetailDTO1, carRentalDetail1);

	fleet.add(carDTO);
	fleet.add(carDto);
	Mockito.when(fleetServiceImpl.getFleet()).thenReturn(fleet);

	listCarRentalDetailDTO.add(carRentalDetailDTO);
	listCarRentalDetailDTO.add(carRentalDetailDTO1);
	Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 22), LocalDate.of(2016, 06, 22), false)).thenReturn(listCarRentalDetailDTO);
	//	Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 23), LocalDate.of(2016, 06, 23))).thenReturn(listCarRentalDetailDTO);

	//	utilizationCalculator.calculateUtilizationForDaysOfWeek(LocalDate.of(2016, 06, 22), LocalDate.of(2016, 06, 22)).forEach(s -> System.out.println(s));
	Assert.assertEquals(new Double(3), utilizationCalculator.calculateUtilizationForDaysOfWeek(LocalDate.of(2016, 06, 22), LocalDate.of(2016, 06, 22)).get(2).get("Economy"));
    }

    @Test
    public void shouldCalculateUtilizationForDaysOfWeekForDifferentClasses() {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 22, 9, 00), LocalDateTime.of(2016, 06, 22, 12, 00));

	CarDTO carDto = new CarDTO("cultus", "Lef", new RentalClassDTO("SUV", 4));
	Car car1 = new Car("cultus", "Lef", false, new RentalClass("SUV", 4));
	CarRentalDetailDTO carRentalDetailDTO1 = new CarRentalDetailDTO(carDto, customerDTO, LocalDateTime.of(2016, 06, 23, 9, 00), LocalDateTime.of(2016, 06, 23, 12, 00));
	CarRentalDetail carRentalDetail1 = new CarRentalDetail(car1, 1, LocalDateTime.of(2016, 06, 23, 9, 00));
	carRentalDetail1.setRentalEndDateTime(LocalDateTime.of(2016, 06, 23, 12, 00));

	mapObjects(carRentalDetailDTO, carRentalDetail);
	mapObjects(carRentalDetailDTO1, carRentalDetail1);

	fleet.add(carDTO);
	fleet.add(carDto);
	Mockito.when(fleetServiceImpl.getFleet()).thenReturn(fleet);

	listCarRentalDetailDTO.add(carRentalDetailDTO);
	List<CarRentalDetailDTO> listCarRentalDetailDTO1 = new ArrayList<>();
	listCarRentalDetailDTO1.add(carRentalDetailDTO1);
	Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 22), LocalDate.of(2016, 06, 22), false)).thenReturn(listCarRentalDetailDTO);
	Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 23), LocalDate.of(2016, 06, 23), false)).thenReturn(listCarRentalDetailDTO1);

	utilizationCalculator.calculateUtilizationForDaysOfWeek(LocalDate.of(2016, 06, 22), LocalDate.of(2016, 06, 23)).forEach(s -> System.out.println(s));
    }

    @Test
    public void shouldAccumulateUtilizationForMondays() throws Exception {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 22, 9, 00), LocalDateTime.of(2016, 06, 22, 12, 00));

	CarDTO carDto = new CarDTO("cultus", "Lef", new RentalClassDTO("SUV", 4));
	Car car1 = new Car("cultus", "Lef", false, new RentalClass("SUV", 4));
	CarRentalDetailDTO carRentalDetailDTO1 = new CarRentalDetailDTO(carDto, customerDTO, LocalDateTime.of(2016, 06, 23, 9, 00), LocalDateTime.of(2016, 06, 23, 12, 00));
	CarRentalDetail carRentalDetail1 = new CarRentalDetail(car1, 1, LocalDateTime.of(2016, 06, 23, 9, 00));
	carRentalDetail1.setRentalEndDateTime(LocalDateTime.of(2016, 06, 23, 12, 00));

	mapObjects(carRentalDetailDTO, carRentalDetail);
	mapObjects(carRentalDetailDTO1, carRentalDetail1);

	fleet.add(carDTO);
	fleet.add(carDto);
	Mockito.when(fleetServiceImpl.getFleet()).thenReturn(fleet);

	listCarRentalDetailDTO.add(carRentalDetailDTO);
	List<CarRentalDetailDTO> listCarRentalDetailDTO1 = new ArrayList<>();
	listCarRentalDetailDTO1.add(carRentalDetailDTO1);
	Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 22), LocalDate.of(2016, 06, 22), false)).thenReturn(listCarRentalDetailDTO);
	Mockito.when(rentalDetailFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 23), LocalDate.of(2016, 06, 23), false)).thenReturn(listCarRentalDetailDTO1);

	List<LocalDate> dates = new ArrayList<>();
	dates.add(LocalDate.of(2016, 06, 22));
	dates.add(LocalDate.of(2016, 06, 23));
	Assert.assertEquals(new Double(3), ((Map<String, Double>) Whitebox.invokeMethod(utilizationCalculator, "calculateUtilizationRentalClassWiseForSingleDayOfWeek", dates)).get("SUV"));
    }
    
    @Test
    public void shouldMergeTwoMaps() throws Exception {
	Map<String, Double> util = new HashMap<>();
	util.put("Economy", 2.0);
	util.put("SUV", 3.0);
	Map<String, Double> util2 = new HashMap<>();
	util2.put("Economy", 2.0);
	util2.put("ABC", 3.0);
	
	Assert.assertEquals(3, ((Map<String, Double>) Whitebox.invokeMethod(utilizationCalculator, "accumulateUtilizations", util, util2)).size());
    }
    
    @Test
    public void shouldAddValuesOfSameKeysWhileMergingMap() throws Exception {
	Map<String, Double> util = new HashMap<>();
	util.put("Economy", 2.0);
	util.put("SUV", 3.0);
	Map<String, Double> util2 = new HashMap<>();
	util2.put("Economy", 2.0);
	util2.put("ABC", 3.0);
	
	Assert.assertEquals(new Double(4.0), ((Map<String, Double>) Whitebox.invokeMethod(utilizationCalculator, "accumulateUtilizations", util, util2)).get("Economy"));
    }

    private void mapObjects(CarRentalDetailDTO carRentalDetailDTO, CarRentalDetail carRentalDetail) {
	Mockito.when(modelMapper.map(carRentalDetailDTO, CarRentalDetail.class)).thenReturn(carRentalDetail);
	Mockito.when(modelMapper.map(carRentalDetail, CarRentalDetailDTO.class)).thenReturn(carRentalDetailDTO);
    }

}
