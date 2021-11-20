package com.alten.hotel.book.api.exception;

/**
 * Exception class referring to validating a reservation made with thirty days in advance.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

public class InvalidReservationRangeOfDaysException extends CustomException {

    public InvalidReservationRangeOfDaysException() {
        super("The reservation can't be made more than thirty days in advance.");
    }

}
