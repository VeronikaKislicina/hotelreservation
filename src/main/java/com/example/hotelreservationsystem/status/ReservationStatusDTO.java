package com.example.hotelreservationsystem.status;

import com.example.hotelreservationsystem.reservation.ReservationDTO;

public class ReservationStatusDTO {
    private Status status;
    private String details;
    private ReservationDTO reservationDTO;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ReservationDTO getReservationDTO() {
        return reservationDTO;
    }

    public void setReservationDTO(ReservationDTO reservationDTO) {
        this.reservationDTO = reservationDTO;
    }
}
