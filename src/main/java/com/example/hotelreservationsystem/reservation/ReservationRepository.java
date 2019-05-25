package com.example.hotelreservationsystem.reservation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {

    List<ReservationEntity> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDateTime startDate, LocalDateTime endDate);

    Integer countByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDateTime startDate, LocalDateTime endDate);

    List<ReservationEntity> findAllByRoomsEntityRoomNumber(Integer roomNumber);
}
