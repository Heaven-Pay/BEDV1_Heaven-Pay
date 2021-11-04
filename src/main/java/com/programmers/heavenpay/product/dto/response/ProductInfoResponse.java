package com.programmers.heavenpay.product.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductInfoResponse {
    private final Long id;

    private final String category;

    private final int price;

    private final String title;

    private final String description;

    private final String s3Path;

    private final int stock;

    private final Long storeId;

    private final Double score;
}
