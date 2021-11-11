package com.programmers.heavenpay.product.dto.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ProductUpdateRequest {
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

    protected ProductUpdateRequest(){
    }

    public ProductUpdateRequest(Long storeID, String category, int price, String title, String description, int stock, MultipartFile multipartFile) {
        this.storeID = storeID;
        this.category = category;
        this.price = price;
        this.title = title;
        this.description = description;
        this.stock = stock;
        this.multipartFile = multipartFile;
    }

    public Long getStoreID() {
        return storeID;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }
}
