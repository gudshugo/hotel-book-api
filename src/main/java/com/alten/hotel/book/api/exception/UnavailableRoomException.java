package com.alten.hotel.book.api.exception;

/**
 * Exception class referring to validating the status of a room given a range of dates.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */


public class UnavailableRoomException extends CustomException{

    public UnavailableRoomException() {
        super("The room is unavailable for the given range of dates.");
    }

}
