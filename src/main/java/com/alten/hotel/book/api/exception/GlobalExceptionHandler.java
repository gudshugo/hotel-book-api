package com.alten.hotel.book.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Class that acts as a global interceptor to direct the correct exceptions related to its status code
 * and error message.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * A method that intercepts any other cases of Exceptions and returns his error message and status code handled.
     * @param exception Exception thrown if a resource is not found.
     * @return A ResponseEntity with Exception handled its status codes and messages accordingly.
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAnyOtherException(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * A method that intercepts RoomNotFoundException cases and returns the error messages and status code handled.
     * @param elementNotFoundException Exception thrown if a resource is not found.
     * @return A ResponseEntity with RoomNotFoundException handled its status codes and messages accordingly.
     */
    @ExceptionHandler(ElementNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleRoomNotFoundException(ElementNotFoundException elementNotFoundException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                elementNotFoundException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    /**
     * A method that intercepts InvalidReservationSpentTimeException cases and returns the error messages and status code handled.
     * @param invalidReservationSpentTimeException Exception thrown if the reservation date spent is longer than three days.
     * @return A ResponseEntity with InvalidReservationSpentTimeException handled its status codes and messages accordingly.
     */
    @ExceptionHandler(InvalidReservationSpentTimeException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidReservationSpentTimeException(InvalidReservationSpentTimeException invalidReservationSpentTimeException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                invalidReservationSpentTimeException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * A method that intercepts InvalidReservationRangeOfDaysException cases and returns the error messages and status code handled.
     * @param invalidReservationRangeOfDaysException Exception thrown in case the reservation date is made with more than thirty days in advance.
     * @return A ResponseEntity with InvalidReservationRangeOfDaysException handled its status codes and messages accordingly.
     */
    @ExceptionHandler(InvalidReservationRangeOfDaysException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidReservationRangeOfDaysException(InvalidReservationRangeOfDaysException invalidReservationRangeOfDaysException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                invalidReservationRangeOfDaysException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * A method that intercepts MethodArgumentNotValidException cases and returns the error messages and status code handled.
     * @param methodArgumentNotValidException Exception thrown in case input parameters are invalid using @Valid notation.
     * @return A ResponseEntity with MethodArgumentNotValidException handled its status codes and messages accordingly.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                methodArgumentNotValidException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * A method that intercepts UnavailableRoomException cases and returns the error messages and status code handled.
     * @param unavailableRoomException Exception thrown in case the room is not available for reservation.
     * @return A ResponseEntity with UnavailableRoomException handled its status codes and messages accordingly.
     */
    @ExceptionHandler(UnavailableRoomException.class)
    public final ResponseEntity<ExceptionResponse> handleUnavailableRoomException(UnavailableRoomException unavailableRoomException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                unavailableRoomException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * A method that intercepts InvalidCheckInDateOrderException cases and returns the error messages and status code handled.
     * @param invalidCheckInDateOrderException Exception thrown in case the check-in date is greater than the check-out date.
     * @return A ResponseEntity with InvalidCheckInDateOrderException handled its status codes and messages accordingly.
     */
    @ExceptionHandler(InvalidCheckInDateOrderException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidCheckInDateOrderException(InvalidCheckInDateOrderException invalidCheckInDateOrderException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                invalidCheckInDateOrderException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * A method that intercepts PastDayCheckInException cases and returns the error messages and status code handled.
     * @param pastDayCheckInException Exception thrown in case the check-in date is a past date.
     * @return A ResponseEntity with PastDayCheckInException handled its status codes and messages accordingly.
     */
    @ExceptionHandler(PastDayCheckInException.class)
    public final ResponseEntity<ExceptionResponse> handlePastDayCheckInException(PastDayCheckInException pastDayCheckInException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                pastDayCheckInException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
