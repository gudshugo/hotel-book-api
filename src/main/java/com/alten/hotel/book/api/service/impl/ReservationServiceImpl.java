package com.alten.hotel.book.api.service.impl;

import com.alten.hotel.book.api.dto.CretateReservationDTO;
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
    public Reservation createReservation(CretateReservationDTO cretateReservationDTO) {
        LocalDate checkIn = cretateReservationDTO.getCheckIn();
        LocalDate checkOut = cretateReservationDTO.getCheckOut();

        verifyDateIntegrity(checkIn, checkOut);

        Room room = roomService.findById(cretateReservationDTO.getRoomId());

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

    private void checkIfRoomIsAvailable(long roomId, LocalDate checkIn, LocalDate checkOut){
        Set<Long> reservationIds = reservationRepository.findReservationsBetweencheckInAndcheckOut(roomId,
                checkIn, checkOut);

        if(!reservationIds.isEmpty()){
            throw new UnavailableRoomException();
        }
    }

}
