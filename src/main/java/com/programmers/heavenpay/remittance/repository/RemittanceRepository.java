package com.programmers.heavenpay.remittance.repository;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.remittance.entity.Remittance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RemittanceRepository extends JpaRepository<Remittance, Long> {
    Optional<Remittance> findByIdAndMember(Long id, Member member);

    Page<Remittance> findByMember(Member member, Pageable pageable);
}
