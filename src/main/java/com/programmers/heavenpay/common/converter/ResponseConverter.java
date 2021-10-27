package com.programmers.heavenpay.common.converter;

import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.ErrorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class ResponseConverter {
    public ResponseEntity<ResponseDto> toResponseEntity(HttpStatus status, ResponseMessage message, EntityModel model) {
        return ResponseEntity
                .status(status)
                .body(ResponseDto.of(message, model));
    }

    public <T> ResponseEntity<ResponseDto> toResponseEntity(HttpStatus status, ResponseMessage message, Page<T> pages, Link link) {
        return ResponseEntity
                .status(status)
                .body(ResponseDto.of(message, pages, link));
    }

    public ResponseEntity<ErrorResponseDto> toResponseEntity(HttpStatus status, ErrorMessage message) {
        return ResponseEntity
                .status(status)
                .body(ErrorResponseDto.of(message));
    }
}