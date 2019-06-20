package com.example.hotelreservationsystem.employees;

import com.example.hotelreservationsystem.availability.AvailabilityDTO;
import com.example.hotelreservationsystem.availability.AvailabilityService;
import com.example.hotelreservationsystem.rooms.RoomsDTO;
import com.example.hotelreservationsystem.rooms.RoomsEntity;
import com.example.hotelreservationsystem.status.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admins")
public class ManageRoomsController {

    private ManageRoomsService manageRoomsService;
    private AvailabilityService availabilityService;

    @Autowired
    public ManageRoomsController(ManageRoomsService manageRoomsService, AvailabilityService availabilityService) {
        this.manageRoomsService = manageRoomsService;
        this.availabilityService = availabilityService;
    }

    @GetMapping("/rooms")
    public Iterable<RoomsEntity> rooms() {
        return manageRoomsService.getAllRooms();
    }

    @PostMapping("/rooms")
    public StatusDTO addRoom(@Valid @RequestBody RoomsDTO roomsDTO) {
        return manageRoomsService.addRoom(roomsDTO);
    }

    @DeleteMapping("/rooms/{id}")
    public StatusDTO deleteRoomById(@PathVariable("id") Long id) {
        return manageRoomsService.deleteRoomById(id);
    }

    @PutMapping("/rooms/{id}")
    public StatusDTO update(@Valid @PathVariable Long id, @RequestBody RoomsDTO roomsDTO) {
        return manageRoomsService.updateRoom(id, roomsDTO);
    }

    @GetMapping("/schedule/{roomNumber}")
    public String seeSchedule(@PathVariable Integer roomNumber) {
        return manageRoomsService.seeSchedule(roomNumber);
    }

    @PostMapping("/availability")
    public StatusDTO addRoom(@Valid @RequestBody AvailabilityDTO availabilityDTO) {
        return availabilityService.checkAvailability(availabilityDTO);
    }
}
