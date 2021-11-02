package com.programmers.heavenpay.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter  //@ModelAttribute를 이용해 파라미터 값을 DTO에 한 번에 바인딩 하기 위함
public class ProductCreateRequest {
    @Min(value = 1L)
    private Long storeID;

    @NotBlank(message = "product category validation fail")
    private String category;

    @Min(value = 0)
    private int price;

    @Pattern(regexp = "^\\S{5,70}$", message = "product title은 공백없는 5~70자이어야 합니다")
    private String title;

    @NotBlank(message = "product description must not be null or blank")
    private String description;

    @Min(value = 0)
    private int stock;

    private MultipartFile multipartFile;
}
