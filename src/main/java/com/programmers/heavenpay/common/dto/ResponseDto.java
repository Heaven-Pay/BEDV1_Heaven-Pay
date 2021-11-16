package com.programmers.heavenpay.common.dto;

import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;

public class ResponseDto<T> {
    private final int status;
    private final String message;
    private final LocalDateTime serverDateTime;
    private final T data;
    private final T link;

    private ResponseDto(ResponseMessage message, T data, T link) {
        this.status = message.getStatus().value();
        this.message = message.name();
        this.serverDateTime = LocalDateTime.now();
        this.data = data;
        this.link = link;
    }

    public static <T> ResponseDto of(ResponseMessage message, T data, T link) {
        return new ResponseDto(message, data, link);
    }

    public static <T> ResponseDto of(ResponseMessage message, EntityModel<T> entityModel) {
        return new ResponseDto(message, entityModel.getContent(), entityModel.getLinks());
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

    public T getData() {
        return data;
    }

    public T getLink() {
        return link;
    }
}