package com.epam.bench.carrental.server.booking.repositories;

import com.epam.bench.carrental.server.booking.entites.BookingDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<BookingDetail, Integer> {
}
