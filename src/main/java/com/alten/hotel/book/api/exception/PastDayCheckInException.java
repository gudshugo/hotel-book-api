package com.alten.hotel.book.api.exception;

/**
 * Exception class referring to validation if the check-in date is less than the current day.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */


public class PastDayCheckInException extends CustomException{

    public PastDayCheckInException() {
        super("The check-in date cannot be made using past days.");
    }

}
