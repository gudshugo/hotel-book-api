package com.alten.hotel.book.api.exception;

/**
 * Exception class referring to validation if the checkIn and checkOut dates range is the same as the existing reservation.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */


public class ReserveDateAlreadyMadeException extends CustomException{

    public ReserveDateAlreadyMadeException() {
        super("The checkIn and checkOut dates entered must be different from your current reservation.");
    }

}
