package com.alten.hotel.book.api.utilitary;

import com.alten.hotel.book.api.exception.InvalidReservationRangeOfDaysException;
import com.alten.hotel.book.api.exception.InvalidReservationSpentTimeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.alten.hotel.book.api.utilitary.Constants.*;

public class DateUtil {

    private DateUtil(){}

    public static LocalDateTime getStartDateFromLocalDate(LocalDate startDate){
        return startDate.atStartOfDay();
    }

    public static LocalDateTime getEndDateFromLocalDate(LocalDate endDate){
        return endDate.atTime(23, 59, 59);
    }

    public static void validateReservationSpentTimeLongerThanThreeDays(LocalDate startDate, LocalDate endDate){
        long spentTime = startDate.until(endDate, ChronoUnit.DAYS);

        if(spentTime > RESERVATION_MAX_SPENT_TIME){
            throw new InvalidReservationSpentTimeException();
        }
    }

    public static void validateReservationStartDayAfterThirtyDays(LocalDate startDate){
        LocalDate currentDayPlusThirtyDays = LocalDate.now().plusDays(RESERVATION_MAX_RANGE_OF_DAYS);

        if(startDate.isAfter(currentDayPlusThirtyDays)){
            throw new InvalidReservationRangeOfDaysException();
        }
    }

}
