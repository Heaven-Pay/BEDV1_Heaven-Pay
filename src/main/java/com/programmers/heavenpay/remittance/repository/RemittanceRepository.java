package com.programmers.heavenpay.remittance.repository;

import com.programmers.heavenpay.remittance.entity.Remittance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemittanceRepository extends JpaRepository<Remittance, Long> {
}
