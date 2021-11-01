package com.programmers.heavenpay.product.entitiy.vo;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {
    FOOD("식품"),
    LUXURY("명품"),
    LIVING("리빙"),
    GIFT_CARD("상품권"),
    BAKERY("베이커리"),
    ELECTRONIC("전자제품"),
    CLOTHES("의류"),
    COSMETICS("화장품"),
    SPORT("스포츠"),
    FURNITURE("가구"),
    BOOK("도서");

    private final String productCategory;

    Category(String productCategory) {
        this.productCategory = productCategory;
    }

    public static Category of(String productCategory) {
        return Arrays.stream(Category.values())
                .filter(v -> v.productCategory.equals(productCategory))
                .findFirst()
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_PRODUCT_CATEGORY));
    }
}
