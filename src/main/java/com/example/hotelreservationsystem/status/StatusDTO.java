package com.example.hotelreservationsystem.status;

import org.springframework.stereotype.Component;

@Component
public class StatusDTO {
    private Status status;
    private String details;

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
}
