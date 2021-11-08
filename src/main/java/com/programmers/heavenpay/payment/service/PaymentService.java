package com.programmers.heavenpay.payment.service;

import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.payment.converter.PaymentConverter;
import com.programmers.heavenpay.payment.dto.response.PaymentCreateResponse;
import com.programmers.heavenpay.payment.dto.response.PaymentDeleteResponse;
import com.programmers.heavenpay.payment.entity.Payment;
import com.programmers.heavenpay.payment.repository.PaymentRepository;
import com.programmers.heavenpay.pointHistory.entity.PointHistory;
import com.programmers.heavenpay.pointHistory.repository.PointHistoryRepository;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletCreateResponse;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletDeleteResponse;
import com.programmers.heavenpay.pointWallet.entity.PointWallet;
import com.programmers.heavenpay.pointWallet.repository.PointWalletRepository;
import com.programmers.heavenpay.store.entity.Store;
import com.programmers.heavenpay.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final PointWalletRepository pointWalletRepository;

    private final PaymentConverter converter;

    @Transactional
    public PaymentCreateResponse create(Long memberId, Long storeId, Long pointWalletId, Integer payPoint) {
        Member originMember = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID)
                );
        Store store = storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_STORE)
                );
        PointWallet pointWallet = pointWalletRepository.findById(pointWalletId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_POINT_WALLET_ID)
                );
        if (pointWallet.getWalletPoint() < payPoint) {
            new NotExistsException(ErrorMessage.NOT_ENOUGH_POINT);
        }

        pointWallet.updateData(pointWallet.getWalletPoint() - payPoint, pointWallet.getAccount());

        Payment orginPayment = converter.toPaymentEntity(originMember, store, pointWallet, payPoint);
        Payment pointWalletEntity = paymentRepository.save(orginPayment);

        return converter.toPaymentCreateResponse(pointWalletEntity);
    }

    @Transactional
    public PaymentDeleteResponse delete(Long id, Long memberId, Long storeId, Long pointWalletId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID)
                );
        Store store = storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_STORE)
                );
        PointWallet pointWallet = pointWalletRepository.findById(pointWalletId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_POINT_WALLET_ID)
                );

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_PAYMENT)
                );
        PaymentDeleteResponse result = converter.toPaymentDeleteResponse(payment);

        pointWallet.updateData(pointWallet.getWalletPoint() + payment.getPaymentPoint(), pointWallet.getAccount());
        pointWalletRepository.save(pointWallet);

        paymentRepository.deleteByIdAndMemberAndStoreAndPointWallet(id, member, store, pointWallet);
        return result;
    }
}
