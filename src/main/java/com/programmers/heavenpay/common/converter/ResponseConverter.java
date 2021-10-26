package com.programmers.heavenpay.common.converter;

import com.programmers.heavenpay.common.dto.ResponseDto;
import com.programmers.heavenpay.common.dto.ResponseMessage;
import org.springframework.hateoas.EntityModel;
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
}
