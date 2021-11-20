package com.alten.hotel.book.api.service;

import com.alten.hotel.book.api.dto.output.RoomAvailabilityOutputDTO;
import com.alten.hotel.book.api.model.Room;

import java.time.LocalDate;

/**
 * Interface with Room class data manipulation method signatures.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

public interface RoomService {

    Room findById(long id);

    RoomAvailabilityOutputDTO getRoomAvailabilityByGivenDates(long id, LocalDate checkIn, LocalDate checkOut);

}
