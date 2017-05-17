package com.epam.bench.carrental.server.service.implementations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CarRentalDetailDTO;
import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;
import com.epam.bench.carrental.server.car.entities.Car;
import com.epam.bench.carrental.server.car.repositories.FleetRepository;
import com.epam.bench.carrental.server.car.service.implementations.FleetServiceImpl;
import com.epam.bench.carrental.server.carrentaldetail.entities.CarRentalDetail;
import com.epam.bench.carrental.server.carrentaldetail.repositories.CarRentalDetailRepository;
import com.epam.bench.carrental.server.carrentaldetail.service.implementations.CarRentalInformationImpl;
import com.epam.bench.carrental.server.rentalclass.entities.RentalClass;
import com.epam.bench.carrental.server.time.CustomizedClock;
import org.modelmapper.internal.cglib.core.Local;

@RunWith(MockitoJUnitRunner.class)
public class CarRentalInformationImplTest {

    @Mock
    CarRentalDetailRepository carRentalInformationRepository;
    @Mock
    FleetRepository fleetRepository;
    @Mock
    FleetServiceImpl fleetServiceImpl;
    @Mock
    ModelMapper modelMapper;
    @Mock
    CustomizedClock customizedClock;
    @InjectMocks
    CarRentalInformationImpl carRentalInformationImpl;

    List<Car> fleet;
    Car availableCar;
    Car rentedCar;

    private final int AVAILABLE_CAR_ID = 1;
    private final int RENTED_CAR_ID = 2;

    @Before
    public void init() {
        availableCar = new Car("model", "LEX", false, new RentalClass(), AVAILABLE_CAR_ID);
        rentedCar = new Car("model", "LEC", true, new RentalClass(), RENTED_CAR_ID);
        fleet = new ArrayList<>();
        fleet.add(availableCar);
        fleet.add(rentedCar);

        when(fleetRepository.findAll()).thenReturn(fleet);
    }

    @Test(expected=RuntimeException.class)
    public void canNotRentAlreadyRentedCar() {
        carRentalInformationImpl.rentCar(2, 1, LocalDateTime.now());
    }

    @Test
    public void canRentACar() {
        CarRentalDetail carRentalDetail = new CarRentalDetail(availableCar, AVAILABLE_CAR_ID, LocalDateTime.now());
        List<CarRentalDetail> carRentalDetails = new ArrayList<>();
        carRentalDetails.add(carRentalDetail);

        when(carRentalInformationRepository.save(any(CarRentalDetail.class))).thenReturn(carRentalDetail);
        when(carRentalInformationRepository.findAll()).thenReturn(carRentalDetails);
        when(customizedClock.now()).thenReturn(LocalDateTime.now());

        carRentalInformationImpl.rentCar(1, 1, LocalDateTime.now());
        List<CarRentalDetail> givenCarRentalDetails = StreamSupport.stream(carRentalInformationRepository.findAll().spliterator(), true).filter(rentalDetail -> rentalDetail.getCar().getId() == 1).collect(Collectors.toList());
        Assert.assertEquals(true, givenCarRentalDetails.get(0).getCar().isRented());
    }

    @Test(expected=RuntimeException.class)
    public void canNotReturnAvailableCar() {
        carRentalInformationImpl.returnCar(1);
    }

    @Test
    public void canReturnACar() {
        CarRentalDetail carRentalDetail = new CarRentalDetail(rentedCar, RENTED_CAR_ID, LocalDateTime.now());
        List<CarRentalDetail> carRentalDetails = new ArrayList<>();
        carRentalDetails.add(carRentalDetail);

        when(carRentalInformationRepository.findAll()).thenReturn(carRentalDetails);
        when(customizedClock.now()).thenReturn(LocalDateTime.now());

        carRentalInformationImpl.returnCar(2);
        List<CarRentalDetail> givenCarRentalDetails = StreamSupport.stream(carRentalInformationRepository.findAll().spliterator(), true).filter(rentalDetail -> rentalDetail.getCar().getId() == 2).collect(Collectors.toList());
        Assert.assertNotNull(givenCarRentalDetails.get(0).getRentalEndDateTime());
        //		Assert.assertEquals(fleet.get(1).isRented(), false);
    }

    @Test
    public void canFetchAvailableCarsFromTheFleet() {
        List<CarDTO> fleet = prepareFleet();

        when(fleetServiceImpl.getFleet()).thenReturn(fleet);
        when(carRentalInformationRepository.findAll()).thenReturn(new ArrayList<>());

        Assert.assertEquals(1, carRentalInformationImpl.getAvailableCars("", LocalDateTime.now()).get(0).getId());
    }

    private List<CarDTO> prepareFleet() {
        CarDTO car = new CarDTO("Mehran", "LEC", new RentalClassDTO(), 1);
        List<CarDTO> fleet = new ArrayList<>();
        fleet.add(car);
        return fleet;
    }

    @Test
    public void canFetchRentalInformationOfRentedCars() {
        CarRentalDetail carRentalDetail = new CarRentalDetail(new Car(), 1, LocalDateTime.now());
        List<CarRentalDetail> carRentalInformation = new ArrayList<>();
        carRentalInformation.add(carRentalDetail);

        when(carRentalInformationRepository.findAll()).thenReturn(carRentalInformation);
        when(modelMapper.map(carRentalDetail, CarRentalDetailDTO.class)).thenReturn(new CarRentalDetailDTO(new CarDTO(), new CustomerDTO(), LocalDateTime.now(), null));

        Assert.assertEquals(1, carRentalInformationImpl.getRentedCarsWithCustomer().size());
    }
}
