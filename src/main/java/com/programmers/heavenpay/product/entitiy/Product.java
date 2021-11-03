package com.programmers.heavenpay.product.entitiy;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotDefinitionException;
import com.programmers.heavenpay.product.entitiy.vo.Category;
import com.programmers.heavenpay.product.entitiy.vo.Reviews;
import com.programmers.heavenpay.product.exception.LackStockException;
import com.programmers.heavenpay.store.entity.Store;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    private int price;

    @Column(name = "product_title", nullable = false, length = 70)
    private String title;

    @Lob
    @Column(name = "product_description")
    private String description;

    @Column(name = "product_s3_path", columnDefinition = "TEXT")
    //@ColumnDefault("'EMPTY_URL'")  //TODO: 에러발생..
    private String s3Path;

    @Column(name = "product_stock", nullable = false)
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    private Store store;

    @Embedded
    private Reviews reviews;

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
            throw new LackStockException(ErrorMessage.LACK_OF_STOCK);
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
}
