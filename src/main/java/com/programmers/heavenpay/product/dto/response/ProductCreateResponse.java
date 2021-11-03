package com.programmers.heavenpay.product.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ProductCreateResponse {
    private final Long id;

    private final String s3Path;

    private final LocalDateTime createdAt;
}
