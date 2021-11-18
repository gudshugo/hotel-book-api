package com.alten.hotel.book.api.service.impl;

import com.alten.hotel.book.api.dto.ReservationInput;
import com.alten.hotel.book.api.model.Reservation;
import com.alten.hotel.book.api.model.Room;
import com.alten.hotel.book.api.repository.ReservationRepository;
import com.alten.hotel.book.api.service.ReservationService;
import com.alten.hotel.book.api.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Reservation createReservation(long roomId, ReservationInput reservationInput) {
        validateReservationSpentTimeLongerThanThreeDays(reservationInput.getStartDate(), reservationInput.getEndDate());
        validateReservationStartDayAfterThirtyDays(reservationInput.getStartDate());

        Room room = roomService.findById(roomId);

        Reservation reservation = Reservation.builder()
                .isReserved(true)
                .startDate(getStartDateFromLocalDate(reservationInput.getStartDate()))
                .endDate(getEndDateFromLocalDate(reservationInput.getEndDate()))
                .room(room)
                .build();

        return reservationRepository.save(reservation);
    }


}
