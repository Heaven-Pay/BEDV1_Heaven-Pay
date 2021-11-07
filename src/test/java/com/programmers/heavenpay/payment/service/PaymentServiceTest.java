package com.programmers.heavenpay.payment.service;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.entity.vo.GenderType;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.payment.converter.PaymentConverter;
import com.programmers.heavenpay.payment.dto.response.PaymentDeleteResponse;
import com.programmers.heavenpay.payment.entity.Payment;
import com.programmers.heavenpay.payment.repository.PaymentRepository;
import com.programmers.heavenpay.pointWallet.entity.PointWallet;
import com.programmers.heavenpay.pointWallet.repository.PointWalletRepository;
import com.programmers.heavenpay.store.entity.Store;
import com.programmers.heavenpay.store.entity.vo.StoreType;
import com.programmers.heavenpay.store.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    private static final Long PAYMENT_ID = 1L;
    private static final Integer PAYMENT_POINT = 2000;

    private static final Long MEMBER_ID = 1L;
    private static final String MEMBER_EMAIL = "ddkk94@naver.com";
    private static final String MEMBER_NAME =  "Taid";
    private static final String MEMBER_PHONE_NUMBER = "01011223344";
    private static final String MEMBER_BIRTH = "20211015";
    private static final GenderType MEMBER_GENDER = GenderType.MALE;

    private static final Long POINT_WALLET_ID = 1L;
    private static final Integer WALLET_POINT = 10000;

    private static final Long STORE_ID = 1L;
    private static final String STORE_NAME = "가게 이름";
    private static final StoreType STORE_TYPE = StoreType.RESTAURANT;
    private static final String STORE_VENDOR_CODE = "111-55-33333";

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private PointWalletRepository pointWalletRepository;

    @Mock
    private PaymentConverter converter;

    private Payment payment = Payment.builder()
            .id(PAYMENT_ID)
            .paymentPoint(PAYMENT_POINT)
            .build();

    private Member member = Member.builder()
            .id(MEMBER_ID)
            .email(MEMBER_EMAIL)
            .name(MEMBER_NAME)
            .phoneNumber(MEMBER_PHONE_NUMBER)
            .birth(MEMBER_BIRTH)
            .gender(MEMBER_GENDER)
            .build();

    private PointWallet pointWallet = PointWallet.builder()
            .id(POINT_WALLET_ID)
            .walletPoint(WALLET_POINT)
            .build();

    private Store store = Store.builder()
            .id(STORE_ID)
            .name(STORE_NAME)
            .type(STORE_TYPE)
            .vendorCode(STORE_VENDOR_CODE)
            .build();

    private PaymentDeleteResponse deleteResponse = PaymentDeleteResponse.builder()
            .id(PAYMENT_ID)
            .payPoint(PAYMENT_POINT)
            .build();

    @Test
    void create() {
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.of(store));
        when(pointWalletRepository.findById(POINT_WALLET_ID)).thenReturn(Optional.of(pointWallet));
        when(converter.toPaymentEntity(member, store, pointWallet, PAYMENT_POINT)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);

        // when
        paymentService.create(MEMBER_ID, STORE_ID, POINT_WALLET_ID, PAYMENT_POINT);

        // then
        verify(memberRepository).findById(MEMBER_ID);
        verify(storeRepository).findById(STORE_ID);
        verify(pointWalletRepository).findById(POINT_WALLET_ID);
        verify(converter).toPaymentEntity(member, store, pointWallet, payment.getPaymentPoint());
        verify(paymentRepository).save(payment);
    }

    @Test
    void delete() {
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(storeRepository.findById(STORE_ID)).thenReturn(Optional.of(store));
        when(pointWalletRepository.findById(POINT_WALLET_ID)).thenReturn(Optional.of(pointWallet));
        when(paymentRepository.findById(PAYMENT_ID)).thenReturn(Optional.of(payment));
        when(converter.toPaymentDeleteResponse(payment)).thenReturn(deleteResponse);

        // when
        paymentService.delete(PAYMENT_ID, MEMBER_ID, STORE_ID, POINT_WALLET_ID);

        // then
        verify(memberRepository).findById(MEMBER_ID);
        verify(storeRepository).findById(STORE_ID);
        verify(pointWalletRepository).findById(POINT_WALLET_ID);
        verify(paymentRepository).findById(PAYMENT_ID);
        verify(converter).toPaymentDeleteResponse(payment);
        verify(paymentRepository).deleteByIdAndMemberAndStoreAndPointWallet(PAYMENT_ID, member, store, pointWallet);
    }
}