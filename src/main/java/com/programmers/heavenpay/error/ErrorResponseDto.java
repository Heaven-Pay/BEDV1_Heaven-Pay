package com.programmers.heavenpay.error;

import java.time.LocalDateTime;

public class ErrorResponseDto {
    private final int status;
    private final String message;
    private final LocalDateTime serverDateTime;

    private ErrorResponseDto(ErrorMessage message) {
        this.status = message.getStatus().value();
        this.message = message.name();
        this.serverDateTime = LocalDateTime.now();
    }

    public static ErrorResponseDto of(ErrorMessage message) {
        return new ErrorResponseDto(message);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getServerDateTime() {
        return serverDateTime;
    }
}