package com.programmers.heavenpay.product.converter;

import com.programmers.heavenpay.product.dto.response.ProductCreateResponse;
import com.programmers.heavenpay.product.dto.response.ProductInfoResponse;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.product.entitiy.vo.Category;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductConverter {
    private final static String EMPTY = "EMPTY";

    public Product toProductEntity(String categoryStr, int price, String title, String description, int stock) {
        return Product.builder()
                .category(Category.of(categoryStr))
                .description(description)
                .price(price)
                .stock(stock)
                .title(title)
                .build();
    }

    public ProductCreateResponse toProductCreateResponse(Long id, LocalDateTime createdDate, String s3Path) {
        return ProductCreateResponse.builder()
                .createdAt(createdDate)
                .s3Path(s3Path)
                .id(id)
                .build();
    }

    public ProductInfoResponse toProductInfoResponse(Product product) {
        String s3Path = product.getS3Path().equals(EMPTY) ? EMPTY : product.getS3Path();

        return ProductInfoResponse.builder()
                .category(product.getCategory().getProductCategory())
                .description(product.getDescription())
                .id(product.getId())
                .price(product.getPrice())
                .s3Path(s3Path)
                .score(product.getReviews().getScore())
                .stock(product.getStock())
                .storeId(product.getStore().getId())
                .title(product.getTitle())
                .build();
    }
}
