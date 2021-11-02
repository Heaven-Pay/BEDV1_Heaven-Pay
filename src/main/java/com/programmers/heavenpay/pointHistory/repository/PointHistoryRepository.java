package com.programmers.heavenpay.pointHistory.repository;

import com.programmers.heavenpay.pointHistory.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    @Override
    Optional<PointHistory> findById(Long aLong);

    @Override
    List<PointHistory> findAll();

    @Override
    PointHistory save(PointHistory entity);

    @Override
    void delete(PointHistory entity);
}
