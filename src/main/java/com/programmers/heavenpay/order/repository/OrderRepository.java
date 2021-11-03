package com.programmers.heavenpay.order.repository;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByMember(Member member, Pageable pageable);
}
