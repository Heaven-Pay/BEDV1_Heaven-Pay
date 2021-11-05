package com.programmers.heavenpay.pointWallet.service;

import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.account.repository.AccountRepository;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.finance.entity.vo.FinanceType;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.entity.vo.GenderType;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.pointWallet.converter.PointWalletConverter;
import com.programmers.heavenpay.pointWallet.entity.PointWallet;
import com.programmers.heavenpay.pointWallet.repository.PointWalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointWalletServiceTest {
    private static final Long POINT_WALLET_ID = 2L;
    private static final Integer WALLET_POINT = 10000;

    private static final Long MEMBER_ID = 1L;
    private static final String EMAIL = "ddkk94@naver.com";
    private static final String NAME =  "Taid";
    private static final String PHONE_NUMBER = "01011223344";
    private static final String BIRTH = "20211015";
    private static final GenderType GENDER = GenderType.MALE;

    private static final Long ACCOUNT_ID = 3L;
    private static final String ACCOUNT_TITLE = "계좌 별명";
    private static final String ACCOUNT_DESCRIPTION = "계좌 등록 설명";
    private static final String ACCOUNT_NUMBER = "1234659642314";

    private static final Long FINANCE_ID = 1L;

    @InjectMocks
    private PointWalletService pointWalletService;

    @Mock
    private PointWalletRepository pointWalletRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PointWalletConverter converter;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<PointWallet> pointWallets;

    private PointWallet pointWallet = PointWallet.builder()
            .id(POINT_WALLET_ID)
            .walletPoint(WALLET_POINT)
            .build();

    private Member member = Member.builder()
            .id(MEMBER_ID)
            .email(EMAIL)
            .name(NAME)
            .phoneNumber(PHONE_NUMBER)
            .birth(BIRTH)
            .gender(GENDER)
            .build();


    private Finance finance = Finance.builder()
            .id(FINANCE_ID)
            .name("신한은행")
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

    @Test
    void create() {
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));
        when(converter.toPointWalletEntity(member, account, WALLET_POINT)).thenReturn(pointWallet);
        when(pointWalletRepository.save(pointWallet)).thenReturn(pointWallet);

        // when
        pointWalletService.create(MEMBER_ID, ACCOUNT_ID, WALLET_POINT);

        // then
        verify(memberRepository).findById(MEMBER_ID);
        verify(accountRepository).findById(ACCOUNT_ID);
        verify(converter).toPointWalletEntity(member, account, pointWallet.getWalletPoint());
        verify(pointWalletRepository).save(pointWallet);
    }

    @Test
    void getOne() {
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));
        when(pointWalletRepository.findByIdAndMemberAndAccount(POINT_WALLET_ID, member, account)).thenReturn(Optional.of(pointWallet));

        // when
        pointWalletService.getOne(POINT_WALLET_ID, MEMBER_ID, ACCOUNT_ID);

        // then
        verify(memberRepository).findById(MEMBER_ID);
        verify(accountRepository).findById(ACCOUNT_ID);
        verify(pointWalletRepository).findByIdAndMemberAndAccount(POINT_WALLET_ID, member, account);
        verify(converter).toPointWalletFindResponse(pointWallet);
    }

    @Test
    void getAll() {
        when(pointWalletRepository.findAll(pageable)).thenReturn(pointWallets);

        // when
        pointWalletService.getAll(pageable);

        // then
        verify(pointWalletRepository).findAll(pageable);
    }

    @Test
    void update() {
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));
        when(pointWalletRepository.findByIdAndMemberAndAccount(POINT_WALLET_ID, member, account)).thenReturn(Optional.of(pointWallet));

        // when
        pointWalletService.update(POINT_WALLET_ID, MEMBER_ID, ACCOUNT_ID, WALLET_POINT + 100);

        // then
        verify(memberRepository).findById(MEMBER_ID);
        verify(accountRepository).findById(ACCOUNT_ID);
        verify(pointWalletRepository).findByIdAndMemberAndAccount(POINT_WALLET_ID, member, account);
        verify(converter).toPointWalletUpdateResponse(pointWallet);
    }

    @Test
    void delete() {
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));
        when(pointWalletRepository.findById(POINT_WALLET_ID)).thenReturn(Optional.of(pointWallet));

        // when
        pointWalletService.delete(POINT_WALLET_ID, MEMBER_ID, ACCOUNT_ID);

        // then
        verify(memberRepository).findById(MEMBER_ID);
        verify(accountRepository).findById(ACCOUNT_ID);
        verify(pointWalletRepository).findById(POINT_WALLET_ID);
        verify(converter).toPointWalletDeleteResponse(pointWallet);
    }
}