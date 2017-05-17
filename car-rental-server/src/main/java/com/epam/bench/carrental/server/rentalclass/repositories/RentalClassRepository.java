package com.epam.bench.carrental.server.rentalclass.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.bench.carrental.server.rentalclass.entities.RentalClass;

@Repository
public interface RentalClassRepository extends CrudRepository<RentalClass, Integer> {
}