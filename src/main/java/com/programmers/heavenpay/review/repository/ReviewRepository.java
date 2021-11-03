package com.programmers.heavenpay.review.repository;

import com.programmers.heavenpay.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Override
    <S extends Review> S save(S entity);
}
