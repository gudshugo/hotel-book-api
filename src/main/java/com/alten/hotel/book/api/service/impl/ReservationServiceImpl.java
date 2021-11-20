package com.alten.hotel.book.api.service.impl;

import com.alten.hotel.book.api.dto.input.ChangeReservationInputDTO;
import com.alten.hotel.book.api.dto.input.CreateReservationInputDTO;
import com.alten.hotel.book.api.exception.ElementNotFoundException;
import com.alten.hotel.book.api.exception.ReserveDateAlreadyMadeException;
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

import static com.alten.hotel.book.api.utilitary.DateUtil.*;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
    }

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

    @Override
    @Transactional
    public Reservation modifyReservation(long id, ChangeReservationInputDTO changeReservationInputDTO) {
        LocalDate checkIn = changeReservationInputDTO.getCheckIn();
        LocalDate checkOut = changeReservationInputDTO.getCheckOut();

        verifyDateIntegrity(checkIn, checkOut);

        Optional<Reservation> reservation = Optional.ofNullable(reservationRepository.findByIdAndIsReserved(id, true));

        if(reservation.isPresent()){
            Reservation currentReservation = reservation.get();
            checkIfModifiedDateIsSameAsCurrentReservation(currentReservation.getCheckIn(), currentReservation.getCheckOut(),
                    checkIn, checkOut);
            checkIfRoomIsAvailable(currentReservation.getRoom().getId(), checkIn, checkOut);

            currentReservation.setCheckIn(checkIn);
            currentReservation.setCheckOut(checkOut);
            return reservationRepository.save(reservation.get());
        }

        throw new ElementNotFoundException(String.format("Reservation with id: %d not found.", id));
    }


    private void checkIfRoomIsAvailable(long roomId, LocalDate checkIn, LocalDate checkOut){
        Set<Long> reservationIds = reservationRepository.findReservationsBetweenCheckInAndCheckOut(roomId,
                checkIn, checkOut);

        if(!reservationIds.isEmpty()){
            throw new UnavailableRoomException();
        }
    }

    private void checkIfModifiedDateIsSameAsCurrentReservation(LocalDate currentCheckIn, LocalDate currentCheckOut,
                                              LocalDate newCheckIn, LocalDate newCheckOut){
        if(compareReserveDates(currentCheckIn, currentCheckOut, newCheckIn, newCheckOut)){
            throw new ReserveDateAlreadyMadeException();
        }
    }

}
