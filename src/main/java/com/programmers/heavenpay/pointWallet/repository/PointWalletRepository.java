package com.programmers.heavenpay.pointWallet.repository;

import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.pointWallet.entity.PointWallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointWalletRepository extends JpaRepository<PointWallet, Long> {
    Optional<PointWallet> findByIdAndMemberAndAccount(Long pointWalletId, Member member, Account account);

    Page<PointWallet> findAllByMemberAndAccount(Member member, Account account, Pageable pageable);

    @Override
    PointWallet save(PointWallet entity);

    void deleteByIdAndMemberAndAccount(Long pointWalletId, Member member, Account account);
}
