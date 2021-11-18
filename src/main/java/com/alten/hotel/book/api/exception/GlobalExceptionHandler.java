package com.alten.hotel.book.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * Class that acts as a global interceptor to direct the correct exceptions related to its status code
 * and error message.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * A method that intercepts RoomNotFoundException cases and returns the error messages and status code handled.
     * @param roomNotFoundException Exception class used when a room isn't found in a database search.
     * @return A ResponseEntity with RoomNotFoundException handled its status codes and messages accordingly.
     */
    @ExceptionHandler(RoomNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleRoomNotFoundException(RoomNotFoundException roomNotFoundException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                roomNotFoundException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

}
