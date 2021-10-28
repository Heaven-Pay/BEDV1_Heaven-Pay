package com.programmers.heavenpay.account.service;

import com.programmers.heavenpay.account.converter.AccountConverter;
import com.programmers.heavenpay.account.dto.response.AccountCreateResponse;
import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.account.repository.AccountRepository;
import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.repository.FinanceRepository;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountConverter accountConverter;
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private final FinanceRepository financeRepository;

    // TODO :: 사용자 검증은 Security에서 해야함
    @Transactional
    public AccountCreateResponse create(Long memberId, String title, String description, String number, Long financeId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID)
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
}
