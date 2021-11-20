package com.alten.hotel.book.api.service;

import com.alten.hotel.book.api.dto.output.RoomAvailabilityOutputDTO;
import com.alten.hotel.book.api.model.Room;

import java.time.LocalDate;

public interface RoomService {

    Room findById(long id);

    RoomAvailabilityOutputDTO getRoomAvailabilityByGivenDates(long id, LocalDate checkIn, LocalDate checkOut);

}
