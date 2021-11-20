package com.alten.hotel.book.api.service;

import com.alten.hotel.book.api.dto.input.ChangeReservationInputDTO;
import com.alten.hotel.book.api.dto.input.CreateReservationInputDTO;
import com.alten.hotel.book.api.model.Reservation;

/**
 * Interface with Reservation class data manipulation method signatures.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

public interface ReservationService {

    Reservation createReservation(CreateReservationInputDTO createReservationInputDTO);

    Reservation cancelReservation(long id);

    Reservation modifyReservation(long id, ChangeReservationInputDTO changeReservationInputDTO);
}
