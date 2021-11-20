package com.alten.hotel.book.api.repository;

import com.alten.hotel.book.api.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface with the signatures of the methods implemented using CrudRepository to retrieve, save,
 * delete and change the data referring to Room class.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {}
