package com.example.hotelreservationsystem.clients;

import com.example.hotelreservationsystem.reservation.ReservationDTO;
import com.example.hotelreservationsystem.reservation.ReservationEntity;
import com.example.hotelreservationsystem.reservation.ReservationRepository;
import com.example.hotelreservationsystem.rooms.RoomsEntity;
import com.example.hotelreservationsystem.rooms.RoomsRepository;
import com.example.hotelreservationsystem.status.Status;
import com.example.hotelreservationsystem.status.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private RoomsRepository roomsRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomsRepository roomsRepository, ReservationRepository reservationRepository) {
        this.roomsRepository = roomsRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public StatusDTO bookRoom(ReservationDTO reservationDTO) {

        List<ReservationEntity> reservations =
                reservationRepository.findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual
                        (reservationDTO.getStartDate(), reservationDTO.getEndDate());

        if (reservations.size() >= roomsRepository.count()) {
            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setStatus(Status.REJECTED);
            statusDTO.setDetails("All rooms are booked for this period.");
            return statusDTO;
        }
        List<Integer> occupiedRoomNumbers = new ArrayList<>();

        StatusDTO statusDTO = makeReservation(reservationDTO, reservations, occupiedRoomNumbers);

        return statusDTO;
    }

    private StatusDTO makeReservation(ReservationDTO reservationDTO, List<ReservationEntity> reservations,
                                      List<Integer> occupiedRoomNumbers) {

        for (ReservationEntity reservation : reservations) {
            occupiedRoomNumbers.add(reservation.getRoomsEntity().getRoomNumber());
        }

        Integer roomNumber = occupiedRoomNumbers.size();

        StatusDTO statusDTO = new StatusDTO();

        if (reservations.size() < roomsRepository.count()) {
            ReservationEntity reservationEntity = new ReservationEntity();

            RoomsEntity room = roomsRepository.findByRoomNumber(roomNumber);

            reservationEntity.setRoomsEntity(room);
            reservationEntity.setStartDate(reservationDTO.getStartDate());
            reservationEntity.setEndDate(reservationDTO.getEndDate());
            reservationEntity.setClientName(reservationDTO.getClientName());

            reservationRepository.save(reservationEntity);

            if (room.getReservations() == null) {
                room.setReservations(new ArrayList<>());
            }
            room.getReservations().add(reservationEntity);
            roomsRepository.save(room);

            statusDTO.setStatus(Status.APPROVED);
            statusDTO.setDetails("Booked success at: " + reservationEntity.getStartDate()
                    + " until: " + reservationEntity.getEndDate());
        } else {
            statusDTO.setStatus(Status.REJECTED);
            statusDTO.setDetails("All rooms are booked.");
        }
        return statusDTO;
    }

}
