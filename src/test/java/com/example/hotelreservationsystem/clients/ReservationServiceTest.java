package com.example.hotelreservationsystem.clients;

import com.example.hotelreservationsystem.reservation.ReservationDTO;
import com.example.hotelreservationsystem.reservation.ReservationEntity;
import com.example.hotelreservationsystem.reservation.ReservationRepository;
import com.example.hotelreservationsystem.rooms.RoomsEntity;
import com.example.hotelreservationsystem.rooms.RoomsRepository;
import com.example.hotelreservationsystem.status.ReservationStatusDTO;
import com.example.hotelreservationsystem.status.Status;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    ReservationRepository mockReservationRepository;

    @Mock
    RoomsRepository roomsRepository;

    @Test
    public void shouldBookRoom() {

        List<ReservationEntity> reservations = new ArrayList<>();

        ReservationDTO reservationDTO = new ReservationDTO();
        LocalDateTime startDate = LocalDateTime.now();
        reservationDTO.setStartDate(startDate);
        LocalDateTime endDate = startDate.plusDays(1);
        reservationDTO.setEndDate(endDate);
        RoomsEntity room = new RoomsEntity();
        room.setAvailable(true);
        room.setRoomNumber(0);

        when(mockReservationRepository.findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate)).thenReturn(reservations);
        when(roomsRepository.findByRoomNumber(0)).thenReturn(room);
        when(roomsRepository.count()).thenReturn(2L);

        ReservationEntity savedReservationEntity = new ReservationEntity();
        savedReservationEntity.setId(777L);
        when(mockReservationRepository.save(any())).thenReturn(savedReservationEntity);

        ReservationStatusDTO status = reservationService.bookRoom(reservationDTO);

        Assert.assertEquals(Status.APPROVED, status.getStatus());
    }

    @Test
    public void shouldntBookRoom() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(1);

        List<ReservationEntity> reservationEntities = new ArrayList<>();
        for (int i = 0; i < roomsRepository.count(); i++) {
            ReservationEntity reservation = new ReservationEntity();
            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            reservationEntities.add(reservation);
        }

        RoomsEntity room = new RoomsEntity();
        room.setAvailable(true);
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setStartDate(startDate);
        reservationDTO.setEndDate(endDate);
        when(mockReservationRepository.findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate)).thenReturn(reservationEntities);


        ReservationStatusDTO status = reservationService.bookRoom(reservationDTO);

        Assert.assertEquals(Status.REJECTED, status.getStatus());
    }

}


