package com.programmers.heavenpay.store.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class StoreCreateRequest {
    @Pattern(regexp = "\\S{2,10}", message = "store name은 공백없는 2~10자이어야 합니다")
    private String name;

    @NotBlank(message = "store type validation fail")
    private String type;

    @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "vendor code validation fail")
    private String vendorCode;
}
