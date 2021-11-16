package com.programmers.heavenpay.finance.entity;

import com.programmers.heavenpay.common.entity.BaseEntity;
import com.programmers.heavenpay.finance.entity.vo.FinanceType;

import javax.persistence.*;

@Entity
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

    protected Finance() {

    }

    public Finance(Long id, String name, FinanceType financeType) {
        this.id = id;
        this.name = name;
        this.financeType = financeType;
    }

    public void update(Long memberId, String name, String financeType) {
        this.name = name.isBlank() ? this.name : name;
        this.financeType = FinanceType.of(financeType);
        updateLastModifiedMember(memberId);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FinanceType getFinanceType() {
        return financeType;
    }

    public static Finance.FinanceBuilder builder() {
        return new Finance.FinanceBuilder();
    }

    public static class FinanceBuilder {
        private Long id;
        private String name;
        private FinanceType financeType;

        private FinanceBuilder() {
        }

        public Finance.FinanceBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public Finance.FinanceBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public Finance.FinanceBuilder financeType(final FinanceType financeType) {
            this.financeType = financeType;
            return this;
        }

        public Finance build() {
            return new Finance(this.id, this.name, this.financeType);
        }
    }
}
