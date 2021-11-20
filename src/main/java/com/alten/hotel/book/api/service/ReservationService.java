package com.alten.hotel.book.api.service;

import com.alten.hotel.book.api.dto.input.ChangeReservationInputDTO;
import com.alten.hotel.book.api.dto.input.CreateReservationInputDTO;
import com.alten.hotel.book.api.model.Reservation;

public interface ReservationService {

    Reservation createReservation(CreateReservationInputDTO createReservationInputDTO);

    Reservation cancelReservation(long id);

    Reservation modifyReservation(long id, ChangeReservationInputDTO changeReservationInputDTO);
}
