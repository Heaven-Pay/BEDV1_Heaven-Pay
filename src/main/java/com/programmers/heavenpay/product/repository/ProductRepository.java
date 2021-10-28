package com.programmers.heavenpay.product.repository;

import com.programmers.heavenpay.product.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
