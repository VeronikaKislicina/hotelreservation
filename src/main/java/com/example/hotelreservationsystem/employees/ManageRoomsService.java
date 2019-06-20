package com.example.hotelreservationsystem.employees;

import com.example.hotelreservationsystem.reservation.ReservationEntity;
import com.example.hotelreservationsystem.reservation.ReservationRepository;
import com.example.hotelreservationsystem.rooms.RoomsDTO;
import com.example.hotelreservationsystem.rooms.RoomsEntity;
import com.example.hotelreservationsystem.rooms.RoomsMapper;
import com.example.hotelreservationsystem.rooms.RoomsRepository;
import com.example.hotelreservationsystem.status.Status;
import com.example.hotelreservationsystem.status.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ManageRoomsService {
    private RoomsRepository roomsRepository;
    private RoomsMapper roomsMapper;
    private ReservationRepository reservationRepository;

    @Autowired
    public ManageRoomsService(RoomsRepository roomsRepository, RoomsMapper roomsMapper,
                              ReservationRepository reservationRepository) {
        this.roomsRepository = roomsRepository;
        this.roomsMapper = roomsMapper;
        this.reservationRepository = reservationRepository;
    }

    public Iterable<RoomsEntity> getAllRooms() {
        return roomsRepository.findAll();
    }

    StatusDTO statusDTO = new StatusDTO();

    @Transactional
    public StatusDTO addRoom(RoomsDTO roomsDTO) {
        RoomsEntity newRoom = roomsMapper.addRoomsDTOtoEntity(roomsDTO);
        roomsRepository.save(newRoom);

        statusDTO.setStatus(Status.APPROVED);
        statusDTO.setDetails("Room has been successfully added");
        return statusDTO;
    }

    @Transactional
    public StatusDTO deleteRoomById(Long id) {
        roomsRepository.deleteById(id);

        statusDTO.setStatus(Status.APPROVED);
        statusDTO.setDetails("Room with id " + id + " was deleted successfully");
        return statusDTO;
    }

    @Transactional
    public StatusDTO updateRoom(Long id, RoomsDTO roomsDTO) {

        Optional<RoomsEntity> roomsEntity = roomsRepository.findById(id);
        if (roomsEntity.isPresent()) {
            RoomsEntity room = roomsEntity.get();

            room.setAvailable(roomsDTO.getAvailable());
            room.setRoomNumber(roomsDTO.getRoomNumber());
            roomsRepository.save(room);
        }

        statusDTO.setStatus(Status.APPROVED);
        statusDTO.setDetails("Room has been updated");

        return statusDTO;
    }

    @Transactional
    public String seeSchedule(Integer roomNumber) {
        List<ReservationEntity> allByRoomsEntityRoomNumber = reservationRepository.findAllByRoomsEntityRoomNumber(roomNumber);
        String response = "";

        for (ReservationEntity reservationEntity : allByRoomsEntityRoomNumber) {
            response += "\nRoom number " +
                    reservationEntity.getRoomsEntity().getRoomNumber() +
                    " booked for "
                    +
                    reservationEntity.getClientName()
            ;
        }
        return response;
    }
}