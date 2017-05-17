package com.epam.bench.carrental.server.customer.data.generator;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.services.CustomerService;

@Component
public class CustomersGenerator {

    @Autowired
    CustomerService customerServiceImpl;
    @Autowired
    DataFactory dataFactory;

    public void generateCustomers() {
	for (int i = 0; i < 100; i++) {
	    customerServiceImpl.addCustomer(new CustomerDTO(dataFactory.getName(), dataFactory.getEmailAddress()));
	}
    }
}
