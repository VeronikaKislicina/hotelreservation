package com.example.hotelreservationsystem.availability;

import com.example.hotelreservationsystem.reservation.ReservationDTO;
import com.example.hotelreservationsystem.reservation.ReservationRepository;
import com.example.hotelreservationsystem.rooms.RoomsRepository;
import com.example.hotelreservationsystem.status.Status;
import com.example.hotelreservationsystem.status.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AvailabilityService {

    ReservationRepository reservationRepository;
    RoomsRepository roomsRepository;

    @Autowired
    public AvailabilityService(ReservationRepository reservationRepository, RoomsRepository roomsRepository) {
        this.reservationRepository = reservationRepository;
        this.roomsRepository = roomsRepository;
    }

    @Transactional
    public StatusDTO checkAvailability(ReservationDTO reservationDTO) {
        Integer occupiedRoomsNumber =
                reservationRepository.countByStartDateGreaterThanEqualAndEndDateLessThanEqual(reservationDTO.getStartDate(),
                        reservationDTO.getEndDate());
        long roomAmountNumber = roomsRepository.count();
        if (occupiedRoomsNumber >= roomAmountNumber) {
            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setStatus(Status.REJECTED);
            statusDTO.setDetails("All rooms are booked for this period.");
            return statusDTO;
        } else {
            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setStatus(Status.APPROVED);
            statusDTO.setDetails("There are " + (roomAmountNumber - occupiedRoomsNumber) + " empty rooms.");
            return statusDTO;
        }
    }
}
