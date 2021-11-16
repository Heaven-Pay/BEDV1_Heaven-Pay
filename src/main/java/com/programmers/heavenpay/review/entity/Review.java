package com.programmers.heavenpay.review.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.product.entitiy.Product;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Builder
public class Review extends BaseEntity<Long> {
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
    @JoinColumn(name = "reviewer_id", referencedColumnName = "member_id", nullable = false)
    private Member reviewer;

    public Review(Long id, int score, String content, Product product, Member reviewer) {
        this.id = id;
        this.score = score;
        this.content = content;
        this.product = product;
        this.reviewer = reviewer;
    }

    protected Review() { // @Entity는 생성자 바인딩
    }

    public void updateInfo(String content, int score){
        this.content = content;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getContent() {
        return content;
    }

    public Product getProduct() {
        return product;
    }

    public Member getReviewer() {
        return reviewer;
    }

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
