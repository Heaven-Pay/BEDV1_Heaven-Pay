package com.programmers.heavenpay.store.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StoreUpdateResponse {
    private final Long id;

    private final String name;

    private final String type;

    private final String vendorCode;
}
