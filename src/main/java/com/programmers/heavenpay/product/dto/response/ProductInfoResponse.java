package com.programmers.heavenpay.product.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductInfoResponse {
    private Long id;

    private String category;

    private int price;

    private String title;

    private String description;

    private String s3Path;

    private int stock;

    private Long storeId;

    private Double score;
}
