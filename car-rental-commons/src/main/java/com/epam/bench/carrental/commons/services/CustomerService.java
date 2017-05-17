package com.epam.bench.carrental.commons.services;

import java.util.List;

import com.epam.bench.carrental.commons.beans.CustomerDTO;

public interface CustomerService {
    public void addCustomer(CustomerDTO customer);
    public List<CustomerDTO> getCustomer();
}
