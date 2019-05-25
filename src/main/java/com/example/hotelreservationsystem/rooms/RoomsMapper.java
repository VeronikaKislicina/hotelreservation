package com.example.hotelreservationsystem.rooms;

import org.springframework.stereotype.Component;

@Component
public class RoomsMapper {
    public RoomsEntity addRoomsDTOtoEntity(RoomsDTO roomsDTO) {
        RoomsEntity rooms = new RoomsEntity();

        rooms.setAvailable(roomsDTO.getAvailable());
        rooms.setRoomNumber(roomsDTO.getRoomNumber());

        return rooms;
    }
}
