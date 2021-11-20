package com.alten.hotel.book.api.exception;

/**
 * Exception class referring to a room not found in the database.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

public class ElementNotFoundException extends CustomException {

    public ElementNotFoundException(String message) {
        super(message);
    }

}
