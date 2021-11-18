package com.alten.hotel.book.api.exception;

/**
 * Exception class referring to validation if the checkIn date is less than the current day.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */


public class PastDayCheckInException extends CustomException{

    public PastDayCheckInException() {
        super("The checkIn date can't be earlier than today.");
    }

}
