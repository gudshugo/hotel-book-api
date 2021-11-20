package com.alten.hotel.book.api.service.impl;

import com.alten.hotel.book.api.dto.input.ChangeReservationInputDTO;
import com.alten.hotel.book.api.dto.input.CreateReservationInputDTO;
import com.alten.hotel.book.api.exception.ElementNotFoundException;
import com.alten.hotel.book.api.exception.UnavailableRoomException;
import com.alten.hotel.book.api.model.Reservation;
import com.alten.hotel.book.api.model.Room;
import com.alten.hotel.book.api.repository.ReservationRepository;
import com.alten.hotel.book.api.service.ReservationService;
import com.alten.hotel.book.api.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static com.alten.hotel.book.api.utility.DateUtil.*;

/**
 * A reservation service implementation class containing methods referring to implementations
 * of the ReservationService interface.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
    }

    /**
     * Implementation of the method that creates a new reservation.
     * @param reservationDTO Data transfer class (DTO) that contains the check-in and check-out dates
     * of the hotel guest.
     * @return A Reservation object created data.
     */
    @Override
    @Transactional
    public Reservation createReservation(CreateReservationInputDTO reservationDTO) {
        LocalDate checkIn = reservationDTO.getCheckIn();
        LocalDate checkOut = reservationDTO.getCheckOut();

        verifyDateIntegrity(checkIn, checkOut);

        Room room = roomService.findById(reservationDTO.getRoomId());

        checkIfRoomIsAvailable(room.getId(), checkIn, checkOut);

        Reservation reservation = Reservation.builder()
                .isReserved(true)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .room(room)
                .build();

        return reservationRepository.save(reservation);
    }

    /**
     * Implementation of the method that cancel a reservation.
     * @param id A unique number that identifies a Reservation within the database.
     * @return A Reservation object canceled data.
     */
    @Override
    @Transactional
    public Reservation cancelReservation(long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if(reservation.isPresent()){
            reservation.get().setReserved(false);
            return reservationRepository.save(reservation.get());
        }

        throw new ElementNotFoundException(String.format("Reservation with id: %d not found.", id));
    }

    /**
     * Implementation of the method that modifies a reservation.
     * @param id A unique number that identifies a Reservation within the database.
     * @param changeReservationInputDTO Data transfer class (DTO) that contains the check-in and check-out dates
     * of the hotel guest.
     * @exception ElementNotFoundException It's thrown in case the reservation isn't found in the database.
     * @return A Reservation object modified data.
     */
    @Override
    @Transactional
    public Reservation modifyReservation(long id, ChangeReservationInputDTO changeReservationInputDTO) {
        LocalDate checkIn = changeReservationInputDTO.getCheckIn();
        LocalDate checkOut = changeReservationInputDTO.getCheckOut();

        verifyDateIntegrity(checkIn, checkOut);

        Optional<Reservation> reservation = Optional.ofNullable(reservationRepository.findByIdAndIsReserved(id, true));

        if(reservation.isPresent()){
            Reservation currentReservation = reservation.get();
            checkIfRoomIsAvailable(currentReservation.getRoom().getId(), checkIn, checkOut);

            currentReservation.setCheckIn(checkIn);
            currentReservation.setCheckOut(checkOut);
            return reservationRepository.save(reservation.get());
        }

        throw new ElementNotFoundException(String.format("Reservation with id: %d not found.", id));
    }

    /**
     * A private method that checks if a room is available for a reservation.
     * @param roomId A unique number that identifies a Room within the database.
     * @param checkIn Customer check-in date.
     * @param checkOut Customer check-out date.
     * @exception UnavailableRoomException It's thrown in case the room is unavailable for the given range of dates.
     */
    private void checkIfRoomIsAvailable(long roomId, LocalDate checkIn, LocalDate checkOut){
        Set<Long> reservationIds = reservationRepository.findReservationsBetweenCheckInAndCheckOut(roomId,
                checkIn, checkOut);

        if(!reservationIds.isEmpty()){
            throw new UnavailableRoomException();
        }
    }
}
