package com.alten.hotel.book.api.exception;

/**
 * Custom exception used as abstract class for other API-specific exceptions.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

public abstract class CustomException extends RuntimeException{

    protected CustomException(final String message){
        super(message);
    }

}
