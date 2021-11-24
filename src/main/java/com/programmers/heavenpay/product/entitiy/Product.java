package com.programmers.heavenpay.product.entitiy;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotDefinitionException;
import com.programmers.heavenpay.product.entitiy.vo.Category;
import com.programmers.heavenpay.product.entitiy.vo.Reviews;
import com.programmers.heavenpay.error.exception.LackStockException;
import com.programmers.heavenpay.store.entity.Store;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DynamicInsert
@DynamicUpdate
public class Product extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", unique = true)
    private Long id;

    @Column(name = "product_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "product_price", nullable = false)
    private Integer price;

    @Column(name = "product_title", nullable = false, length = 70)
    private String title;

    @Lob
    @Column(name = "product_description")
    private String description;

    @Column(name = "product_s3_path", columnDefinition = "TEXT")
    //@ColumnDefault("'EMPTY_URL'")  //TODO: 에러발생..
    private String s3Path;

    @Column(name = "product_stock", nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    private Store store;

    @Embedded
    private Reviews reviews;

    protected Product(){
    }

    public Product(Long id, Category category, Integer price, String title, String description, String s3Path, Integer stock, Store store, Reviews reviews) {
        this.id = id;
        this.category = category;
        this.price = price;
        this.title = title;
        this.description = description;
        this.s3Path = s3Path;
        this.stock = stock;
        this.store = store;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
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

    public String getS3Path() {
        return s3Path;
    }

    public int getStock() {
        return stock;
    }

    public Store getStore() {
        return store;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public synchronized void updateInfos(String description, int price, String s3Path, String title, String categoryStr, int stock) {
        this.description = description;
        this.price = price;
        this.s3Path = s3Path;
        this.title = title;
        this.category = Category.of(categoryStr);
        this.stock = stock;
    }

    public synchronized void updateS3Path(String s3Url) {
        this.s3Path = s3Url;
    }

    public synchronized void addStock() {
        stock++;
    }

    public synchronized void subtractStock() {
        if (stock <= 0) {
            throw new LackStockException(ErrorMessage.NOT_ENOUGH_STOCK);
        }

        stock--;
    }

    public void checkValidStoreOrElseThrow(Long storeId) {
        if(this.store.getId() != storeId){
            throw new NotDefinitionException(ErrorMessage.MISMATCH_BETWEEN_PRODUCT_AND_STORE);
        }
    }

    /**
     * 연관관계 편의 메소드: Store와 Product 연결
     */
    public synchronized void makeRelationWithStore(Store store) {
        if (Objects.nonNull(this.store)) {
            this.store.getProducts().remove(this);
        }

        this.store = store;
        this.store.getProducts().add(this);
    }

    /**
     * 연관관계 편의 메소드: Store에서 Product 단건 삭제
     */
    public synchronized void deleteFromStore() {
        this.store.getProducts().remove(this);
    }

    // builder blow //

    public static Product.ProductBuilder builder() {
        return new Product.ProductBuilder();
    }

    public static class ProductBuilder {
        private Long id;

        private Category category;

        private Integer price;

        private String title;

        private String description;

        private String s3Path;

        private Integer stock;

        private Store store;

        private Reviews reviews;

        ProductBuilder() {
        }

        public Product.ProductBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public Product.ProductBuilder category(final Category category) {
            this.category = category;
            return this;
        }

        public Product.ProductBuilder price(final Integer price) {
            this.price = price;
            return this;
        }

        public Product.ProductBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public Product.ProductBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public Product.ProductBuilder s3Path(final String s3Path) {
            this.s3Path = s3Path;
            return this;
        }

        public Product.ProductBuilder stock(final Integer stock) {
            this.stock = stock;
            return this;
        }

        public Product.ProductBuilder store(final Store store) {
            this.store = store;
            return this;
        }

        public Product.ProductBuilder reviews(final Reviews reviews) {
            this.reviews = reviews;
            return this;
        }

        public Product build() {
            return new Product(
                    this.id,
                    this.category,
                    this.price,
                    this.title,
                    this.description,
                    this.s3Path,
                    this.stock,
                    this.store,
                    this.reviews
            );
        }
    }
}
