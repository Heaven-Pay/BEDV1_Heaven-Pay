package com.programmers.heavenpay.giftorder.repository;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.giftorder.entity.GiftOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftOrderRepository extends JpaRepository<GiftOrder, Long> {
    Page<GiftOrder> findAllByMember(Member member, Pageable pageable);
}
