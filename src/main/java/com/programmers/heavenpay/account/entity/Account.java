package com.programmers.heavenpay.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.member.entity.Member;

import javax.persistence.*;

@Entity
public class Account extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(name = "account_title", length = 50, nullable = false)
    private String title;

    @Column(name = "account_description", length = 100)
    private String description;

    @Column(name = "account_number", nullable = false)
    private String number;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finance_id")
    private Finance finance;

    protected Account() {

    }

    public Account(Long id, String title, String description, String number, Member member, Finance finance) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.number = number;
        this.member = member;
        this.finance = finance;
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getNumber() {
        return number;
    }

    public Member getMember() {
        return member;
    }

    public Finance getFinance() {
        return finance;
    }

    public static Account.AccountBuilder builder() {
        return new Account.AccountBuilder();
    }

    public static class AccountBuilder {
        private Long id;
        private String title;
        private String description;
        private String number;
        private Member member;
        private Finance finance;

        private AccountBuilder() {
        }

        public Account.AccountBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public Account.AccountBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public Account.AccountBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public Account.AccountBuilder number(final String number) {
            this.number = number;
            return this;
        }

        @JsonIgnore
        public Account.AccountBuilder member(final Member member) {
            this.member = member;
            return this;
        }

        @JsonIgnore
        public Account.AccountBuilder finance(final Finance finance) {
            this.finance = finance;
            return this;
        }

        public Account build() {
            return new Account(this.id, this.title, this.description, this.number, this.member, this.finance);
        }
    }
}
