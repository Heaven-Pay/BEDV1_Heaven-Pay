package com.programmers.heavenpay.review.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.product.entitiy.Product;

import javax.persistence.*;
import java.util.Objects;

@Entity
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

    public void updateInfo(String content, int score) {
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

    public static Review.ReviewBuilder builder() {
        return new Review.ReviewBuilder();
    }

    public static class ReviewBuilder {
        private Long id;

        private int score;

        private String content;

        private Product product;

        private Member reviewer;

        ReviewBuilder() {
        }

        public Review.ReviewBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public Review.ReviewBuilder score(final int score) {
            this.score = score;
            return this;
        }

        public Review.ReviewBuilder content(final String content) {
            this.content = content;
            return this;
        }

        public Review.ReviewBuilder product(final Product product) {
            this.product = product;
            return this;
        }

        public Review.ReviewBuilder reviewer(final Member reviewer) {
            this.reviewer = reviewer;
            return this;
        }

        public Review build() {
            return new Review(
                    this.id,
                    this.score,
                    this.content,
                    this.product,
                    this.reviewer
            );
        }
    }
}
