package com.programmers.heavenpay.product.entitiy.vo;

import com.programmers.heavenpay.review.entity.Review;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@DynamicInsert
@DynamicUpdate
public class Reviews {
    @Column(name = "product_score")
    @ColumnDefault("0.0")
    private Double score;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    protected Reviews(){
    }

    public Reviews(Double score, List<Review> reviews) {
        this.score = score;
        this.reviews = reviews;
    }

    public Double getScore() {
        return score;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public synchronized void updateReviewScore() {
        double scoreSum = 0;
        for (Review review : reviews) {
            scoreSum += review.getScore();
        }
        score = scoreSum / reviews.size();
    }

    public void deleteReview(Review review){
        this.reviews.remove(review);
    }

    public void addReview(Review review){
        this.reviews.add(review);
    }

}
