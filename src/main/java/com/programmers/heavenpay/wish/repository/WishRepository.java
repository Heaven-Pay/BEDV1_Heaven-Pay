package com.programmers.heavenpay.wish.repository;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.wish.entity.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    @Override
    <S extends Wish> S save(S entity);

    @Override
    Optional<Wish> findById(Long aLong);

    @Override
    void deleteById(Long aLong);

    Page<Wish> findAllByMember(Member member, Pageable pageable);
}
