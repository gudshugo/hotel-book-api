package com.alten.hotel.book.api.utilitary;

import com.alten.hotel.book.api.exception.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.alten.hotel.book.api.utilitary.Constants.RESERVATION_MAX_RANGE_OF_DAYS;
import static com.alten.hotel.book.api.utilitary.Constants.RESERVATION_MAX_SPENT_TIME;

public class DateUtil {

    private DateUtil(){}

    public static void verifyDateIntegrity(LocalDate checkIn, LocalDate checkOut){
        validadePastCheckIn(checkIn);
        validateCheckInDateOrder(checkIn, checkOut);
        validateReservationSpentTimeLongerThanThreeDays(checkIn, checkOut);
        validateReservationCheckInAfterThirtyDays(checkIn);
    }

    private static void validadePastCheckIn(LocalDate checkIn){
        if(checkIn.isBefore(LocalDate.now())){
            throw new PastDayCheckInException();
        }
    }

    private static void validateCheckInDateOrder(LocalDate checkIn, LocalDate checkOut){
        if(checkIn.isAfter(checkOut)){
            throw new InvalidCheckInDateOrderException();
        }
    }

    private static void validateReservationSpentTimeLongerThanThreeDays(LocalDate checkIn, LocalDate checkOut){
        long spentTime = checkIn.until(checkOut, ChronoUnit.DAYS);

        if(spentTime > RESERVATION_MAX_SPENT_TIME){
            throw new InvalidReservationSpentTimeException();
        }
    }

    private static void validateReservationCheckInAfterThirtyDays(LocalDate checkIn){
        LocalDate currentDayPlusThirtyDays = LocalDate.now().plusDays(RESERVATION_MAX_RANGE_OF_DAYS);

        if(checkIn.isAfter(currentDayPlusThirtyDays)){
            throw new InvalidReservationRangeOfDaysException();
        }
    }

}
