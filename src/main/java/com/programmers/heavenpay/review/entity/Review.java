package com.programmers.heavenpay.review.entity;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.product.entitiy.Product;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id", unique = true)
    private Long id;

    @Column(name = "reivew_score", nullable = false)
    private int score;

    @Column(name = "reivew_content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", referencedColumnName = "member_id", nullable = false)
    private Member writer;

    /**
     * 연관관계 편의 메소드: Product와 Review 연결
     */
    public synchronized void makeRelationWithProduct(Product product) {
        if (Objects.nonNull(this.product)) {
            this.product.getReviews().deleteReview(this);
        }

        this.product = product;
        this.product.getReviews().addReview(this);
    }

    /**
     * 연관관계 편의 메소드: Product에서 Review 단건 삭제
     */
    public synchronized void deleteFromProduct() {
        this.product.getReviews().deleteReview(this);
    }
}
