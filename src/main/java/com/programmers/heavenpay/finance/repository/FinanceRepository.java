package com.programmers.heavenpay.finance.repository;

import com.programmers.heavenpay.finance.entity.Finance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long> {
    boolean existsByName(String name);

    @Override
    Finance save(Finance finance);

    @Override
    Optional<Finance> findById(Long id);

    @Override
    Page<Finance> findAll(Pageable pageable);

    @Override
    void deleteById(Long id);
}
