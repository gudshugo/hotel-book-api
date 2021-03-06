package com.alten.hotel.book.api.dto.input;

import com.alten.hotel.book.api.utility.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Data transfer class (DTO) that contains the room id and a Set of available check-in and check-out dates.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationInputDTO {

    @NotNull(message = "You must choose at least one room.")
    @JsonProperty("room_id")
    private long roomId;

    @NotNull(message = "The start date attribute can't have null or empty values.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = Constants.LOCAL_DATE_FORMAT)
    @JsonProperty("check_in")
    private LocalDate checkIn;

    @NotNull(message = "The end date attribute can't have null or empty values.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = Constants.LOCAL_DATE_FORMAT)
    @JsonProperty("check_out")
    private LocalDate checkOut;

}
