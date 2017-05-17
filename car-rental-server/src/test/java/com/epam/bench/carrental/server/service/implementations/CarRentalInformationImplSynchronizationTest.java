package com.epam.bench.carrental.server.service.implementations;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.epam.bench.carrental.commons.beans.CarDTO;
import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.beans.RentalClassDTO;
import com.epam.bench.carrental.commons.services.CarRentalDetailService;
import com.epam.bench.carrental.commons.services.CustomerService;
import com.epam.bench.carrental.commons.services.FleetService;
import com.epam.bench.carrental.commons.services.RentalClassService;
import com.epam.bench.carrental.server.configurations.ParentSpringConfigurations;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ParentSpringConfigurations.class, loader=AnnotationConfigContextLoader.class)
public class CarRentalInformationImplSynchronizationTest {
	@Autowired
	CarRentalDetailService carRentalInformationImpl;
	@Autowired
	FleetService fleetServiceImpl;
	@Autowired
	CustomerService customerServiceImpl;
	@Autowired
	RentalClassService rentalClassServiceImpl;

	@Before
	public void insertCustomerAndCar() {
		RentalClassDTO rentalClass = new RentalClassDTO("economy", 1.0);
		rentalClassServiceImpl.addRentalClass(rentalClass);
		rentalClass = rentalClassServiceImpl.getRentalClasses().get(0);
		CarDTO carDto = new CarDTO("Mehran", "LEC", rentalClass);
		fleetServiceImpl.addCar(carDto);
		carDto = new CarDTO("Ravi", "dfgsdf", rentalClass);
		fleetServiceImpl.addCar(carDto);
		CustomerDTO customerDto = new CustomerDTO("Ali", "aliyounas44@yahoo.com");
		customerServiceImpl.addCustomer(customerDto);
		customerDto = new CustomerDTO("Younas", "email@yahoo.com");
		customerServiceImpl.addCustomer(customerDto);
	}

	@Test
	public void carCanOnlyBeRentToASingleCustomerAtATime() {
		final CountDownLatch latch = new CountDownLatch(2);

		new Thread(() -> {
			rentCar(1, 1);
			latch.countDown();
		}).start();

		new Thread(() -> {
			rentCar(1, 2);
			latch.countDown();
		}).start();

		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(carRentalInformationImpl.getAllRentalInformation().size(), 1);
	}

	private void rentCar(int carId, int customerId) {
		try {
			carRentalInformationImpl.rentCar(carId, customerId, LocalDateTime.now());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
