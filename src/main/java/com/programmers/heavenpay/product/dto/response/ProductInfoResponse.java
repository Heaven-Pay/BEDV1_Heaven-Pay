package com.programmers.heavenpay.product.dto.response;

public class ProductInfoResponse {
    private final Long id;

    private final String category;

    private final Integer price;

    private final String title;

    private final String description;

    private final String s3Path;

    private final Integer stock;

    private final Long storeId;

    private final Double score;

    public ProductInfoResponse(Long id, String category, int price, String title, String description, String s3Path, int stock, Long storeId, Double score) {
        this.id = id;
        this.category = category;
        this.price = price;
        this.title = title;
        this.description = description;
        this.s3Path = s3Path;
        this.stock = stock;
        this.storeId = storeId;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public Integer getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getS3Path() {
        return s3Path;
    }

    public Integer getStock() {
        return stock;
    }

    public Long getStoreId() {
        return storeId;
    }

    public Double getScore() {
        return score;
    }

    public static ProductInfoResponse.ProductInfoResponseBuilder builder() {
        return new ProductInfoResponse.ProductInfoResponseBuilder();
    }

    public static class ProductInfoResponseBuilder {
        private Long id;

        private String category;

        private Integer price;

        private String title;

        private String description;

        private String s3Path;

        private Integer stock;

        private Long storeId;

        private Double score;

        ProductInfoResponseBuilder() {
        }

        public ProductInfoResponse.ProductInfoResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public ProductInfoResponse.ProductInfoResponseBuilder category(final String category) {
            this.category = category;
            return this;
        }

        public ProductInfoResponse.ProductInfoResponseBuilder price(final Integer price) {
            this.price = price;
            return this;
        }

        public ProductInfoResponse.ProductInfoResponseBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public ProductInfoResponse.ProductInfoResponseBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public ProductInfoResponse.ProductInfoResponseBuilder s3Path(final String s3Path) {
            this.s3Path = s3Path;
            return this;
        }

        public ProductInfoResponse.ProductInfoResponseBuilder stock(final Integer stock) {
            this.stock = stock;
            return this;
        }

        public ProductInfoResponse.ProductInfoResponseBuilder storeId(final Long storeId) {
            this.storeId = storeId;
            return this;
        }

        public ProductInfoResponse.ProductInfoResponseBuilder score(final Double score) {
            this.score = score;
            return this;
        }

        public ProductInfoResponse build() {
            return new ProductInfoResponse(
                    this.id,
                    this.category,
                    this.price,
                    this.title,
                    this.description,
                    this.s3Path,
                    this.stock,
                    this.storeId,
                    this.score
            );
        }
    }
}
