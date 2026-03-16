package com.lorenzo.rentalmanagement.common.exception;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, int status, String message) {

}
