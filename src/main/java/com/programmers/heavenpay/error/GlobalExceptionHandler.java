package com.programmers.heavenpay.error;

import com.programmers.heavenpay.error.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException exception) {
        ErrorMessage message = exception.getErrorMessage();
        ErrorResponseDto response = ErrorResponseDto.of(message);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodValidException(MethodArgumentNotValidException exception) {
        ErrorMessage message = ErrorMessage.valueOf(exception.getMessage());
        ErrorResponseDto response = ErrorResponseDto.of(message);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException() {
        ErrorResponseDto response = ErrorResponseDto.of(ErrorMessage.INTERNAL_SERVER_ERROR);
        return ResponseEntity.ok(response);
    }
}
