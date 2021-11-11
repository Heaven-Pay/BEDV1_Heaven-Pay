package com.programmers.heavenpay.product.converter;

import com.programmers.heavenpay.product.dto.response.ProductCreateResponse;
import com.programmers.heavenpay.product.dto.response.ProductDeleteResponse;
import com.programmers.heavenpay.product.dto.response.ProductInfoResponse;
import com.programmers.heavenpay.product.dto.response.ProductUpdateResponse;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.product.entitiy.vo.Category;
import org.springframework.stereotype.Component;

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

    public ProductCreateResponse toProductCreateResponse(Product product) {
        return new ProductCreateResponse(product.getId(), product.getS3Path().equals(EMPTY) ? EMPTY : product.getS3Path(), product.getCreatedDate());
    }

    public ProductInfoResponse toProductInfoResponse(Product product) {
        return ProductInfoResponse.builder()
                .category(product.getCategory().getProductCategory())
                .description(product.getDescription())
                .id(product.getId())
                .price(product.getPrice())
                .s3Path(product.getS3Path().equals(EMPTY) ? EMPTY : product.getS3Path())
                .score(product.getReviews().getScore())
                .stock(product.getStock())
                .storeId(product.getStore().getId())
                .title(product.getTitle())
                .build();
    }

    public ProductDeleteResponse toProductDeleteResponse(Long id) {
        return new ProductDeleteResponse(id);
    }

    public ProductUpdateResponse toProductUpdateResponse(Product product) {
        return ProductUpdateResponse.builder()
                .id(product.getId())
                .createdAt(product.getCreatedDate())
                .modifiedAt(product.getModifiedDate())
                .s3Path(product.getS3Path().equals(EMPTY) ? EMPTY : product.getS3Path())
                .build();
    }
}
