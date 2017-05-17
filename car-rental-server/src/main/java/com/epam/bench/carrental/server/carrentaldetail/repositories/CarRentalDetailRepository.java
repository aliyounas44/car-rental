package com.epam.bench.carrental.server.carrentaldetail.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.bench.carrental.server.carrentaldetail.entities.CarRentalDetail;

@Repository
public interface CarRentalDetailRepository extends CrudRepository<CarRentalDetail, Integer>{
}
