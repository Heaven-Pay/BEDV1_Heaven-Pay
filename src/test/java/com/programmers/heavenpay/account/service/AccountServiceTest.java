package com.programmers.heavenpay.account.service;

import com.programmers.heavenpay.account.converter.AccountConverter;
import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.account.repository.AccountRepository;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.entity.vo.FinanceType;
import com.programmers.heavenpay.finance.repository.FinanceRepository;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.entity.vo.GenderType;
import com.programmers.heavenpay.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    private static final Long MEMBER_ID = 1L;
    private static final Long FINANCE_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;
    private static final String ACCOUNT_TITLE = "계좌 별명";
    private static final String ACCOUNT_DESCRIPTION = "계좌 등록 설명";
    private static final String ACCOUNT_NUMBER = "1234659642314";

    @InjectMocks
    private AccountService accountService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FinanceRepository financeRepository;

    @Mock
    private AccountConverter accountConverter;

    @Mock
    private AccountRepository accountRepository;

    private Member member = Member.builder()
            .id(MEMBER_ID)
            .email("wrjs@naver.com")
            .name("김동건")
            .phoneNumber("01031829709")
            .birth("19970908")
            .gender(GenderType.MALE)
            .build();

    private Finance finance = Finance.builder()
            .id(FINANCE_ID)
            .name("국민은행")
            .financeType(FinanceType.BANK)
            .build();

    private Account account = Account.builder()
            .id(ACCOUNT_ID)
            .title(ACCOUNT_TITLE)
            .description(ACCOUNT_DESCRIPTION)
            .number(ACCOUNT_NUMBER)
            .member(member)
            .finance(finance)
            .build();

    @DisplayName("계좌_생성")
    @Test
    void create() {
        // given
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(financeRepository.findById(FINANCE_ID)).thenReturn(Optional.of(finance));
        when(accountConverter.toAccountEntity(ACCOUNT_TITLE, ACCOUNT_DESCRIPTION, ACCOUNT_NUMBER, member, finance)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);

        // when
        accountService.create(MEMBER_ID, ACCOUNT_TITLE, ACCOUNT_DESCRIPTION, ACCOUNT_NUMBER, FINANCE_ID);

        // then
        verify(accountRepository).save(account);
    }

    @Test
    void 계좌_단건_조회(){
        // given
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(accountRepository.findByIdAndMember(ACCOUNT_ID, member)).thenReturn(Optional.of(account));

        // when
        accountService.getOne(ACCOUNT_ID, MEMBER_ID);

        // then
        verify(accountRepository).findByIdAndMember(ACCOUNT_ID, member);
    }
}