package com.alten.hotel.book.api.exception;

/**
 * Exception class referring to validation that a customer can't reserve more than 3 days in a room.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */


public class InvalidReservationSpentTimeException extends CustomException{

    public InvalidReservationSpentTimeException() {
        super("The reservation cannot last longer than three days.");
    }

}
