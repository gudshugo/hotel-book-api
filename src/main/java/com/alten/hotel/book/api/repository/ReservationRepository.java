package com.alten.hotel.book.api.repository;

import com.alten.hotel.book.api.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {}
