package com.example.hotelreservationsystem.rooms;

import javax.validation.constraints.NotNull;

public class RoomsDTO {
    @NotNull
    private Boolean available;

    @NotNull
    private Integer roomNumber;

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }
}
