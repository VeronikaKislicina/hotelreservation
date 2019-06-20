package com.example.hotelreservationsystem.rooms;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RoomsMapperTest {

    @Test
    public void shouldMapRoomsDTOToEntity() {
        RoomsMapper roomsMapper = new RoomsMapper();
        RoomsDTO roomsDTO = new RoomsDTO();
        roomsDTO.setRoomNumber(10);

        RoomsEntity room = roomsMapper.addRoomsDTOtoEntity(roomsDTO);

        assertTrue(room.getRoomNumber().equals(roomsDTO.getRoomNumber()));

    }

}