package com.programmers.heavenpay.remittance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.member.entity.Member;

import javax.persistence.*;

@Entity
public class Remittance extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "remittance_id", unique = true, nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finance_id")
    private Finance finance;

    @Column(name = "remittance_name", nullable = false)
    private String name;

    @Column(name = "remittance_number", nullable = false)
    private String number;

    @Column(name = "remittance_money", nullable = false)
    private Integer money;

    protected Remittance() {

    }

    public Remittance(Long id, Member member, Account account, Finance finance, String name, String number, Integer money) {
        this.id = id;
        this.member = member;
        this.account = account;
        this.finance = finance;
        this.name = name;
        this.number = number;
        this.money = money;
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

    public Finance getFinance() {
        return finance;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public Integer getMoney() {
        return money;
    }


    public static Remittance.RemittanceBuilder builder() {
        return new Remittance.RemittanceBuilder();
    }

    public static class RemittanceBuilder {
        private Long id;
        private Member member;
        private Account account;
        private Finance finance;
        private String name;
        private String number;
        private Integer money;

        private RemittanceBuilder() {
        }

        public Remittance.RemittanceBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @JsonIgnore
        public Remittance.RemittanceBuilder member(final Member member) {
            this.member = member;
            return this;
        }

        @JsonIgnore
        public Remittance.RemittanceBuilder account(final Account account) {
            this.account = account;
            return this;
        }

        @JsonIgnore
        public Remittance.RemittanceBuilder finance(final Finance finance) {
            this.finance = finance;
            return this;
        }

        public Remittance.RemittanceBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public Remittance.RemittanceBuilder number(final String number) {
            this.number = number;
            return this;
        }

        public Remittance.RemittanceBuilder money(final Integer money) {
            this.money = money;
            return this;
        }

        public Remittance build() {
            return new Remittance(this.id, this.member, this.account, this.finance, this.name, this.number, this.money);
        }
    }
}