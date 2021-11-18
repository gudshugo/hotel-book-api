package com.alten.hotel.book.api.repository;

import com.alten.hotel.book.api.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    Room findById(long id);

}
