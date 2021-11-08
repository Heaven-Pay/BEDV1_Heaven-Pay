package com.programmers.heavenpay.payment.repository;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.payment.entity.Payment;
import com.programmers.heavenpay.pointWallet.entity.PointWallet;
import com.programmers.heavenpay.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Override
    Payment save(Payment entity);

    void deleteByIdAndMemberAndStoreAndPointWallet(Long id, Member member, Store store, PointWallet pointWallet);
}
