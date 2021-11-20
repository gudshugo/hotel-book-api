package com.alten.hotel.book.api.service.impl;

import com.alten.hotel.book.api.dto.output.RoomAvailabilityOutputDTO;
import com.alten.hotel.book.api.exception.ElementNotFoundException;
import com.alten.hotel.book.api.model.Room;
import com.alten.hotel.book.api.repository.RoomRepository;
import com.alten.hotel.book.api.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.alten.hotel.book.api.utilitary.DateUtil.getStreamRangeDatesBetweenTwoDates;

/**
 * A reservation service implementation class containing methods referring to implementations
 * of the ReservationService interface.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * The implementation of a method that finds a room by id.
     * @param id A unique number that identifies a Room within the database.
     * @return A Room object class, if it's found.
     * @exception ElementNotFoundException It's thrown in case the room isn't found in the database.
     */
    @Override
    public Room findById(long id) {
        Optional<Room> room = roomRepository.findById(id);

        if(room.isPresent()){
            return room.get();
        }

        throw new ElementNotFoundException(String.format("Room with id: %d not found.", id));
    }

    /**
     * The implementation of a method that verify if a Room is available given a range of Dates.
     * @param id A unique number that identifies a Room within the database.
     * @param checkIn Customer check-in date.
     * @param checkOut Customer check-out date.
     * @return A RoomAvailabilityOutputDTO object instance with the room id and a
     * Set of available check-in and check-out dates.
     */
    @Override
    public RoomAvailabilityOutputDTO getRoomAvailabilityByGivenDates(long id, LocalDate checkIn, LocalDate checkOut) {
        Room room = findById(id);

        Set<LocalDate> possibleReserveDates = getStreamRangeDatesBetweenTwoDates(checkIn, checkOut)
                                              .collect(Collectors.toSet());

        Set<LocalDate> reservedDates = room.getReservation()
                                        .stream()
                                        .flatMap(reservation -> getStreamRangeDatesBetweenTwoDates(reservation.getCheckIn(), reservation.getCheckOut()))
                                        .collect(Collectors.toSet());

        possibleReserveDates.removeAll(reservedDates);

        return new RoomAvailabilityOutputDTO(id, possibleReserveDates);
    }

}
