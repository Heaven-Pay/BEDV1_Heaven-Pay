package com.programmers.heavenpay.account.repository;

import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Override
    Account save(Account account);

    Optional<Account> findByIdAndMember(Long accountId, Member member);

    Page<Account> findAllByMember(Member member, Pageable pageable);

    void deleteByIdAndMember(Long accountId, Member member);
}
