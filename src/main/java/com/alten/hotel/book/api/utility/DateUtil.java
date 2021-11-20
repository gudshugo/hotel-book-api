package com.alten.hotel.book.api.utility;

import com.alten.hotel.book.api.exception.InvalidCheckInDateOrderException;
import com.alten.hotel.book.api.exception.InvalidReservationRangeOfDaysException;
import com.alten.hotel.book.api.exception.InvalidReservationSpentTimeException;
import com.alten.hotel.book.api.exception.PastDayCheckInException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static com.alten.hotel.book.api.utility.Constants.RESERVATION_MAX_RANGE_OF_DAYS;
import static com.alten.hotel.book.api.utility.Constants.RESERVATION_MAX_SPENT_TIME;

/**
 * Utility class with methods responsible for handling dates.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

public class DateUtil {

    private DateUtil(){}

    /**
     * Method that validates and verifies the integrity of the customer's check-in and check-out dates.
     * @param checkIn Customer check-in date.
     * @param checkOut Customer check-out date.
     */
    public static void verifyDateIntegrity(LocalDate checkIn, LocalDate checkOut){
        validadePastCheckIn(checkIn);
        validateCheckInDateOrder(checkIn, checkOut);
        validateReservationSpentTimeLongerThanThreeDays(checkIn, checkOut);
        validateReservationCheckInAfterThirtyDays(checkIn);
    }

    /**
     * Method that retrieves a range of dates given two fixed dates.
     * @param checkIn Customer check-in date.
     * @param checkOut Customer check-out date.
     * @return A Stream with a range of dates.
     */
    public static Stream<LocalDate> getStreamRangeDatesBetweenTwoDates(LocalDate checkIn, LocalDate checkOut){
        return checkIn.datesUntil(checkOut.plusDays(1));
    }

    /**
     * Method that checks if the date entered is in the past.
     * @param checkIn Customer check-in date.
     * @exception PastDayCheckInException Exception thrown in case the check-in date is a past date.
     */
    private static void validadePastCheckIn(LocalDate checkIn){
        if(checkIn.isBefore(LocalDate.now())){
            throw new PastDayCheckInException();
        }
    }

    /**
     * Method that checks if the check-in date is after the check-out date.
     * @param checkIn Customer check-in date.
     * @param checkOut Customer check-out date.
     * @exception InvalidCheckInDateOrderException Exception thrown in case the check-in date is after
     * than the check-out date.
     */
    private static void validateCheckInDateOrder(LocalDate checkIn, LocalDate checkOut){
        if(checkIn.isAfter(checkOut)){
            throw new InvalidCheckInDateOrderException();
        }
    }

    /**
     * Method that verifies if the reservation spent date is longer than three days.
     * @param checkIn Customer check-in.
     * @param checkOut Customer check-out.
     * @exception InvalidReservationSpentTimeException Exception thrown if the reservation spent date
     * is longer than three days.
     */
    private static void validateReservationSpentTimeLongerThanThreeDays(LocalDate checkIn, LocalDate checkOut){
        long spentTime = checkIn.until(checkOut, ChronoUnit.DAYS);

        if(spentTime > RESERVATION_MAX_SPENT_TIME){
            throw new InvalidReservationSpentTimeException();
        }
    }

    /**
     * Method that verifies if the client's reservation is made with thirty days in advance.
     * @param checkIn Customer check-in
     * @exception InvalidReservationRangeOfDaysException Exception thrown in case the reservation date is
     * made with more than thirty days in advance.
     */
    private static void validateReservationCheckInAfterThirtyDays(LocalDate checkIn){
        LocalDate currentDayPlusThirtyDays = LocalDate.now().plusDays(RESERVATION_MAX_RANGE_OF_DAYS);

        if(checkIn.isAfter(currentDayPlusThirtyDays)){
            throw new InvalidReservationRangeOfDaysException();
        }
    }

}
