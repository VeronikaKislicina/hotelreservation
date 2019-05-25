package com.example.hotelreservationsystem.rooms;

public class RoomsDTO {
    private Boolean available;
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
