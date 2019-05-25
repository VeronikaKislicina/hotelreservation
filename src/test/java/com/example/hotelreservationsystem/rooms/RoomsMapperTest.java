package com.example.hotelreservationsystem.rooms;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class RoomsMapperTest {

    @Test
    public void shouldMapRoomsDTOToEntity() {
        RoomsMapper roomsMapper = new RoomsMapper();
        RoomsDTO roomsDTO = new RoomsDTO();

//        roomsDTO.setStartDate(LocalDateTime.now());
//        roomsDTO.setEndDate(LocalDateTime.now().plusDays(10));
//        roomsDTO.setClientName("Name");
        roomsDTO.setRoomNumber(10);

        RoomsEntity room = roomsMapper.addRoomsDTOtoEntity(roomsDTO);

//        assertTrue(room.getStartDate().equals(roomsDTO.getStartDate()));
//        assertTrue(room.getEndDate().equals(roomsDTO.getEndDate()));
//        assertTrue(room.getClientName().equals(roomsDTO.getClientName()));
        assertTrue(room.getRoomNumber().equals(roomsDTO.getRoomNumber()));

    }

}