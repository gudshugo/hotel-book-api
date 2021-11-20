package com.alten.hotel.book.api.utility;

import com.alten.hotel.book.api.exception.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static com.alten.hotel.book.api.utility.Constants.*;

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
        validadeIfCheckInDateIsDifferentFromNow(checkIn);
        validadePastCheckIn(checkIn);
        validateCheckInDateOrder(checkIn, checkOut);
        validateReservationSpentTimeLongerThanThreeDays(checkIn, checkOut);
        validateReservationCheckInAfterThirtyDays(checkIn);
    }

    /**
     * Method that checks if check-in date is different from today.
     * @param checkIn Customer check-in date.
     * @exception UnavailableRoomException It's thrown in case the room is unavailable for the given range of dates.
     */
    private static void validadeIfCheckInDateIsDifferentFromNow(LocalDate checkIn){
        if(LocalDate.now().isEqual(checkIn)){
            throw new UnavailableRoomException();
        }
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
     *  * Note: It is important to mention that an extra day was added to the calculation result of the ChronoUnit.until()
     *  function because the end date is not included in the calculation (source: javadoc documentation).
     * @param checkIn Customer check-in.
     * @param checkOut Customer check-out.
     * @exception InvalidReservationSpentTimeException Exception thrown if the reservation spent date
     * is longer than three days.
     */
    private static void validateReservationSpentTimeLongerThanThreeDays(LocalDate checkIn, LocalDate checkOut){
        long spentTime = checkIn.until(checkOut, ChronoUnit.DAYS) + EXCLUDED_END_DATE;

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
