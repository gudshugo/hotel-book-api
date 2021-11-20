package com.alten.hotel.book.api.service;

import com.alten.hotel.book.api.dto.input.ChangeReservationInputDTO;
import com.alten.hotel.book.api.dto.input.CreateReservationInputDTO;
import com.alten.hotel.book.api.exception.*;
import com.alten.hotel.book.api.model.Reservation;
import com.alten.hotel.book.api.model.Room;
import com.alten.hotel.book.api.repository.ReservationRepository;
import com.alten.hotel.book.api.service.impl.ReservationServiceImpl;
import com.alten.hotel.book.api.service.impl.RoomServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomServiceImpl roomService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    public void shouldCreateReservationWithSuccess(){
        //GIVEN
        final CreateReservationInputDTO inputRequest = new CreateReservationInputDTO(1L, LocalDate.now(),
                LocalDate.now().plusDays(2));
        final Room roomExpected = createTestRoom();
        final Reservation reservationTested = createTestReservation(roomExpected);

        //WHEN
        when(roomService.findById(inputRequest.getRoomId())).thenReturn(roomExpected);
        when(reservationRepository.save(reservationTested)).thenReturn(reservationTested);

        Reservation reservationExpected = reservationService.createReservation(inputRequest);

        //THEN
        Assertions.assertThat(reservationExpected).isNotNull();
        Assertions.assertThat(reservationExpected.getRoom()).isNotNull();
        Assertions.assertThat(reservationExpected.getRoom().getId()).isEqualTo(roomExpected.getId());
        Assertions.assertThat(reservationExpected.getCheckIn()).isEqualTo(inputRequest.getCheckIn());
        Assertions.assertThat(reservationExpected.getCheckOut()).isEqualTo(inputRequest.getCheckOut());
    }

    @Test
    public void shouldThrowPastDayCheckInExceptionWhenCreateReservation(){
        //GIVEN
        final CreateReservationInputDTO inputRequest = new CreateReservationInputDTO(1L, LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(2));

        //THEN
        Assertions.assertThatThrownBy(() -> reservationService.createReservation(inputRequest))
                .isInstanceOf(PastDayCheckInException.class);
    }

    @Test
    public void shouldThrowInvalidCheckInDateOrderExceptionWhenCreateReservation(){
        //GIVEN
        final CreateReservationInputDTO inputRequest =  new CreateReservationInputDTO(1L, LocalDate.now().plusDays(5),
                LocalDate.now());

        //THEN
        Assertions.assertThatThrownBy(() -> reservationService.createReservation(inputRequest))
                .isInstanceOf(InvalidCheckInDateOrderException.class);
    }

    @Test
    public void shouldThrowInvalidReservationSpentTimeExceptionWhenCreateReservation(){
        //GIVEN
        final CreateReservationInputDTO inputRequest =  new CreateReservationInputDTO(1L, LocalDate.now(),
                LocalDate.now().plusDays(5));

        //THEN
        Assertions.assertThatThrownBy(() -> reservationService.createReservation(inputRequest))
                .isInstanceOf(InvalidReservationSpentTimeException.class);
    }

    @Test
    public void shouldThrowInvalidReservationRangeOfDaysExceptionWhenCreateReservation(){
        //GIVEN
        final CreateReservationInputDTO inputRequest =  new CreateReservationInputDTO(1L, LocalDate.now().plusDays(31),
                LocalDate.now().plusDays(32));

        //THEN
        Assertions.assertThatThrownBy(() -> reservationService.createReservation(inputRequest))
                .isInstanceOf(InvalidReservationRangeOfDaysException.class);
    }

    @Test
    public void shouldThrowUnavailableRoomExceptionWhenCreateReservation(){
        //GIVEN
        final CreateReservationInputDTO inputRequest = new CreateReservationInputDTO(1L, LocalDate.now(),
                LocalDate.now().plusDays(2));
        final Room roomExpected = createTestRoom();
        final String unavailableRoomExceptionMessage = "The room is unavailable for the given range of dates.";

        //WHEN
        when(roomService.findById(inputRequest.getRoomId())).thenReturn(roomExpected);
        when(reservationRepository.findReservationsBetweenCheckInAndCheckOut(anyLong(), any(), any()))
                .thenReturn(Set.of(1L));

        //THEN
        Assertions.assertThatThrownBy(() -> reservationService.createReservation(inputRequest))
                .isInstanceOf(UnavailableRoomException.class)
                .hasMessage(unavailableRoomExceptionMessage);
    }


    @Test
    public void shouldCancelReservationWithSuccess(){
        //GIVEN
        final long id = 10L;
        final Room roomExpected = createTestRoom();
        final Reservation reservationExpected = createTestReservation(roomExpected);

        //WHEN
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservationExpected));
        when(reservationRepository.save(reservationExpected)).thenReturn(reservationExpected);

        Reservation reservationTested = reservationService.cancelReservation(id);

        //THEN
        Assertions.assertThat(reservationTested).isNotNull();
        Assertions.assertThat(reservationTested.getId()).isEqualTo(reservationExpected.getId());
        Assertions.assertThat(reservationTested.getCheckIn()).isEqualTo(reservationExpected.getCheckIn());
        Assertions.assertThat(reservationTested.getCheckOut()).isEqualTo(reservationExpected.getCheckOut());
        Assertions.assertThat(reservationTested.getRoom()).isEqualTo(reservationExpected.getRoom());
    }

    @Test
    public void shouldThrowElementNotFoundExceptionWhenCancelReservation(){
        //GIVEN
        final long id = 10L;
        final String elementNotFoundExceptionMessage = String.format("Reservation with id: %d not found.", id);

        //WHEN
        when(reservationRepository.findById(id)).thenReturn(Optional.empty());

        //THEN
        Assertions.assertThatThrownBy(() -> reservationService.cancelReservation(id))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessage(elementNotFoundExceptionMessage);
    }

    @Test
    public void shouldModifyReservationWithSuccess(){
        //GIVEN
        final long id = 10L;
        final ChangeReservationInputDTO inputRequest = new ChangeReservationInputDTO(LocalDate.now(),
                LocalDate.now().plusDays(1));
        final Room roomExpected = createTestRoom();
        final Reservation reservationExpected = createTestReservation(roomExpected);


        //WHEN
        when(reservationRepository.findByIdAndIsReserved(id, true)).thenReturn(reservationExpected);
        when(reservationRepository.save(reservationExpected)).thenReturn(reservationExpected);

        Reservation reservationTested = reservationService.modifyReservation(id, inputRequest);

        //THEN
        Assertions.assertThat(reservationTested).isNotNull();
        Assertions.assertThat(reservationTested.getCheckIn()).isEqualTo(inputRequest.getCheckIn());
        Assertions.assertThat(reservationTested.getCheckOut()).isEqualTo(inputRequest.getCheckOut());
    }

    @Test
    public void shouldThrowElementNotFoundExceptionWhenModifyReservation(){
        //GIVEN
        final long id = 10L;
        final ChangeReservationInputDTO inputRequest = new ChangeReservationInputDTO(LocalDate.now(),
                LocalDate.now().plusDays(2));
        final String elementNotFoundExceptionMessage = String.format("Reservation with id: %d not found.", id);

        //THEN
        Assertions.assertThatThrownBy(() -> reservationService.modifyReservation(id, inputRequest))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessage(elementNotFoundExceptionMessage);
    }

    private Room createTestRoom(){
        Room room = Room.builder()
                .id(12L)
                .build();
        Reservation reservation = createTestReservation(room);
        room.setReservation(singletonList(reservation));
        return room;
    }

    private Reservation createTestReservation(Room room){
        return Reservation.builder()
                .isReserved(true)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(2))
                .room(room)
                .build();
    }

}
