package com.programmers.heavenpay.account.service;

import com.programmers.heavenpay.account.converter.AccountConverter;
import com.programmers.heavenpay.account.dto.response.*;
import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.account.repository.AccountRepository;
import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.repository.FinanceRepository;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private final AccountConverter accountConverter;
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private final FinanceRepository financeRepository;

    public AccountService(AccountConverter accountConverter, AccountRepository accountRepository, MemberRepository memberRepository, FinanceRepository financeRepository) {
        this.accountConverter = accountConverter;
        this.accountRepository = accountRepository;
        this.memberRepository = memberRepository;
        this.financeRepository = financeRepository;
    }

    @Transactional
    public AccountCreateResponse create(Long memberId, String title, String description, String number, Long financeId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        Finance finance = financeRepository.findById(financeId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_FINANCE)
                );

        Account accountInstance = accountConverter.toAccountEntity(title, description, number, member, finance);
        accountInstance.addCreatedAndLastModifiedMember(member.getId());

        Account accountEntity = accountRepository.save(accountInstance);
        return accountConverter.toAccountCreateResponse(accountEntity);
    }

    @Transactional(readOnly = true)
    public AccountDetailResponse getOne(Long accountId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );

        Account account = accountRepository.findByIdAndMember(accountId, member)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_ACCOUNT)
                );
        return accountConverter.toAccountDetailResponse(account);
    }

    @Transactional(readOnly = true)
    public Page<AccountDetailAllResponse> findAllByPages(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        Page<Account> allByMember = accountRepository.findAllByMember(member, pageable);

        return allByMember.map(accountConverter::toAccountDetailAllResponse);
    }

    @Transactional
    public AccountUpdateResponse update(Long memberId, Long accountId, String title, String description) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );

        Account account = accountRepository.findByIdAndMember(accountId, member)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_ACCOUNT)
                );

        account.update(title, description);

        return accountConverter.toAccountUpdateResponse(account);
    }

    @Transactional
    public AccountDeleteResponse delete(Long memberId, Long accountId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        accountRepository.deleteByIdAndMember(accountId, member);

        return accountConverter.toAccountDeleteResponse(accountId);
    }
}