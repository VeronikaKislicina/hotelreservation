package com.example.hotelreservationsystem.clients;

import com.example.hotelreservationsystem.reservation.ReservationDTO;
import com.example.hotelreservationsystem.reservation.ReservationEntity;
import com.example.hotelreservationsystem.reservation.ReservationRepository;
import com.example.hotelreservationsystem.rooms.RoomsEntity;
import com.example.hotelreservationsystem.rooms.RoomsRepository;
import com.example.hotelreservationsystem.status.ReservationStatusDTO;
import com.example.hotelreservationsystem.status.Status;
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
    public ReservationStatusDTO bookRoom(ReservationDTO reservationDTO) {

        List<ReservationEntity> reservations =
                reservationRepository.findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual
                        (reservationDTO.getStartDate(), reservationDTO.getEndDate());

        if (reservations.size() >= roomsRepository.count()) {
            ReservationStatusDTO reservationStatusDTO = new ReservationStatusDTO();
            reservationStatusDTO.setStatus(Status.REJECTED);
            reservationStatusDTO.setDetails("All rooms are booked for this period.");
            return reservationStatusDTO;
        }
        List<Integer> occupiedRoomNumbers = new ArrayList<>();

        ReservationStatusDTO reservationStatusDTO = makeReservation(reservationDTO, reservations, occupiedRoomNumbers);

        return reservationStatusDTO;
    }

    private ReservationStatusDTO makeReservation(ReservationDTO reservationDTO, List<ReservationEntity> reservations,
                                                 List<Integer> occupiedRoomNumbers) {

        for (ReservationEntity reservation : reservations) {
            occupiedRoomNumbers.add(reservation.getRoomsEntity().getRoomNumber());
        }

        Integer roomNumber = occupiedRoomNumbers.size();

        ReservationStatusDTO reservationStatusDTO = new ReservationStatusDTO();

        if (reservations.size() < roomsRepository.count()) {
            ReservationEntity reservationEntity = new ReservationEntity();

            RoomsEntity room = roomsRepository.findByRoomNumber(roomNumber);

            reservationEntity.setRoomsEntity(room);
            reservationEntity.setStartDate(reservationDTO.getStartDate());
            reservationEntity.setEndDate(reservationDTO.getEndDate());
            reservationEntity.setClientName(reservationDTO.getClientName());

            reservationStatusDTO.setReservationDTO(reservationDTO);

            ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);

            reservationDTO.setId(savedReservationEntity.getId());

            if (room.getReservations() == null) {
                room.setReservations(new ArrayList<>());
            }
            room.getReservations().add(reservationEntity);
            roomsRepository.save(room);

            reservationStatusDTO.setStatus(Status.APPROVED);
            reservationStatusDTO.setDetails("Booked success at: " + reservationEntity.getStartDate()
                    + " until: " + reservationEntity.getEndDate());
        } else {
            reservationStatusDTO.setStatus(Status.REJECTED);
            reservationStatusDTO.setDetails("All rooms are booked.");
        }
        return reservationStatusDTO;
    }

}
