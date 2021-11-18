package com.alten.hotel.book.api.service.impl;

import com.alten.hotel.book.api.exception.ElementNotFoundException;
import com.alten.hotel.book.api.model.Room;
import com.alten.hotel.book.api.repository.RoomRepository;
import com.alten.hotel.book.api.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room findById(long id) {
        Optional<Room> room = roomRepository.findById(id);

        if(room.isPresent()){
            return room.get();
        }

        throw new ElementNotFoundException(String.format("Room with id: %d not found", id));
    }

}
