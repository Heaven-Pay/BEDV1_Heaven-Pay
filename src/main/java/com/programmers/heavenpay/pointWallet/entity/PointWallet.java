package com.programmers.heavenpay.pointWallet.entity;

import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.member.entity.Member;

import javax.persistence.*;

@Entity
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

    protected PointWallet() {
    }

    public PointWallet(Long id, Member member, Account account, Integer walletPoint) {
        this.id = id;
        this.member = member;
        this.account = account;
        this.walletPoint = walletPoint;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Account getAccount() {
        return account;
    }

    public Integer getWalletPoint() {
        return walletPoint;
    }

    public void updateData(Integer point, Account account) {
        this.walletPoint = point;
        this.account = account;
    }

    public static PointWallet.PointWalletBuilder builder() {
        return new PointWallet.PointWalletBuilder();
    }

    public static class PointWalletBuilder {
        private Long id;
        private Member member;
        private Account account;
        private Integer walletPoint;

        private PointWalletBuilder() {
        }

        public PointWallet.PointWalletBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PointWallet.PointWalletBuilder member(final Member member) {
            this.member = member;
            return this;
        }

        public PointWallet.PointWalletBuilder account(final Account account) {
            this.account = account;
            return this;
        }

        public PointWallet.PointWalletBuilder walletPoint(final Integer walletPoint) {
            this.walletPoint = walletPoint;
            return this;
        }

        public PointWallet build() {
            return new PointWallet(this.id, this.member, this.account, this.walletPoint);
        }
    }
}
