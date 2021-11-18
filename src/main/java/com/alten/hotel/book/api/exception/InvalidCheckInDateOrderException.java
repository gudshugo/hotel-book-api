package com.alten.hotel.book.api.exception;

/**
 * Exception class referring to validation if the checkIn date is greater than the checkOut date.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */


public class InvalidCheckInDateOrderException extends CustomException{

    public InvalidCheckInDateOrderException() {
        super("The checkIn date can't be greater than the checkOut date.");
    }

}
