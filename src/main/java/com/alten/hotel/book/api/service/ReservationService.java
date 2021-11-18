package com.alten.hotel.book.api.service;

import com.alten.hotel.book.api.dto.CretateReservationDTO;
import com.alten.hotel.book.api.model.Reservation;

public interface ReservationService {

    Reservation createReservation(CretateReservationDTO cretateReservationDTO);

    Reservation cancelReservation(long id);

}
