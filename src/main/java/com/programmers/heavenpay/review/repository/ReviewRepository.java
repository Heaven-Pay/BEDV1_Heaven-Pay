package com.programmers.heavenpay.review.repository;

import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Override
    <S extends Review> S save(S entity);

    @Override
    Page<Review> findAll(Pageable pageable);

    @Override
    Optional<Review> findById(Long aLong);

    @Override
    void delete(Review entity);

    Page<Review> findAllByProduct(Product product, Pageable pageable);
}
