package com.alten.hotel.book.api.controller;

import com.alten.hotel.book.api.dto.input.ChangeReservationInputDTO;
import com.alten.hotel.book.api.dto.input.CreateReservationInputDTO;
import com.alten.hotel.book.api.model.Reservation;
import com.alten.hotel.book.api.service.ReservationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.alten.hotel.book.api.utility.Constants.ROOT_URL_RESERVATION_CONTROLLER;

/**
 * Class responsible for implementation of a set of endpoints (CRUD operations) in a REST controller referring
 * to the Reservation class.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping(value = ROOT_URL_RESERVATION_CONTROLLER)
@Api("Resource controller referring to insert, delete, update and read operations with the Reservation entity.")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * An endpoint method within the ReservationController class that creates a new reservation.
     * @param reservationDTO Data transfer class (DTO) that contains the check-in and check-out dates of the hotel guest.
     * @return Class with the output payload with the reservation data, if successful.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody CreateReservationInputDTO reservationDTO){
        return new ResponseEntity<>(reservationService.createReservation(reservationDTO), HttpStatus.CREATED);
    }

    /**
     * An endpoint method within the ReservationController class that cancel a Reservation by a given id.
     * @param id A unique number that identifies a Reservation within the database.
     * @return Class with the output payload with the canceled reservation data, if successful.
     */
    @PatchMapping(value = "/{id}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> cancelReservation(@PathVariable long id){
        return new ResponseEntity<>(reservationService.cancelReservation(id), HttpStatus.OK);
    }

    /**
     * An endpoint method within the ReservationController class that modifies part of a Reservation (checkIn and checkOut attributes).
     * @param id A unique number that identifies a Reservation within the database.
     * @param changeReservationInputDTO Data transfer class (DTO) that contains the check-in and check-out dates of the hotel guest.
     * @return Class with the output payload with the modified reservation data, if successful.
     */
    @PatchMapping(value = "/{id}/modify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> modifyReservation(@PathVariable long id,
                                                         @Valid @RequestBody ChangeReservationInputDTO changeReservationInputDTO){
        return new ResponseEntity<>(reservationService.modifyReservation(id, changeReservationInputDTO), HttpStatus.OK);
    }

}
