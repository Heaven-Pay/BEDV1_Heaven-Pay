package com.programmers.heavenpay.product.entitiy.vo;

import com.programmers.heavenpay.review.entity.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reviews {
    @Column(name = "product_score", nullable = false)
    private Double score;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public synchronized void updateReviewScore() {
        double scoreSum = 0;
        for (Review review : reviews) {
            scoreSum += review.getScore();
        }
        score = scoreSum / reviews.size();
    }
}
