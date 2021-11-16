package com.programmers.heavenpay.remittance.service;

import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.account.repository.AccountRepository;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.entity.vo.FinanceType;
import com.programmers.heavenpay.finance.repository.FinanceRepository;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.entity.vo.GenderType;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.remittance.converter.RemittanceConverter;
import com.programmers.heavenpay.remittance.dto.response.RemittanceGetResponse;
import com.programmers.heavenpay.remittance.entity.Remittance;
import com.programmers.heavenpay.remittance.repository.RemittanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemittanceServiceTest {
    private final Long REMITTANCE_ID = 1L;
    private final Long MEMBER_ID = 1L;
    private final Long FINANCE_ID = 1L;
    private final String FINANCE_NAME = "신한";
    private final Long ACCOUNT_ID = 1L;
    private final String ACCOUNT_TITLE = "계좌 별명";
    private final String ACCOUNT_DESCRIPTION = "계좌 등록 설명";
    private final String ACCOUNT_NUMBER = "1234659642314";
    private final String NAME = "김동건";
    private final String NUMBER = "524586456349654";
    private final Integer MONEY = 10000;
    private final String BANK_NAME = "국민은행";
    private final String MEMBER_BIRTH = "19970908";
    private final String MEMBER_PHONENUMBER = "01031829709";
    private final String REMITTANCE_MEMBER_NAME = "김동건";
    private final String MEMBER_EMAIL = "wrjs@naver.com";
    private final String MEMBER_NAME = "황인준";

    @InjectMocks
    private RemittanceService remittanceService;

    @Mock
    private RemittanceRepository remittanceRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FinanceRepository financeRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RemittanceConverter remittanceConverter;

    @Mock
    private Page<Remittance> remittances;

    @Mock
    private Pageable pageable;

    private Member member = Member.builder()
            .id(MEMBER_ID)
            .email(MEMBER_EMAIL)
            .name(REMITTANCE_MEMBER_NAME)
            .phoneNumber(MEMBER_PHONENUMBER)
            .birth(MEMBER_BIRTH)
            .gender(GenderType.MALE)
            .build();

    private Finance finance = Finance.builder()
            .id(FINANCE_ID)
            .name(BANK_NAME)
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

    private Remittance remittance = Remittance.builder()
            .id(REMITTANCE_ID)
            .member(member)
            .account(account)
            .finance(finance)
            .name(NAME)
            .number(NUMBER)
            .money(MONEY)
            .build();

    private RemittanceGetResponse remittanceGetResponse = RemittanceGetResponse.builder()
            .remittanceId(REMITTANCE_ID)
            .memberName(MEMBER_NAME)
            .financeName(FINANCE_NAME)
            .remittanceName(NAME)
            .remittanceNumber(NUMBER)
            .remittanceMoney(MONEY)
            .build();

    @Test
    void 송금하기() {
        // given
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(accountRepository.findByIdAndMember(ACCOUNT_ID, member)).thenReturn(Optional.of(account));
        when(financeRepository.findById(FINANCE_ID)).thenReturn(Optional.of(finance));
        when(remittanceConverter.toRemittanceEntity(member, account, finance, NAME, NUMBER, MONEY)).thenReturn(remittance);
        when(remittanceRepository.save(remittance)).thenReturn(remittance);

        // when
        remittanceService.create(MEMBER_ID, ACCOUNT_ID, FINANCE_ID, NAME, NUMBER, MONEY);

        // then
        verify(remittanceRepository).save(remittance);
    }

    @Test
    void 송금_단건_조회() {
        // given
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(remittanceRepository.findByIdAndMember(REMITTANCE_ID, member)).thenReturn(Optional.of(remittance));
        when(remittanceConverter.toRemittanceGetResponse(remittance)).thenReturn(remittanceGetResponse);

        // when
        remittanceService.getOne(MEMBER_ID, REMITTANCE_ID);

        // then
        verify(remittanceRepository).findByIdAndMember(REMITTANCE_ID, member);
    }

    @Test
    void 송금_전체_조회() {
        // given
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(remittanceRepository.findByMember(member, pageable)).thenReturn(remittances);

        // when
        remittanceService.getAll(MEMBER_ID, pageable);

        // then
        verify(remittanceRepository).findByMember(member, pageable);
    }
}