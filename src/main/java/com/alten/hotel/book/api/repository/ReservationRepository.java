package com.alten.hotel.book.api.repository;

import com.alten.hotel.book.api.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

/**
 * Interface with the signatures of the methods implemented using CrudRepository to retrieve, save,
 * delete and change the data referring to Reservation class.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    /**
     * Method that implements a query that retrieves reservations identifiers (reservation ids) for a room
     * given a range of dates and a room identifier (room id).
     * @param roomId A unique number that identifies a Room within the database.
     * @param checkIn Customer check-out date.
     * @param checkOut Customer check-out date.
     * @return A set with reservation identifiers (reservation ids) if found in the database.
     */

    @Query("SELECT res.id FROM Reservation res" +
            " INNER JOIN res.room r" +
            " WHERE r.id = :roomId" +
            " AND (:checkIn BETWEEN res.checkIn AND res.checkOut" +
            " OR :checkOut BETWEEN res.checkIn AND res.checkOut)" +
            " AND res.isReserved is true")
    Set<Long> findReservationsBetweenCheckInAndCheckOut(long roomId, LocalDate checkIn, LocalDate checkOut);

    /**
     * Method that implements the query of a single reservation given its id and a flag referring to
     * the room being reserved.
     * @param id A unique number that identifies a Reservation within the database.
     * @param reserved A boolean that represents whether a reservation is booked or not.
     * @return A single Reservation data output.
     */
    Reservation findByIdAndIsReserved(long id, boolean reserved);
}
