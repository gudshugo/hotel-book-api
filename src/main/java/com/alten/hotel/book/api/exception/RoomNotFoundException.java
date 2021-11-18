package com.alten.hotel.book.api.exception;

/**
 * Exception class referring to a room not found in the databases.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

public class RoomNotFoundException extends CustomException{

    public RoomNotFoundException(String message) {
        super(message);
    }

}
