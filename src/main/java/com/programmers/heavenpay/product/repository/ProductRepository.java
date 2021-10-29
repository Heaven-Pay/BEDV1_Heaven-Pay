package com.programmers.heavenpay.product.repository;

import com.programmers.heavenpay.product.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    <S extends Product> S save(S entity);

    @Override
    Optional<Product> findById(Long aLong);
}
