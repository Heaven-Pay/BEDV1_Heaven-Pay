package com.programmers.heavenpay.pointHistory.repository;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.pointHistory.entity.PointHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    Optional<PointHistory> findByIdAndMember(Long pointHistoryId, Member member);

    Page<PointHistory> findAllByMember(Member member, Pageable pageable);

    @Override
    PointHistory save(PointHistory entity);

    void deleteByIdAndMember(Long pointHistoryId, Member member);
}
