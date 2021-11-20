package com.alten.hotel.book.api.exception;

/**
 * Exception class referring to validation if the check-in date is greater than the check-out date.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */


public class InvalidCheckInDateOrderException extends CustomException{

    public InvalidCheckInDateOrderException() {
        super("The check-in date can't be greater than the check-out date.");
    }

}
