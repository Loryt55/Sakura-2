package com.lorenzo.rentalmanagement.property.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp; // quando è successo l'errore
    private int status;              // codice HTTP (404, 400, ecc.)
    private String message;          // messaggio leggibile

    public ErrorResponse(LocalDateTime timestamp, int status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
