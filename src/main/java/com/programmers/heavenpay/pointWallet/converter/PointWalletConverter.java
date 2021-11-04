package com.programmers.heavenpay.pointWallet.converter;

import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletCreateResponse;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletDeleteResponse;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletGetOneResponse;
import com.programmers.heavenpay.pointWallet.dto.response.PointWalletUpdateResponse;
import com.programmers.heavenpay.pointWallet.entity.PointWallet;
import org.springframework.stereotype.Component;

@Component
public class PointWalletConverter {
    public PointWallet toPointWalletEntity(Member member, Account account, int walletPoint) {
        return PointWallet.builder()
                .member(member)
                .account(account)
                .walletPoint(walletPoint)
                .build();
    }

    public PointWalletCreateResponse toPointWalletCreateResponse(PointWallet pointHistory) {
        return PointWalletCreateResponse.builder()
                .id(pointHistory.getId())
                .build();
    }

    public PointWalletGetOneResponse toPointWalletFindResponse(PointWallet pointWallet) {
        return PointWalletGetOneResponse.builder()
                .id(pointWallet.getId())
                .walletPoint(pointWallet.getWalletPoint())
                .build();
    }

    public PointWalletUpdateResponse toPointWalletUpdateResponse(PointWallet pointWallet) {
        return PointWalletUpdateResponse.builder()
                .id(pointWallet.getId())
                .walletPoint(pointWallet.getWalletPoint())
                .build();
    }

    public PointWalletDeleteResponse toPointWalletDeleteResponse(PointWallet pointWallet) {
        return PointWalletDeleteResponse.builder()
                .id(pointWallet.getId())
                .walletPoint(pointWallet.getWalletPoint())
                .build();
    }
}
