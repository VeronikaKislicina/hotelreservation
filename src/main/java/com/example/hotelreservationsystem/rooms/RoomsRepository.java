package com.example.hotelreservationsystem.rooms;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface RoomsRepository extends CrudRepository<RoomsEntity, Long> {

    RoomsEntity findByRoomNumber(Integer roomNumber);
}
