package com.programmers.heavenpay.finance.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.finance.entity.vo.FinanceType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Finance extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "finance_id", nullable = false)
    private Long id;

    @Column(name = "finance_name", nullable = false, length = 15)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "finance_type")
    private FinanceType financeType;

    public void update(Long memberId, String name, String financeType) {
        this.name = name.isBlank() ? this.name : name;
        this.financeType = FinanceType.of(financeType);
        updateLastModifiedMember(memberId);
    }
}
