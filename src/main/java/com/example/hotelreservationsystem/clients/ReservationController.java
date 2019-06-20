package com.example.hotelreservationsystem.clients;

import com.example.hotelreservationsystem.availability.AvailabilityDTO;
import com.example.hotelreservationsystem.availability.AvailabilityService;
import com.example.hotelreservationsystem.reservation.ReservationDTO;
import com.example.hotelreservationsystem.status.ReservationStatusDTO;
import com.example.hotelreservationsystem.status.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ReservationController {

    private ReservationService reservationService;
    private AvailabilityService availabilityService;

    @Autowired
    public ReservationController(ReservationService reservationService, AvailabilityService availabilityService) {
        this.reservationService = reservationService;
        this.availabilityService = availabilityService;
    }

    @PostMapping("/bookings")
    public ReservationStatusDTO createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        return reservationService.bookRoom(reservationDTO);
    }

    @PostMapping("/availability")
    public StatusDTO addRoom(@Valid @RequestBody AvailabilityDTO availabilityDTO) {
        return availabilityService.checkAvailability(availabilityDTO);
    }
}
