package com.alten.hotel.book.api.service;

import com.alten.hotel.book.api.dto.ReservationInput;
import com.alten.hotel.book.api.model.Reservation;

public interface ReservationService {

    Reservation createReservation(long roomId, ReservationInput reservationInput);

}
