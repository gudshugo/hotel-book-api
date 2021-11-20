package com.alten.hotel.book.api.controller;

import com.alten.hotel.book.api.dto.output.RoomAvailabilityOutputDTO;
import com.alten.hotel.book.api.service.RoomService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.alten.hotel.book.api.utilitary.Constants.LOCAL_DATE_FORMAT;
import static com.alten.hotel.book.api.utilitary.Constants.ROOT_URL_ROOM_CONTROLLER;

/**
 * Class responsible for implementation of a set of endpoints (CRUD operations) in a REST controller referring
 * to the Room class.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping(value = ROOT_URL_ROOM_CONTROLLER)
@Api("Resource controller referring to insert, delete, update and read operations with the Room entity.")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * An endpoint method that verify if a Room is available given a range of Dates.
     * @param id A unique number that identifies a Room within the database.
     * @param checkIn Customer check-out date.
     * @param checkOut Customer check-out date.
     * @return Data transfer class (DTO) that contains the room id and a Set of available check-in and check-out dates.
     */
    @GetMapping(value = "{id}/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomAvailabilityOutputDTO> getRoomAvailability(@PathVariable long id,
                                                                         @RequestParam("check_in") @DateTimeFormat(pattern = LOCAL_DATE_FORMAT) LocalDate checkIn,
                                                                         @RequestParam("check_out") @DateTimeFormat(pattern = LOCAL_DATE_FORMAT) LocalDate checkOut){
        return new ResponseEntity<>(roomService.getRoomAvailabilityByGivenDates(id, checkIn, checkOut), HttpStatus.OK);
    }
}
