package com.epam.bench.carrental.server.car.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.bench.carrental.server.car.entities.Car;

@Repository
public interface FleetRepository extends CrudRepository<Car, Integer> {
}
