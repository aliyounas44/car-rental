package com.epam.bench.carrental.server.report.calculater;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;
import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;
import com.epam.bench.carrental.commons.services.CarRentalDetailService;
import com.epam.bench.carrental.server.car.entities.Car;
import com.epam.bench.carrental.server.carrentaldetail.entities.CarRentalDetail;
import com.epam.bench.carrental.server.carrentaldetail.service.implementations.RentalDetailsFetcher;
import com.epam.bench.carrental.server.rentalclass.entities.RentalClass;

@RunWith(MockitoJUnitRunner.class)
public class RentalDetailsFetcherTest {
    @Mock
    CarRentalDetailService carRentalInformationImpl;
    @Mock
    ModelMapper modelMapper;
    @InjectMocks
    RentalDetailsFetcher rentalDetailsFetcher;

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
    public void shouldFetchRentalDetailIfRentIsEndingInDateRange() {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 20, 9, 00), LocalDateTime.of(2016, 06, 22, 9, 00));
	mockCarRentalDetail();
	
	Assert.assertEquals(1, rentalDetailsFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23), false).size());
    }

    @Test
    public void shouldFetchRentalDetailIfRentIsStartingInDateRange() {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 22, 9, 00), LocalDateTime.of(2016, 06, 24, 9, 00));
	mockCarRentalDetail();

	Assert.assertEquals(1, rentalDetailsFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 23), false).size());
    }

    @Test
    public void shouldFetchRentalDetailIfRentIsStartingAndEndingInDateRange() {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 22, 9, 00), LocalDateTime.of(2016, 06, 23, 9, 00));
	mockCarRentalDetail();

	Assert.assertEquals(1, rentalDetailsFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 24), false).size());
    }

    @Test
    public void shouldNotFetchRentalDetailIfRentIsNotInDateRange() {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 19, 9, 00), LocalDateTime.of(2016, 06, 20, 9, 00));
	mockCarRentalDetail();

	Assert.assertEquals(0, rentalDetailsFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 24), false).size());
    }
    
    @Test
    public void shouldNotReturnNullIfNoRentIsFoundInDateRange() {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 19, 9, 00), LocalDateTime.of(2016, 06, 20, 9, 00));
	mockCarRentalDetail();

	Assert.assertNotNull(rentalDetailsFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 24), false));
    }
    
    @Test
    public void shouldReturnEmptyListWhenNoRentIsFoundInDateRange() {
	buildCarRentalDetail(LocalDateTime.of(2016, 06, 19, 9, 00), LocalDateTime.of(2016, 06, 20, 9, 00));
	mockCarRentalDetail();

	Assert.assertEquals(0, rentalDetailsFetcher.fetchRentalDetailsInSpecifiedDateRange(LocalDate.of(2016, 06, 21), LocalDate.of(2016, 06, 24), false).size());
	Mockito.verify(carRentalInformationImpl).getAllRentalInformation();
    }
    
    private void mockCarRentalDetail() {
	mapObjects(carRentalDetailDTO, carRentalDetail);
	listCarRentalDetailDTO.add(carRentalDetailDTO);
	Mockito.when(carRentalInformationImpl.getAllRentalInformation()).thenReturn(listCarRentalDetailDTO);
    }
    
    private void buildCarRentalDetail(LocalDateTime rentalStart, LocalDateTime rentalEnd) {
	carRentalDetailDTO = new CarRentalDetailDTO(carDTO, customerDTO, rentalStart, rentalEnd);
	carRentalDetail = new CarRentalDetail(car, 1, rentalStart);
	carRentalDetail.setRentalEndDateTime(rentalEnd);
    }
    
    private void mapObjects(CarRentalDetailDTO carRentalDetailDTO, CarRentalDetail carRentalDetail) {
	Mockito.when(modelMapper.map(carRentalDetailDTO, CarRentalDetail.class)).thenReturn(carRentalDetail);
	Mockito.when(modelMapper.map(carRentalDetail, CarRentalDetailDTO.class)).thenReturn(carRentalDetailDTO);
    }

}
