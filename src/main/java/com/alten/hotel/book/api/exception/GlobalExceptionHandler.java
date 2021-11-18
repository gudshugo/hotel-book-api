package com.alten.hotel.book.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class GlobalExceptionHandler {

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

    @ExceptionHandler(InvalidReservationSpentTimeException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidReservationSpentTimeException(InvalidReservationSpentTimeException invalidReservationSpentTimeException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                invalidReservationSpentTimeException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidReservationRangeOfDaysException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidReservationRangeOfDaysException(InvalidReservationRangeOfDaysException invalidReservationRangeOfDaysException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                invalidReservationRangeOfDaysException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                methodArgumentNotValidException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnavailableRoomException.class)
    public final ResponseEntity<ExceptionResponse> handleUnavailableRoomException(UnavailableRoomException unavailableRoomException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                unavailableRoomException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidCheckInDateOrderException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidCheckInDateOrderException(InvalidCheckInDateOrderException invalidCheckInDateOrderException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                invalidCheckInDateOrderException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PastDayCheckInException.class)
    public final ResponseEntity<ExceptionResponse> handlePastDayCheckInException(PastDayCheckInException pastDayCheckInException){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                pastDayCheckInException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
