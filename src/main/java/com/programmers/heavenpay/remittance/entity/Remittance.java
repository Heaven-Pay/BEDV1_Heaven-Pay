package com.programmers.heavenpay.remittance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.programmers.heavenpay.account.entity.Account;
import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.finance.entity.Finance;
import com.programmers.heavenpay.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
}
