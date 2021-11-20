package com.alten.hotel.book.api.service;

import com.alten.hotel.book.api.dto.output.RoomAvailabilityOutputDTO;
import com.alten.hotel.book.api.exception.ElementNotFoundException;
import com.alten.hotel.book.api.model.Reservation;
import com.alten.hotel.book.api.model.Room;
import com.alten.hotel.book.api.repository.RoomRepository;
import com.alten.hotel.book.api.service.impl.RoomServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    public void shouldFindByIdWithSuccess(){
        //GIVEN
        final long id = 10L;
        Room roomExpected = Room.builder()
                .id(id)
                .build();

        //WHEN
        when(roomRepository.findById(id)).thenReturn(Optional.of(roomExpected));

        Room roomTested = roomService.findById(id);

        //THEN
        Assertions.assertThat(roomTested).isNotNull();
        Assertions.assertThat(roomTested.getId()).isEqualTo(roomExpected.getId());
    }

    @Test
    public void shouldThrowElementNotFoundExceptionWhenFindRoomById(){
        //GIVEN
        final long id = 12L;
        final String elementNotFoundExceptionMessage = String.format("Room with id: %d not found.", id);

        //WHEN
        when(roomRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        //THEN
        Assertions.assertThatThrownBy(() -> roomService.findById(id))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessage(elementNotFoundExceptionMessage);
    }

    @Test
    public void shouldGetRoomAvailabilityByGivenDatesWithSuccess(){
        //GIVEN
        final long id = 12L;
        final LocalDate checkIn = LocalDate.now();
        final LocalDate checkOut = checkIn.plusDays(6);
        Room roomExpected = createTestRoom();

        //WHEN
        when(roomRepository.findById(id)).thenReturn(Optional.of(roomExpected));

        RoomAvailabilityOutputDTO availability = roomService.getRoomAvailabilityByGivenDates(id, checkIn, checkOut);

        //THEN
        Assertions.assertThat(availability).isNotNull();
        Assertions.assertThat(availability.getRoomId()).isEqualTo(roomExpected.getId());
        Assertions.assertThat(availability.getAvailableDates()).isNotEmpty();
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
