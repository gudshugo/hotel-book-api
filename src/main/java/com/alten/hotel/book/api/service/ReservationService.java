package com.alten.hotel.book.api.service;

import com.alten.hotel.book.api.dto.ChangeReservationDTO;
import com.alten.hotel.book.api.dto.CreateReservationDTO;
import com.alten.hotel.book.api.model.Reservation;

public interface ReservationService {

    Reservation createReservation(CreateReservationDTO createReservationDTO);

    Reservation cancelReservation(long id);

    Reservation modifyReservation(long id, ChangeReservationDTO changeReservationDTO);
}
