package com.programmers.heavenpay.pointWallet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointWallet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "point_wallet_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "point_wallet_point", nullable = false)
    private Integer walletPoint;

    public void updateData(Integer point, Account account) {
        this.walletPoint = point;
        this.account = account;
    }
}
