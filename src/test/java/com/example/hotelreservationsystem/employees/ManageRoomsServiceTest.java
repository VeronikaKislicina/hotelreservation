package com.example.hotelreservationsystem.employees;

import com.example.hotelreservationsystem.reservation.ReservationEntity;
import com.example.hotelreservationsystem.reservation.ReservationRepository;
import com.example.hotelreservationsystem.rooms.RoomsEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManageRoomsServiceTest {

    @InjectMocks
    ManageRoomsService manageRoomsService;

    @Mock
    ReservationRepository reservationRepository;


    @Test
    public void seeScheduleShouldReturnAllForRoom1() {

        List<ReservationEntity> reservations = new ArrayList<>();

        RoomsEntity roomsEntity = new RoomsEntity();
        ReservationEntity reservationEntity = new ReservationEntity();

        roomsEntity.setRoomNumber(1);

        reservationEntity.setClientName("Client Name");
        reservationEntity.setRoomsEntity(roomsEntity);
        reservations.add(reservationEntity);
        when(reservationRepository.findAllByRoomsEntityRoomNumber(1)).thenReturn(reservations);


        String schedule = manageRoomsService.seeSchedule(1);

        String expectedSchedule = "\nRoom number " + 1 + " booked for Client Name";
        assertEquals(expectedSchedule, schedule);
    }


}