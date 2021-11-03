package com.programmers.heavenpay.error;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
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
}