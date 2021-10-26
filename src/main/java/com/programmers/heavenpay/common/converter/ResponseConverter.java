package com.programmers.heavenpay.common.converter;

import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseConverter {
    public <T> ResponseEntity<ResponseDto> toResponseEntity(ResponseMessage message, EntityModel<T> model) {
        return ResponseEntity.ok(
                ResponseDto.of(
                        message,
                        model
                )
        );
    }

    public <T> ResponseEntity<ResponseDto> toResponseEntity(HttpStatus status, ResponseMessage message, Page<T> pages, Link link) {
        return ResponseEntity
                .status(status)
                .body(ResponseDto.of(message, pages, link));
    }
}
