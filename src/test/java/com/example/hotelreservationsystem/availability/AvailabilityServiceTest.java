package com.example.hotelreservationsystem.availability;

import com.example.hotelreservationsystem.reservation.ReservationEntity;
import com.example.hotelreservationsystem.reservation.ReservationRepository;
import com.example.hotelreservationsystem.rooms.RoomsRepository;
import com.example.hotelreservationsystem.status.Status;
import com.example.hotelreservationsystem.status.StatusDTO;
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

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AvailabilityServiceTest {

    @InjectMocks
    AvailabilityService availabilityService;

    @Mock
    ReservationRepository mockReservationRepository;

    @Mock
    RoomsRepository mockRoomsRepository;


    @Test
    public void shouldBeAvailable() {

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(1);
        List<ReservationEntity> reservationEntities = new ArrayList<>();
        AvailabilityDTO availabilityDTO = new AvailabilityDTO();
        availabilityDTO.setStartDate(startDate);
        availabilityDTO.setEndDate(endDate);

        when(mockReservationRepository.findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate)).thenReturn(reservationEntities);
        when(mockRoomsRepository.count()).thenReturn(2L);

        StatusDTO status = availabilityService.checkAvailability(availabilityDTO);

        Assert.assertEquals(Status.APPROVED, status.getStatus());
    }

    @Test
    public void shouldNotBeAvailable() {

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(1);
        AvailabilityDTO availabilityDTO = new AvailabilityDTO();

        availabilityDTO.setStartDate(startDate);
        availabilityDTO.setEndDate(endDate);
        when(mockReservationRepository.countByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate)).thenReturn(5);

        StatusDTO status = availabilityService.checkAvailability(availabilityDTO);

        Assert.assertEquals(Status.REJECTED, status.getStatus());
    }

}