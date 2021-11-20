package com.alten.hotel.book.api.utility;

/**
 * Utility class with constants used by other classes of the project.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

public class Constants {

    private Constants() {
    }

    public static final long RESERVATION_MAX_SPENT_TIME = 3;
    public static final long RESERVATION_MAX_RANGE_OF_DAYS = 30;
    public static final String ROOT_URL_RESERVATION_CONTROLLER = "/api/v1/reservation";
    public static final String ROOT_URL_ROOM_CONTROLLER = "/api/v1/room";
    public static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";

}
