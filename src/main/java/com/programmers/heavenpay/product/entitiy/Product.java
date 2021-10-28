package com.programmers.heavenpay.product.entitiy;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.product.entitiy.vo.Category;
import com.programmers.heavenpay.product.exception.LackStockException;
import com.programmers.heavenpay.store.entity.Store;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "product_score", nullable = false)
    private double score;

    @Column(name = "product_price", nullable = false)
    private int price;

    @Column(name = "product_title", nullable = false, length = 70)
    private String title;

    @Lob
    @Column(name = "product_description")
    private String description;

    @Column(name = "product_s3_path", columnDefinition = "TEXT")
    private String s3Path;

    @Column(name = "product_stock", nullable = false)
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public synchronized void updatePrice(int price) {
        this.price = price;
    }

    public synchronized void updateDescription(String description) {
        this.description = description;
    }

    public synchronized void updateDescriptionPath(String s3Path) {
        this.s3Path = s3Path;
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

    public synchronized void updateReviewScore() {
        double scoreSum = 0;
        for (int i = 0; i < reviews.size(); i++) {  //TODO: stream으로 바꾸기
            scoreSum += reviews.get(i).getScore();
        }
        score = scoreSum / reviews.size();
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
