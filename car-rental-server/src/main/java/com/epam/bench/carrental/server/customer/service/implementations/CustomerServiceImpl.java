package com.epam.bench.carrental.server.customer.service.implementations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.bench.carrental.commons.beans.CustomerDTO;
import com.epam.bench.carrental.commons.services.CustomerService;
import com.epam.bench.carrental.server.customer.entities.Customer;
import com.epam.bench.carrental.server.customer.repositories.CustomerRepository;

public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ModelMapper modelMapper;

    public void addCustomer(CustomerDTO customerDto) {
	customerRepository.save(modelMapper.map(customerDto, Customer.class));
    }

    public List<CustomerDTO> getCustomer() {
    return StreamSupport.stream(customerRepository.findAll().spliterator(), true).map(s -> modelMapper.map(s, CustomerDTO.class)).collect(Collectors.toList());
    }
}
