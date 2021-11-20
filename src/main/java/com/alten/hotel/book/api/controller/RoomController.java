package com.alten.hotel.book.api.controller;

import com.alten.hotel.book.api.dto.output.RoomAvailabilityOutputDTO;
import com.alten.hotel.book.api.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.alten.hotel.book.api.utilitary.Constants.LOCAL_DATE_FORMAT;
import static com.alten.hotel.book.api.utilitary.Constants.ROOT_URL_ROOM_CONTROLLER;

@RestController
@RequestMapping(value = ROOT_URL_ROOM_CONTROLLER)
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(value = "{id}/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomAvailabilityOutputDTO> getRoomAvailability(@PathVariable long id,
                                                                         @RequestParam("check_in") @DateTimeFormat(pattern = LOCAL_DATE_FORMAT) LocalDate checkIn,
                                                                         @RequestParam("check_out") @DateTimeFormat(pattern = LOCAL_DATE_FORMAT) LocalDate checkOut){
        return new ResponseEntity<>(roomService.getRoomAvailabilityByGivenDates(id, checkIn, checkOut), HttpStatus.OK);
    }
}
