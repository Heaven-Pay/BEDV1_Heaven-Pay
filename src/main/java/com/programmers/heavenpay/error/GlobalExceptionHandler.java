package com.programmers.heavenpay.error;

import com.programmers.heavenpay.error.exception.DuplicationException;
import com.programmers.heavenpay.error.exception.NotDefinitionException;
import com.programmers.heavenpay.product.exception.LackStockException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotDefinitionException.class)
    protected ResponseEntity<ErrorResponseDto> handleNotDefinitionException(NotDefinitionException exception) {
        return makeErrorResponse(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> methodValidException(MethodArgumentNotValidException exception){
        return makeErrorResponse(exception);
    }

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ErrorResponseDto> duplicateDataException(DuplicationException exception){
        return makeErrorResponse(exception);
    }

    @ExceptionHandler(LackStockException.class)
    public ResponseEntity<ErrorResponseDto> duplicateDataException(LackStockException exception){
        return makeErrorResponse(exception);
    }

    // TODO 자동화
    private ResponseEntity<ErrorResponseDto> makeErrorResponse(Exception exception) {
        ErrorMessage message = ErrorMessage.valueOf(exception.getMessage());
        ErrorResponseDto response = ErrorResponseDto.of(message);
        return ResponseEntity.ok(response);
    }
}
