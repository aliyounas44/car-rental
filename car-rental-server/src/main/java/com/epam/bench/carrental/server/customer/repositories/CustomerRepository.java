package com.epam.bench.carrental.server.customer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.bench.carrental.server.customer.entities.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
