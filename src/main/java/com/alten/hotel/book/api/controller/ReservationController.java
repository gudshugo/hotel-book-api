package com.alten.hotel.book.api.controller;

import com.alten.hotel.book.api.dto.ChangeReservationDTO;
import com.alten.hotel.book.api.dto.CreateReservationDTO;
import com.alten.hotel.book.api.model.Reservation;
import com.alten.hotel.book.api.service.ReservationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.alten.hotel.book.api.utilitary.Constants.ROOT_URL_RESERVATION_CONTROLLER;

/**
 * Class responsible for implementation of a set of endpoints (CRUD operations) in a rest controller referring
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
     * An endpoint within the Reservations Controller that creates a new reservation.
     * @param reservationDTO Data transfer class (DTO) that contains the arrival and departure dates of the host.
     * @return Class with the output payload with the reservation data, if successful.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody CreateReservationDTO reservationDTO){
        return new ResponseEntity<>(reservationService.createReservation(reservationDTO), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> cancelReservation(@PathVariable long id){
        return new ResponseEntity<>(reservationService.cancelReservation(id), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/modify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> modifyReservation(@PathVariable long id,
                                                         @Valid @RequestBody ChangeReservationDTO changeReservationDTO){
        return new ResponseEntity<>(reservationService.modifyReservation(id, changeReservationDTO), HttpStatus.OK);
    }

}
