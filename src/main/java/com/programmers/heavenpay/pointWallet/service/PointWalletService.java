package com.programmers.heavenpay.pointWallet.service;

import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.account.repository.AccountRepository;
import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.pointWallet.converter.PointWalletConverter;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletCreateResponse;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletDeleteResponse;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletGetOneResponse;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletUpdateResponse;
import com.programmers.heavenpay.pointWallet.entity.PointWallet;
import com.programmers.heavenpay.pointWallet.repository.PointWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointWalletService {
    private final PointWalletRepository pointWalletRepository;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final PointWalletConverter converter;

    public PointWalletService(PointWalletRepository pointWalletRepository, MemberRepository memberRepository, AccountRepository accountRepository, PointWalletConverter converter) {
        this.pointWalletRepository = pointWalletRepository;
        this.memberRepository = memberRepository;
        this.accountRepository = accountRepository;
        this.converter = converter;
    }

    @Transactional
    public PointWalletCreateResponse create(Long memberId, Long accountId, Integer walletPoint) {
        Member originMember = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        Account account = accountRepository.findById(accountId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_ACCOUNT)
                );

        PointWallet orginPointWallet = converter.toPointWalletEntity(originMember, account, walletPoint);
        PointWallet pointWalletEntity = pointWalletRepository.save(orginPointWallet);

        return converter.toPointWalletCreateResponse(pointWalletEntity);
    }

    @Transactional(readOnly = true)
    public PointWalletGetOneResponse getOne(Long id, Long memberId, Long accountId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        Account account = accountRepository.findById(accountId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_ACCOUNT)
                );
        PointWallet pointWallet = pointWalletRepository.findByIdAndMemberAndAccount(id, member, account)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_POINT_WALLET)
                );;
        return converter.toPointWalletFindResponse(pointWallet);
    }

    @Transactional(readOnly = true)
    public Page<PointWalletGetOneResponse> getAll(Pageable pageable){
        Page<PointWallet> allByMember = pointWalletRepository.findAll(pageable);
        return allByMember.map(converter::toPointWalletFindResponse);
    }

    @Transactional
    public PointWalletUpdateResponse update(Long id, Long memberId, Long accountId, Integer walletPoint) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );

        Account account = accountRepository.findById(accountId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_ACCOUNT)
                );

        PointWallet orginPointWallet = pointWalletRepository.findByIdAndMemberAndAccount(id, member, account)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_POINT_WALLET));

        orginPointWallet.updateData(walletPoint, account);
        return converter.toPointWalletUpdateResponse(orginPointWallet);
    }

    @Transactional
    public PointWalletDeleteResponse delete(Long id, Long memberId, Long accountId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER)
                );
        Account account = accountRepository.findById(accountId)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_ACCOUNT)
                );
        PointWallet pointWallet = pointWalletRepository.findById(id)
                .orElseThrow(
                        () -> new NotExistsException(ErrorMessage.NOT_EXIST_POINT_WALLET)
                );
        PointWalletDeleteResponse result = converter.toPointWalletDeleteResponse(pointWallet);

        pointWalletRepository.deleteByIdAndMemberAndAccount(id, member, account);
        return result;
    }
}
