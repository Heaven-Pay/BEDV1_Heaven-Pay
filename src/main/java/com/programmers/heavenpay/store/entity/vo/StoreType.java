package com.programmers.heavenpay.store.entity.vo;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StoreType {
    DEFAULT("미정"),
    FINANCIAL("금융업"),
    RESTAURANT("식품업"),
    RETAIL_BUSINESS("소매업"),
    TRAVEL("여행업"),
    RENTAL_RETAIL_BUSINESS("대여업"),
    SERVICE_BUSINESS("서비스업");

    private final String storeType;

    StoreType(String storeType) {
        this.storeType = storeType;
    }

    public static StoreType of(String storeType) {
        return Arrays.stream(StoreType.values())
                .filter(v -> v.storeType.equals(storeType))
                .findFirst()
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_STORE_TYPE));
    }
}
