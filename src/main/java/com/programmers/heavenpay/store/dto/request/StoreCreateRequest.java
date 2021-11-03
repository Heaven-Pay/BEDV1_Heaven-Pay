package com.programmers.heavenpay.store.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class StoreCreateRequest {
    @Pattern(regexp = "^[ㄱ-ㅎ|가-힣|a-z|A-Z|]{2,10}$", message = "store name은 공백없는 2~10자이어야 합니다")
    private String name;

    @NotBlank(message = "store type validation fail")
    private String type;

    @Pattern(regexp = "^(\\S{3,3})+[-]+(\\S{2,2})+[-]+(\\S{5,5})", message = "사업자코드 형식이 올바르지 않습니다.")
    private String vendorCode;
}
