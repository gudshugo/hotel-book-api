package com.alten.hotel.book.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Class for persistence and reading tha represents a hotel room.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ApiModel("Class for persistence and reading tha represents a hotel room.")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "room", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reservation> reservation;

}
