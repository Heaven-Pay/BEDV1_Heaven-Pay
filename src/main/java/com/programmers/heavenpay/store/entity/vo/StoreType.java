package com.programmers.heavenpay.store.entity.vo;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StoreType {
    FOOD("식품"),
    DEPARTMENT_STORE("백화점"),
    LIVING("리빙"),
    GIFT_CARD("상품권"),
    BAKERY("베이커리"),
    ELECTRONIC("전자제품"),
    CLOTHES("의류"),
    COSMETICS("화장품"),
    SPORT("스포츠");

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
