package com.alten.hotel.book.api.utilitary;

import com.alten.hotel.book.api.exception.InvalidCheckInDateOrderException;
import com.alten.hotel.book.api.exception.InvalidReservationRangeOfDaysException;
import com.alten.hotel.book.api.exception.InvalidReservationSpentTimeException;
import com.alten.hotel.book.api.exception.PastDayCheckInException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.util.stream.Stream;

import static com.alten.hotel.book.api.utilitary.DateUtil.*;

public class DateUtilTest {

    @Test
    public void shouldTestVerifyDateIntegrityWithSuccess(){
        //GIVEN
        final LocalDate checkIn = LocalDate.now();
        final LocalDate checkOut = checkIn.plusDays(2);

        //THEN
        Assertions.assertThatCode(() -> verifyDateIntegrity(checkIn, checkOut))
                .doesNotThrowAnyException();
    }

    @Test
    public void shouldThrowPastDayCheckInExceptionWhenVerifyDateIntegrity(){
        //GIVEN
        final LocalDate checkIn = LocalDate.now().minusDays(1);
        final LocalDate checkOut = checkIn.plusDays(2);
        final String pastDayCheckInExceptionMessage = "The checkIn date can't be earlier than today.";

        //THEN
        Assertions.assertThatThrownBy(() -> verifyDateIntegrity(checkIn, checkOut))
                .isInstanceOf(PastDayCheckInException.class)
                .hasMessage(pastDayCheckInExceptionMessage);
    }

    @Test
    public void shouldThrowInvalidCheckInDateOrderExceptionWhenVerifyDateIntegrity(){
        //GIVEN
        final LocalDate checkIn = LocalDate.now();
        final LocalDate checkOut = checkIn.minusDays(1);
        final String invalidCheckInDateOrderMessage = "The checkIn date can't be greater than the checkOut date.";

        //THEN
        Assertions.assertThatThrownBy(() -> verifyDateIntegrity(checkIn, checkOut))
                .isInstanceOf(InvalidCheckInDateOrderException.class)
                .hasMessage(invalidCheckInDateOrderMessage);
    }

    @Test
    public void shouldThrowInvalidReservationSpentTimeExceptionWhenVerifyDateIntegrity(){
        //GIVEN
        final LocalDate checkIn = LocalDate.now();
        final LocalDate checkOut = checkIn.plusDays(4);
        final String invalidReservationSpentTimeException = "The reservation can't last longer than three days.";

        //THEN
        Assertions.assertThatThrownBy(() -> verifyDateIntegrity(checkIn, checkOut))
                .isInstanceOf(InvalidReservationSpentTimeException.class)
                .hasMessage(invalidReservationSpentTimeException);
    }

    @Test
    public void shouldThrowInvalidReservationRangeOfDaysExceptionWhenVerifyDateIntegrity(){
        //GIVEN
        final LocalDate checkIn = LocalDate.now().plusDays(31);
        final LocalDate checkOut = checkIn.plusDays(1);
        final String invalidReservationSpentTimeException = "The reservation can't be made more than thirty days in advance.";

        //THEN
        Assertions.assertThatThrownBy(() -> verifyDateIntegrity(checkIn, checkOut))
                .isInstanceOf(InvalidReservationRangeOfDaysException.class)
                .hasMessage(invalidReservationSpentTimeException);
    }

    @Test
    public void shouldGetStreamRangeDatesBetweenTwoDatesWithSuccess() {
        //GIVEN
        final LocalDate checkIn = LocalDate.now();
        final LocalDate checkOut = checkIn.plusDays(4);

        //WHEN
        Stream<LocalDate> streamOfDates = getStreamRangeDatesBetweenTwoDates(checkIn, checkOut);

        //THEN
        Assertions.assertThat(streamOfDates).contains(checkIn, checkOut);
    }

    @Test
    public void shouldCompareReserveDatesWhenTheyAreEqual(){
        //GIVEN
        final LocalDate currentCheckIn = LocalDate.now();
        final LocalDate currentCheckOut = currentCheckIn.plusDays(1);
        final LocalDate newCheckIn = LocalDate.now();
        final LocalDate newCheckOut = newCheckIn.plusDays(1);

        //WHEN
        boolean isEqual = compareReserveDates(currentCheckIn, currentCheckOut, newCheckIn, newCheckOut);

        //THEN
        Assertions.assertThat(isEqual).isTrue();
    }

    @Test
    public void shouldCompareReserveDatesWhenCurrentCheckInIsDifferentFromNewCheckIn(){
        //GIVEN
        final LocalDate currentCheckIn = LocalDate.now();
        final LocalDate currentCheckOut = currentCheckIn.plusDays(2);
        final LocalDate newCheckIn = LocalDate.now().plusDays(1);
        final LocalDate newCheckOut = currentCheckIn.plusDays(2);

        //WHEN
        boolean isEqual = compareReserveDates(currentCheckIn, currentCheckOut, newCheckIn, newCheckOut);

        //THEN
        Assertions.assertThat(isEqual).isFalse();
    }

    @Test
    public void shouldCompareReserveDatesWhenCurrentCheckOutIsDifferentFromNewCheckOut(){
        //GIVEN
        final LocalDate currentCheckIn = LocalDate.now();
        final LocalDate currentCheckOut = currentCheckIn.plusDays(2);
        final LocalDate newCheckIn = LocalDate.now();
        final LocalDate newCheckOut = newCheckIn.plusDays(1);

        //WHEN
        boolean isEqual = compareReserveDates(currentCheckIn, currentCheckOut, newCheckIn, newCheckOut);

        //THEN
        Assertions.assertThat(isEqual).isFalse();
    }
}
