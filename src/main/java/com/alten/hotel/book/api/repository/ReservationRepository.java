package com.alten.hotel.book.api.repository;

import com.alten.hotel.book.api.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    @Query("SELECT res.id FROM Reservation res" +
            " INNER JOIN res.room r" +
            " WHERE r.id = :roomId" +
            " AND (:checkIn BETWEEN res.checkIn AND res.checkOut" +
            " OR :checkOut BETWEEN res.checkIn AND res.checkOut)" +
            " AND res.isReserved is true")
    Set<Long> findReservationsBetweenCheckInAndCheckOut(long roomId, LocalDate checkIn, LocalDate checkOut);

    Reservation findByIdAndIsReserved(long id, boolean reserved);
}
