package com.programmers.heavenpay.remittance.service;

import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.account.repository.AccountRepository;
import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.repository.FinanceRepository;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.remittance.converter.RemittanceConverter;
import com.programmers.heavenpay.remittance.dto.response.RemittanceCreateResponse;
import com.programmers.heavenpay.remittance.dto.response.RemittanceDetailAllResponse;
import com.programmers.heavenpay.remittance.dto.response.RemittanceGetResponse;
import com.programmers.heavenpay.remittance.entity.Remittance;
import com.programmers.heavenpay.remittance.repository.RemittanceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemittanceService {
    private final RemittanceRepository remittanceRepository;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final FinanceRepository financeRepository;
    private final RemittanceConverter remittanceConverter;

    public RemittanceService(RemittanceRepository remittanceRepository, MemberRepository memberRepository, AccountRepository accountRepository, FinanceRepository financeRepository, RemittanceConverter remittanceConverter) {
        this.remittanceRepository = remittanceRepository;
        this.memberRepository = memberRepository;
        this.accountRepository = accountRepository;
        this.financeRepository = financeRepository;
        this.remittanceConverter = remittanceConverter;
    }

    @Transactional
    public RemittanceCreateResponse create(Long memberId, Long accountId, Long financeId, String name, String number, Integer money) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );

        Account account = accountRepository.findByIdAndMember(accountId, member)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_ACCOUNT)
                );

        Finance finance = financeRepository.findById(financeId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_FINANCE_TYPE)
                );
        Remittance remittanceInstance = remittanceConverter.toRemittanceEntity(member, account, finance, name, number, money);
        remittanceInstance.addCreatedAndLastModifiedMember(member.getId());

        Remittance remittance = remittanceRepository.save(remittanceInstance);

        return remittanceConverter.toRemittanceCreateResponse(remittance);
    }

    @Transactional(readOnly = true)
    public RemittanceGetResponse getOne(Long memberId, Long remittanceId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        Remittance remittance = remittanceRepository.findByIdAndMember(remittanceId, member)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_REMITTANCE)
                );

        return remittanceConverter.toRemittanceGetResponse(remittance);
    }

    @Transactional(readOnly = true)
    public Page<RemittanceDetailAllResponse> getAll(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        return remittanceRepository.findByMember(member, pageable)
                .map(remittanceConverter::toRemittanceDetailAllResponse);
    }
}
