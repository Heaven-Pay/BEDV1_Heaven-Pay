package com.programmers.heavenpay.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
