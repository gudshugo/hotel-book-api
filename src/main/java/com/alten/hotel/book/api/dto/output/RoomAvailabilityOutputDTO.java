package com.alten.hotel.book.api.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAvailabilityOutputDTO {

    @JsonProperty("room_id")
    private long roomId;

    @JsonProperty("available_dates")
    private Set<LocalDate> availableDates;

}
